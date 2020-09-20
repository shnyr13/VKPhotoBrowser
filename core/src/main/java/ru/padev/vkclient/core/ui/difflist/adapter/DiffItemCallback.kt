package ru.padev.vkclient.core.ui.difflist.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.padev.vkclient.core.ui.difflist.internal.EqualityFunction

class DiffItemCallback<T> (
    var mIdentityEqualityFunction: EqualityFunction<in T>,
    var mContentEqualityFunction: EqualityFunction<in T>
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return mIdentityEqualityFunction
            .equal(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return mContentEqualityFunction.equal(oldItem, newItem)
    }
}