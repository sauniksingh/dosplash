package com.test.dosplash.splash

import android.os.Bundle
import com.test.dosplash.R
import com.test.dosplash.base.BaseAcitivity

class SplashActivity : BaseAcitivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}