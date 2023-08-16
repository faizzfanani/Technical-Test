package com.faizfanani.movieapp.utils

import android.content.Context
import android.content.pm.PackageManager
import android.util.DisplayMetrics
import android.view.WindowManager
import timber.log.Timber

/**
 * Created by Moh.Faiz Fanani on 19/03/2023
 */
fun getStringMetadata(context: Context, name: String): String? {
    try {
        val appInfo = context.packageManager.getApplicationInfo(
            context.packageName, PackageManager.GET_META_DATA
        )
        if (appInfo.metaData != null) {
            return appInfo.metaData.getString(name)
        }
    } catch (e: PackageManager.NameNotFoundException) {
        // if we can’t find it in the manifest, just return null
        Timber.tag("Str Meta Data").e(e)
    }

    return null
}
fun getScreenHeight(mContext: Context): Float {
    val displayMetrics = DisplayMetrics()
    val windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return displayMetrics.heightPixels.toFloat()
}
