package ru.padev.vkclient.core.ui.difflist

interface DataObserver {
    fun onChanged()

    fun onItemRangeChanged(positionStart: Int, itemCount: Int)

    fun onItemRangeInserted(positionStart: Int, itemCount: Int)

    fun onItemRangeRemoved(positionStart: Int, itemCount: Int)

    fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int)
}