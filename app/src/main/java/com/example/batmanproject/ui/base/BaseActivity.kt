package com.example.batmanproject.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.batmanproject.R

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}