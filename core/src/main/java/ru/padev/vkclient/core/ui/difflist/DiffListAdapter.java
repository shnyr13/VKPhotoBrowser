package ru.padev.vkclient.core.ui.difflist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import ru.padev.vkclient.core.ui.difflist.adapter.BaseViewHolder;
import ru.padev.vkclient.core.ui.difflist.internal.DiffList;
import ru.padev.vkclient.core.ui.difflist.internal.EqualityFunction;
import timber.log.Timber;

public abstract class DiffListAdapter<VH extends BaseViewHolder<T>, T>
        extends RecyclerView.Adapter<VH> implements DataObserver {

    private CompositeDisposable mDisposables = new CompositeDisposable();

    @NonNull protected final DiffList<T> mList;

    protected final PublishSubject<ViewData<T, VH>> mDidClickSubject = PublishSubject.create();

    public DiffListAdapter() {
        mList = new DiffList<>(this, null, null, false);
    }

    public DiffListAdapter(@Nullable EqualityFunction<? super T> identityEqualityFunction,
            @Nullable EqualityFunction<? super T> contentEqualityFunction, boolean detectMoves) {

        mList = new DiffList<>(this, identityEqualityFunction, contentEqualityFunction,
                detectMoves);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(getViewId(), parent, false);

        return instantiateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        final T item = getItem(position);

        holder.bindTo(item);

        holder.itemView.setOnClickListener(view -> {
            mDidClickSubject.onNext(new ViewData<>(item, holder));
        });
    }

    public abstract int getViewId();

    public abstract VH instantiateViewHolder(View view);

    @NonNull
    public T getItem(int position) {
        return mList.get(position);
    }

    public boolean isExists(int position) {
        return position >= 0 && mList.size() > position;
    }

    @Override
    public void onChanged() {
        notifyDataSetChanged();
    }

    @Override
    public void onItemRangeChanged(int positionStart, int itemCount) {
        notifyItemRangeChanged(positionStart, itemCount);
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        notifyItemRangeInserted(positionStart, itemCount);
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    @Override
    public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
        if (itemCount == 1) {
            notifyItemMoved(fromPosition, toPosition);
        } else {
            notifyDataSetChanged();
        }
    }

    public List<T> getItems() {
        return mList.getItems();
    }

    public int size() {
        return mList.size();
    }

    public T removeAt(int position) {
        return mList.removeAt(position);
    }

    public void addAt(T item, int position) {
        mList.add(item, position);
    }

    public void setIdentityEqualityFunction(
            @Nullable EqualityFunction<? super T> identityEqualityFunction) {
        mList.setIdentityEqualityFunction(identityEqualityFunction);
    }

    public void setContentEqualityFunction(
            @Nullable EqualityFunction<? super T> contentEqualityFunction) {
        mList.setContentEqualityFunction(contentEqualityFunction);
    }

    public void setDetectMoves(boolean detectMoves) {
        mList.setDetectMoves(detectMoves);
    }

    //TODO: create subject to update data
    public void apply(List<T> newData, @DiffList.Type int type) {
        apply(newData, type, null);
    }

    public void apply(List<T> newData, @DiffList.Type int type, ApplyCallback callback) {
        Disposable disposable = Observable.just(newData)
                .switchMap(ts -> mList.operation(ts, type))
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    if (callback != null) {
                        callback.applyCompleted();
                    }
                }, throwable -> {
                    Timber.d("Error update data");
                });

        addDisposable(disposable);
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

        if (!mDisposables.isDisposed()) {
            mDisposables.dispose();
        }
    }

    protected void addDisposable(Disposable disposable) {
        mDisposables.add(disposable);
    }

    public Observable<ViewData<T, VH>> getClickObservable() {
        return mDidClickSubject;
    }

    public interface ApplyCallback {
        void applyCompleted();
    }
}
