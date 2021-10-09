package com.example.batmanproject.ui.splash

import android.content.Intent
import android.os.Bundle
import com.example.batmanproject.ui.MainActivity
import com.example.batmanproject.ui.base.BaseActivity

class SplashScreenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}