package com.test.dosplash.base

import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.test.dosplash.R

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
abstract class BaseAcitivity : AppCompatActivity() {
    fun initGlide(): RequestManager {
        val options = RequestOptions()
            .placeholder(R.drawable.ic_gallery_placeholder)
            .error(R.drawable.ic_gallery_placeholder)
        return Glide.with(this)
            .setDefaultRequestOptions(options)
    }
}