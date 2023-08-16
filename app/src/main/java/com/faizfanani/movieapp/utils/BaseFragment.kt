package com.faizfanani.movieapp.utils

import android.os.SystemClock
import android.view.View
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    private var mLastClickTime: Long = 0

    protected fun View.clickWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
        this.setOnClickListener { actionWithDebounce(debounceTime, action) }
    }

    protected fun actionWithDebounce(debounceTime: Long = 600L, action: () -> Unit) {
        if (SystemClock.elapsedRealtime() - mLastClickTime < debounceTime) return
        else action()

        mLastClickTime = SystemClock.elapsedRealtime()
    }
}