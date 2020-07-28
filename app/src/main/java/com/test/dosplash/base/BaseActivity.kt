package com.test.dosplash.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.test.dosplash.R
import com.test.dosplash.error.FailureResponse

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
abstract class BaseActivity : AppCompatActivity() {
    private lateinit var errorObserver: Observer<Throwable>
    private lateinit var failureResponseObserver: Observer<FailureResponse>
    fun initGlide(): RequestManager {
        val options = RequestOptions()
            .placeholder(R.drawable.ic_gallery_placeholder)
            .error(R.drawable.ic_gallery_placeholder)
        return Glide.with(this)
            .setDefaultRequestOptions(options)
    }

    @Suppress("DEPRECATION")
    open fun isNetworkAvailable(): Boolean {
        val cm =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected
    }

    private fun initObservers() {
        errorObserver =
            Observer {
                this.onErrorOccurred()
            }
        failureResponseObserver =
            Observer {
                onFailure()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
    }

    private fun onFailure() {
    }

    fun getErrorObserver(): Observer<Throwable> {
        return errorObserver
    }

    fun getFailureResponseObserver(): Observer<FailureResponse> {
        return failureResponseObserver
    }

    private fun onErrorOccurred() {
    }

}