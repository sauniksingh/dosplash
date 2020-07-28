package com.test.dosplash.splash

import android.content.Intent
import android.os.Bundle
import com.test.dosplash.R
import com.test.dosplash.base.BaseAcitivity
import com.test.dosplash.image.ImageListActivity
import java.util.*

class SplashActivity : BaseAcitivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@SplashActivity, ImageListActivity::class.java))
            }
        }, 5000)
    }
}