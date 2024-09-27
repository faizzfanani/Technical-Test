package com.faizzfanani.core.domain.entity

data class Version(
    private val major:Int,
    private val minor:Int,
    private val revision:Int
){
    private fun getVersionCode():Long{
        return (major.toLong() * 1000000) + (minor * 1000) + revision
    }

    fun display():String{
        return "$major.$minor.$revision"
    }

    operator fun compareTo(version: Version):Int{
        return getVersionCode().compareTo(version.getVersionCode())
    }

    companion object{
        fun create(versionName:String): Version {
            val splits = versionName.split(".").toTypedArray().map { it.toIntOrNull()?:0 }
            return Version(splits[0], splits[1], splits[2])
        }
    }
}