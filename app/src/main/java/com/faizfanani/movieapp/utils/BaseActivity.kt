@file:Suppress("OverrideDeprecatedMigration")

package com.faizfanani.movieapp.utils

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    private var mLastClickTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        this.requestedOrientation = if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isTaskRoot && canOpenHome) {
                    val homeIntent = Intent(applicationContext, Class.forName("com.faizfanani.movieapp.view.ui.MainActivity"))
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(homeIntent)
                }
            }
        })
        super.onCreate(savedInstanceState)
    }

    open var canOpenHome = true

    protected fun View.clickWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
        this.setOnClickListener { actionWithDebounce(debounceTime, action) }
    }

    protected fun actionWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < debounceTime) return
        else action()

        mLastClickTime = SystemClock.elapsedRealtime()
    }
}