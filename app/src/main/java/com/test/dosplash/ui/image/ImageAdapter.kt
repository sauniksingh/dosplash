package com.test.dosplash.ui.image

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.ListPreloader
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.drbindra.badabusiness.ui.viewholder.BaseViewHolder
import com.drbindra.badabusiness.ui.viewholder.LoadMoreViewHolder
import com.test.dosplash.R
import com.test.dosplash.listener.ChildListener
import com.test.dosplash.listener.OnLoadMoreListener
import com.test.dosplash.model.UnsplashImage
import com.test.dosplash.ui.viewholder.EvenImageViewHolder
import com.test.dosplash.ui.viewholder.OddImageViewHolder

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
class ImageAdapter(
    private var items: ArrayList<UnsplashImage>,
    val childListener: ChildListener, private val requestManager: RequestManager,
    private val viewPreloadSizeProvider: ViewPreloadSizeProvider<String>) :
    RecyclerView.Adapter<BaseViewHolder<*>>(), ListPreloader.PreloadModelProvider<String> {
    companion object {
        private const val TYPE_EVEN = 0
        private const val TYPE_ODD = 1
        private const val TYPE_LOADING = 2
        private const val loading = "Loading..."
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val view: View?
        when (viewType) {
            TYPE_EVEN -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_images_even, parent, false)
                return EvenImageViewHolder(
                    view,
                    childListener,
                    requestManager,
                    viewPreloadSizeProvider
                )
            }
            TYPE_ODD -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_images_odd, parent, false)
                return OddImageViewHolder(
                    view,
                    childListener,
                    requestManager,
                    viewPreloadSizeProvider
                )
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.layout_loading_item, parent, false)
                return LoadMoreViewHolder(view, childListener)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (getItemViewType(position)) {
            TYPE_EVEN -> (holder as EvenImageViewHolder).bind(items[position])
            TYPE_ODD -> (holder as OddImageViewHolder).bind(items[position])
            TYPE_LOADING -> (holder as LoadMoreViewHolder).bind(items[position])
        }
    }

    override fun getPreloadItems(position: Int): List<String?> {
        val url: String? = items[position].urls?.full
        return if (TextUtils.isEmpty(url)) {
            emptyList()
        } else listOf(url)
    }

    override fun getPreloadRequestBuilder(item: String): RequestBuilder<*>? =
        requestManager.load(item)

    override fun getItemViewType(position: Int): Int {
        return when {
            loading.equals(items[position].type, true) -> TYPE_LOADING
            else -> position % 2
        }
    }

    // display loading during search request
    fun displayLoading() {
        if (!items.isNullOrEmpty()) {
            val load = items[items.size -1];
            if(!loading.equals(load.type, true)) {
                val image = UnsplashImage()
                image.type = loading
                items.add(image)
            }
            notifyDataSetChanged()
        }
    }

    fun hideLoading() {
        if (isLoading()) {
           val removePosition= items.size - 1
            if (loading.equals(items[0].type, true)) {
                items.removeAt(0)
            } else if (loading.equals(items[removePosition].type, true)) {
                items.removeAt(removePosition)
            }
            notifyItemRemoved(removePosition)
            notifyDataSetChanged()
        }
    }

    private fun isLoading(): Boolean {
        if (items.size > 0) {
            if (loading.equals(items[(items.size - 1)].type, true)) {
                return true
            }
        }
        return false
    }

    fun setImages(images: ArrayList<UnsplashImage>) {
        items = images
        notifyDataSetChanged()
    }
}