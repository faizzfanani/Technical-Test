package com.faizzfanani.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity <T: ViewBinding> : AppCompatActivity() {

    protected lateinit var viewBinding: T

    abstract fun inflate(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = inflate()
        setContentView(viewBinding.root)
    }
}
