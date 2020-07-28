package com.test.dosplash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
class ImageViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ImageListViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}