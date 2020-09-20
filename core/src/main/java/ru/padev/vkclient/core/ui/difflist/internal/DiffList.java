package ru.padev.vkclient.core.ui.difflist.internal;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import ru.padev.vkclient.core.ui.difflist.DataObservableListUpdateCallback;
import ru.padev.vkclient.core.ui.difflist.DataObserver;
import ru.padev.vkclient.core.ui.difflist.Preconditions;
import ru.padev.vkclient.core.utils.RxUtils;

import static ru.padev.vkclient.core.ui.difflist.internal.DiffList.Type.*;

public class DiffList<T> {
    protected final DataObserver mDataObserver;

    protected List<T> mData = Lists.newArrayList();

    @Nullable private EqualityFunction<? super T> mIdentityEqualityFunction;

    @Nullable private EqualityFunction<? super T> mContentEqualityFunction;

    private boolean mDetectMoves;

    @NonNull final ListUpdateCallback mListUpdateCallback;

    public DiffList(@NonNull DataObserver dataObserver,
            @Nullable EqualityFunction<? super T> identityEqualityFunction,
            @Nullable EqualityFunction<? super T> contentEqualityFunction, boolean detectMoves) {
        mDataObserver = dataObserver;
        mIdentityEqualityFunction = identityEqualityFunction;
        mContentEqualityFunction = contentEqualityFunction;
        mDetectMoves = detectMoves;
        mListUpdateCallback = new DataObservableListUpdateCallback(mDataObserver);
    }

    public void setIdentityEqualityFunction(
            @Nullable EqualityFunction<? super T> identityEqualityFunction) {
        this.mIdentityEqualityFunction = identityEqualityFunction;
    }

    public void setContentEqualityFunction(
            @Nullable EqualityFunction<? super T> contentEqualityFunction) {
        this.mContentEqualityFunction = contentEqualityFunction;
    }

    public void setDetectMoves(boolean detectMoves) {
        this.mDetectMoves = detectMoves;
    }

    public int size() {
        return mData.size();
    }

    @NonNull
    public T get(int position) {
        return mData.get(position);
    }

    public List<T> getItems() {
        return mData;
    }

    public T removeAt(int position) {
        T value = mData.remove(position);

        mDataObserver.onItemRangeRemoved(position, 1);

        return value;
    }

    public void add(T item, int position) {
        mData.add(position, item);

        mDataObserver.onItemRangeInserted(position, 1);
    }

    public void clear() {
        int size = mData.size();

        if (size > 0) {
            mData.clear();
            mDataObserver.onItemRangeRemoved(0, size);
        }
    }

    @NonNull
    public Observable<?> prepend(@NonNull final Collection<T> list) {
        Preconditions.INSTANCE.checkNotNull(list, "list");

        return RxUtils.mainThreadObservable(() -> {
            mData.addAll(0, list);
            mDataObserver.onItemRangeInserted(0, list.size());
            return Optional.absent();
        });
    }

    @NonNull
    public Observable<Optional> append(@NonNull final Collection<T> list) {
        Preconditions.INSTANCE.checkNotNull(list, "list");

        return RxUtils.mainThreadObservable(() -> {
            int oldSize = mData.size();

            ArrayList<T> newList = Lists.newArrayList();
            newList.addAll(list);

            mData = newList;
            mDataObserver.onItemRangeInserted(oldSize, list.size());
            return Optional.absent();
        });
    }

    @NonNull
    public Observable<?> overwrite(@NonNull Collection<T> collection) {
        Preconditions.INSTANCE.checkNotNull(collection, "collection");

        if (mIdentityEqualityFunction == null || mContentEqualityFunction == null) {
            return overwriteBasic(collection);
        }
        if (collection instanceof List) {
            if (collection.size() == 0) {
                return overwriteAll(collection);
            }

            return overwriteUsingDiffUtil((List<T>)collection, mIdentityEqualityFunction,
                    mContentEqualityFunction);
        }
        return overwriteUsingDiffUtil(new ArrayList<>(collection), mIdentityEqualityFunction,
                mContentEqualityFunction);
    }

    public Observable<?> operation(@NonNull Collection<T> collection, @Type int type) {
        if (type == Type.OVERWRITE) return overwrite(collection);
        if (type == APPEND) return append(collection);
        if (type == PREPEND) return prepend(collection);

        throw new IllegalArgumentException("operation does not support");
    }

    @NonNull
    Observable<?> overwriteBasic(@NonNull final Collection<T> collection) {
        return RxUtils.mainThreadObservable(() -> {
            final List<T> existing = mData;
            final int oldSize = existing.size();
            existing.clear();
            existing.addAll(collection);
            final int newSize = collection.size();
            final int deltaSize = newSize - oldSize;

            // Issue removal/insertion notifications. These must happen first, otherwise downstream item count
            // verification will complain that our size has changed without a corresponding structural ic_notification.
            if (deltaSize < 0) {
                mDataObserver.onItemRangeRemoved(oldSize + deltaSize, -deltaSize);
            } else if (deltaSize > 0) {
                mDataObserver.onItemRangeInserted(oldSize, deltaSize);
            }

            // Finally, issue a change ic_notification for the range of elements not accounted for above.
            final int changed = Math.min(oldSize, newSize);
            if (changed > 0) {
                mDataObserver.onItemRangeChanged(0, changed);
            }
            return Optional.absent();
        });
    }

    @NonNull
    Observable<?> overwriteAll(@NonNull final Collection<T> collection) {
        return RxUtils.mainThreadObservable(() -> {
            ArrayList<T> newList = Lists.newArrayList();
            newList.addAll(collection);

            mData = newList;
            mDataObserver.onChanged();

            return Optional.absent();
        });
    }

    @NonNull
    Observable<?> overwriteEmpty() {
        return RxUtils.mainThreadObservable(() -> {
            mDataObserver.onChanged();
            return Optional.absent();
        });
    }

    @NonNull
    Observable<?> overwriteUsingDiffUtil(@NonNull final List<T> newContents,
                                         @NonNull final EqualityFunction<? super T> identityEqualityFunction,
                                         @NonNull final EqualityFunction<? super T> contentEqualityFunction) {
        return copyContents().switchMap(
                existingContentsCopy -> calculateDiff(existingContentsCopy, newContents,
                        identityEqualityFunction, contentEqualityFunction)
                        .switchMap(diffResult -> applyNewContentsAndDispatchDiffNotifications(
                                newContents, diffResult)));
    }

    @NonNull
    Observable<List<T>> copyContents() {
        return RxUtils.mainThreadObservable(() -> new ArrayList<>(mData));
    }

    @NonNull
    Observable<DiffUtil.DiffResult> calculateDiff(@NonNull final List<T> oldContents,
                                                  @NonNull final List<T> newContents,
                                                  @NonNull final EqualityFunction<? super T> identityEqualityFunction,
                                                  @NonNull final EqualityFunction<? super T> contentEqualityFunction) {
        return Observable.fromCallable(
                () -> diff(oldContents, newContents, identityEqualityFunction,
                        contentEqualityFunction)).subscribeOn(Schedulers.computation());
    }

    @NonNull
    Observable<Optional> applyNewContentsAndDispatchDiffNotifications(
            @NonNull final List<T> newContents,
            @NonNull final DiffUtil.DiffResult diffResult) {
        return RxUtils.mainThreadObservable(() -> {
            mData = newContents;

            diffResult.dispatchUpdatesTo(mListUpdateCallback);
            return Optional.absent();
        });
    }

    @NonNull
    DiffUtil.DiffResult diff(@NonNull final List<T> oldList,
                             @NonNull final List<T> newList,
                             @NonNull final EqualityFunction<? super T> identityEqualityFunction,
                             @NonNull final EqualityFunction<? super T> contentEqualityFunction) {
        return DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return oldList.size();
            }

            @Override
            public int getNewListSize() {
                return newList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return identityEqualityFunction.equal(oldList.get(oldItemPosition),
                        newList.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return contentEqualityFunction.equal(oldList.get(oldItemPosition),
                        newList.get(newItemPosition));
            }
        }, mDetectMoves);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ OVERWRITE, PREPEND, APPEND })
    public @interface Type {
        int OVERWRITE = 1;
        int PREPEND = 2;
        int APPEND = 3;
    }
}
