package ru.padev.vkclient.core.ui.difflist

import androidx.recyclerview.widget.ListUpdateCallback

class DataObservableListUpdateCallback(dataObserver: DataObserver) : ListUpdateCallback {
    private val mDataObserver: DataObserver = Preconditions.checkNotNull(
        dataObserver,
        "dataObserver"
    )

    override fun onInserted(position: Int, count: Int) {
        mDataObserver.onItemRangeInserted(position, count)
    }

    override fun onRemoved(position: Int, count: Int) {
        mDataObserver.onItemRangeRemoved(position, count)
    }

    override fun onMoved(fromPosition: Int, toPosition: Int) {
        mDataObserver.onItemRangeMoved(fromPosition, toPosition, 1)
    }

    override fun onChanged(position: Int, count: Int, payload: Any?) {
        mDataObserver.onItemRangeChanged(position, count)
    }
}