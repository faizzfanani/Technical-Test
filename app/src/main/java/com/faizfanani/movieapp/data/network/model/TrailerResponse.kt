package com.faizfanani.movieapp.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */
data class TrailerResponse(
    @SerializedName("results")
    val trailer: List<VideoKeyResponse>?
)

data class VideoKeyResponse(
    @SerializedName("key")
    val videoUrl: String?,
)
