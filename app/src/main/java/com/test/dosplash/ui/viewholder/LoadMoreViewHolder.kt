package com.drbindra.badabusiness.ui.viewholder

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.test.dosplash.listener.OnLoadMoreListener
import kotlinx.android.synthetic.main.layout_loading_item.view.*

/**
 * Created by Saunik Singh on 5/16/2020.
 * Bada Business
 */
class LoadMoreViewHolder(itemView: View, onLoadMoreListener: OnLoadMoreListener?) :
    BaseViewHolder<Any>(itemView) {
    private val loadText: TextView = itemView.loadMoreText
    private val loadProgress: ProgressBar = itemView.progressBar1

    init {
        loadText.setOnClickListener {
            loadText.visibility = View.GONE
            loadProgress.visibility = View.VISIBLE
            onLoadMoreListener?.onLoadMore()
        }
    }

    override fun bind(item: Any) {
        loadProgress.visibility = View.GONE
        loadText.visibility = View.VISIBLE

    }
}