package com.faizfanani.technicaltest.presentation

import com.faizzfanani.core.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import id.faizzfanani.technical_test.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun inflate(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater, null, false)
    }

}