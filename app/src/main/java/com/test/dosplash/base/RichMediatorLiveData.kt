package com.test.dosplash.base

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.test.dosplash.error.FailureResponse

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
abstract class RichMediatorLiveData<T>(): MediatorLiveData<T>() {
    private var errorLiveData: MutableLiveData<Throwable>? = null
    private var failureResponseLiveData: MutableLiveData<FailureResponse>? = null

   private fun initLiveData() {
        errorLiveData = MutableLiveData()
        failureResponseLiveData = MutableLiveData<FailureResponse>()
    }

    protected abstract fun getFailureObserver(): Observer<FailureResponse>

    protected abstract fun getErrorObserver(): Observer<Throwable>

    override fun onInactive() {
        super.onInactive()
        removeSource<FailureResponse>(failureResponseLiveData!!)
        removeSource(errorLiveData!!)
    }

    override fun onActive() {
        super.onActive()
        initLiveData()
        addSource<FailureResponse>(failureResponseLiveData!!, getFailureObserver())
        addSource(errorLiveData!!, getErrorObserver())
    }

     fun setFailure(failureResponse: FailureResponse) {
        try {
            failureResponseLiveData!!.setValue(failureResponse)
        } catch (ignore: NullPointerException) {
        }
    }

     fun setError(t: Throwable) {
        try {
            errorLiveData!!.value = t
        } catch (ignore: NullPointerException) {
        }
    }
}