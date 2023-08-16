package com.faizfanani.movieapp.interactor.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import com.faizfanani.movieapp.BuildConfig

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}