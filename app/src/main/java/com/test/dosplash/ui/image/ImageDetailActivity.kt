package com.test.dosplash.ui.image

import android.graphics.drawable.Drawable
import android.os.Bundle
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.test.dosplash.R
import com.test.dosplash.base.BaseActivity
import com.test.dosplash.model.UnsplashImage
import com.test.dosplash.util.Constant
import kotlinx.android.synthetic.main.activity_image_detail.*

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
class ImageDetailActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)
        val unsplashImage = intent.getParcelableExtra<UnsplashImage>(Constant.image)
        cross.setOnClickListener { finish() }
        val requestManager = initGlide()
        unsplashImage?.apply {
            val thumbnailRequest: RequestBuilder<Drawable> =
                requestManager.load(urls?.thumb)
            requestManager.load(urls?.full).thumbnail(thumbnailRequest)
                .into(mainImage)
            location.text = user?.location
            detail.text = description
            requestManager.load(user?.profileImage?.large)
                .apply(
                    RequestOptions.circleCropTransform()
                        .placeholder(R.drawable.ic_person_light_grey)
                        .diskCacheStrategy(
                            DiskCacheStrategy.ALL
                        )
                )
                .into(pg)
            name.text = user?.name
        }
    }
}