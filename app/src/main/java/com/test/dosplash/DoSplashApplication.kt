package com.test.dosplash

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import com.test.dosplash.data.DataManager

/**
 * Created by Saunik Singh on 28-07-2020.
 * Bada Business
 */
class DoSplashApplication : Application(), LifecycleObserver {
    override fun onCreate() {
        super.onCreate()
        val dataManager: DataManager =
            DataManager.init(this)
        dataManager.initApiManager()
    }
}