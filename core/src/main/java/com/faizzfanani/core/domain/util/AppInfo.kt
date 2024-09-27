package com.faizzfanani.core.domain.util

import com.faizzfanani.core.domain.entity.Version

interface AppInfo {
    fun getAppVersion(): Version
    fun isInternetAvailable():Boolean
    fun isStaging(): Boolean
}