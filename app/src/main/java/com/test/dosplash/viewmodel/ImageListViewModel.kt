package com.test.dosplash.viewmodel

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.test.dosplash.error.FailureResponse

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
class ImageListViewModel : ViewModel() {
    private var mFailureObserver: Observer<FailureResponse>? = null
    private var mErrorObserver: Observer<Throwable>? = null
}