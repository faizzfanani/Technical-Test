package com.faizzfanani.core.utils

import android.content.Context
import android.content.pm.PackageManager
import javax.inject.Qualifier

//base url qualifiers
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GithubBaseUrl

//key qualifiers
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GithubApiToken

//retrofit qualifiers
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GithubRetrofit

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
    }

    return null
}