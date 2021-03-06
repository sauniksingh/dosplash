package com.test.dosplash.ui.splash

import android.content.Intent
import android.os.Bundle
import com.test.dosplash.R
import com.test.dosplash.base.BaseActivity
import com.test.dosplash.ui.image.ImageListActivity
import java.util.*

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Timer().schedule(object : TimerTask() {
            override fun run() {
                startActivity(Intent(this@SplashActivity, ImageListActivity::class.java))
                finish()
            }
        }, 5000)
    }
}