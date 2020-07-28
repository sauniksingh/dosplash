package com.test.dosplash.repo

import com.test.dosplash.base.RichMediatorLiveData
import com.test.dosplash.data.DataManager
import com.test.dosplash.data.NetworkCallback
import com.test.dosplash.error.FailureResponse
import com.test.dosplash.model.UnsplashImage

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
class ImageListRepo {
    fun getImages(
        imageLiveData: RichMediatorLiveData<ArrayList<UnsplashImage>?>,
        page: Int,
        item: Int
    ) {
        DataManager.getInstance().getImages(page, item).enqueue(object :
            NetworkCallback<ArrayList<UnsplashImage>?>() {
            override fun onSuccess(t: ArrayList<UnsplashImage>?) {
                imageLiveData.value = t
            }

            override fun onFailure(failureResponse: FailureResponse?) {
                failureResponse?.apply {
                    imageLiveData.setFailure(failureResponse)
                }
            }

            override fun onError(t: Throwable?) {
                t?.apply {
                    imageLiveData.setError(t)
                }
            }
        })
    }

}