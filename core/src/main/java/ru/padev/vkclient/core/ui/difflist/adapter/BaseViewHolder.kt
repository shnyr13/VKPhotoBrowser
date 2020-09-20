package ru.padev.vkclient.core.ui.difflist.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<D>(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    fun setVisibility(isVisible: Boolean) {
        val param =
            itemView.layoutParams as RecyclerView.LayoutParams
        if (isVisible) {
            param.height = ViewGroup.LayoutParams.WRAP_CONTENT
            param.width = ViewGroup.LayoutParams.MATCH_PARENT
            itemView.visibility = View.VISIBLE
        } else {
            itemView.visibility = View.GONE
            param.height = 0
            param.width = 0
        }
        itemView.layoutParams = param
    }

    open fun bindTo(item: D) {}
}