package com.test.dosplash.viewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.test.dosplash.AppExecutors
import com.test.dosplash.base.RichMediatorLiveData
import com.test.dosplash.error.FailureResponse
import com.test.dosplash.model.UnsplashImage
import com.test.dosplash.repo.ImageListRepo

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
class ImageListViewModel : ViewModel() {
    private var mFailureObserver: Observer<FailureResponse>? = null
    private var mErrorObserver: Observer<Throwable>? = null
    var imageListData: RichMediatorLiveData<ArrayList<UnsplashImage>?>? = null
    private val imageListRepo = ImageListRepo()

    fun setGenericListeners(
        errorObserver: Observer<Throwable>,
        failureResponseObserver: Observer<FailureResponse>
    ) {
        mErrorObserver = errorObserver
        mFailureObserver = failureResponseObserver
        initLiveData()
    }

    private fun initLiveData() {
        if (imageListData == null) {
            imageListData = object : RichMediatorLiveData<ArrayList<UnsplashImage>?>() {
                override fun getFailureObserver(): Observer<FailureResponse> {
                    return mFailureObserver!!
                }

                override fun getErrorObserver(): Observer<Throwable> {
                    return mErrorObserver!!
                }
            }
        }
    }

    fun getImages(page: Int, item: Int) {
        imageListData?.apply {
            imageListRepo.getImages(imageListData!!, page, item)
        }
    }
}