package com.faizzfanani.core.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.faizzfanani.core.domain.entity.Version
import com.faizzfanani.core.domain.util.AppInfo

class AppInfoImpl(private val context: Context): AppInfo {
    override fun getAppVersion(): Version {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName,0)
        return Version.create(packageInfo.versionName)
    }

    override fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
    }

    override fun isStaging(): Boolean {
        return false
    }
}