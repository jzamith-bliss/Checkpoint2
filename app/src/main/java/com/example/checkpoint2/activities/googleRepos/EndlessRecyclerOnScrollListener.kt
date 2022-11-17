package com.example.checkpoint2.activities.googleRepos

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

@Suppress("unused")
abstract class EndlessRecyclerOnScrollListener(
    manager: LinearLayoutManager
) : RecyclerView.OnScrollListener() {

    private var layoutManager: LinearLayoutManager = manager

    abstract fun onLoadMore(lastVisibleIndex: Int)

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val firstVisibleItemIndex: Int = layoutManager.findFirstVisibleItemPosition()
        val visibleItemCount: Int = layoutManager.childCount
        val totalItemCount: Int = layoutManager.itemCount
        val delta = if (layoutManager.orientation == LinearLayoutManager.HORIZONTAL) dx else dy
        val startToEnd: Boolean = !layoutManager.reverseLayout

        if ((if (startToEnd) delta > 0 else delta < 0) && firstVisibleItemIndex + visibleItemCount >= totalItemCount) {
            onLoadMore(layoutManager.findLastVisibleItemPosition())
        }
    }
}