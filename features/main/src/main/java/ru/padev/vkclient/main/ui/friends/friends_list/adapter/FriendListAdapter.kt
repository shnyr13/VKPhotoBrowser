package ru.padev.vkclient.main.ui.friends.friends_list.adapter

import android.view.View
import com.squareup.picasso.Picasso
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.rv_friend.view.*
import ru.padev.vkclient.core.ui.difflist.DiffListAdapter
import ru.padev.vkclient.core.ui.difflist.adapter.BaseViewHolder
import ru.padev.vkclient.core.ui.difflist.internal.EqualityFunction
import ru.padev.vkclient.main.R
import ru.padev.vkclient.main.data.vo.FriendVO

class FriendListAdapter (
    val friendDidClickSubject: PublishSubject<FriendVO>,
    identityEqualityFunction: EqualityFunction<in FriendVO>,
    contentEqualityFunction: EqualityFunction<in FriendVO>
): DiffListAdapter<FriendListAdapter.ViewHolder, FriendVO>(
    identityEqualityFunction,
    contentEqualityFunction,
    true
) {
    override fun getViewId(): Int {
        return R.layout.rv_friend
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)

        holder.mItemView.setOnClickListener {
            friendDidClickSubject.onNext(
                getItem(holder.adapterPosition)
            )
        }
    }

    override fun instantiateViewHolder(view: View): ViewHolder {
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : BaseViewHolder<FriendVO>(itemView) {

        var mItemView: View = itemView

        override fun bindTo(item: FriendVO) {
            super.bindTo(item)

            if (item.photo100.isBlank()) {
                mItemView.image_friend.setImageResource(R.drawable.ic_placeholder)
            }
            else {
                Picasso.get()
                    .load(item.photo100)
                    .placeholder(R.drawable.ic_placeholder)
                    .fit()
                    .centerInside()
                    .into(mItemView.image_friend)
            }

            itemView.name_friend.text = item.fullName

            itemView.image_online.visibility = if (item.online) View.VISIBLE else View.GONE
        }
    }
}