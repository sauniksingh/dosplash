package com.test.dosplash.ui.viewholder

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.util.ViewPreloadSizeProvider
import com.drbindra.badabusiness.ui.viewholder.BaseViewHolder
import com.test.dosplash.R
import com.test.dosplash.listener.ImageClickListener
import com.test.dosplash.model.UnsplashImage
import kotlinx.android.synthetic.main.item_images_even.view.*

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
class EvenImageViewHolder(
    itemView: View,
    private val imageClickListener: ImageClickListener,
    private val requestManager: RequestManager,
    private val preloadSizeProvider: ViewPreloadSizeProvider<*>
) :
    BaseViewHolder<UnsplashImage>(itemView) {
    private val evenImage: ImageView = itemView.evenImage
    private val authorImage: ImageView = itemView.pg
    override fun bind(item: UnsplashImage) {
        val thumbnailRequest: RequestBuilder<Drawable> = requestManager.load(item.urls?.thumb)
        requestManager
            .load(item.urls?.full).thumbnail(thumbnailRequest)
            .into(evenImage)
        preloadSizeProvider.setView(evenImage)
        requestManager.load(item.user?.profileImage?.large)
            .apply(
                RequestOptions.circleCropTransform().placeholder(R.drawable.ic_person_light_grey)
                    .diskCacheStrategy(
                        DiskCacheStrategy.ALL
                    )
            )
            .into(authorImage)
        imageClickListener.onImageClick(item)
    }

}