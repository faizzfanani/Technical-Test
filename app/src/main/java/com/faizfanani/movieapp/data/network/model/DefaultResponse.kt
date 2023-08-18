package com.faizfanani.movieapp.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */
data class DefaultResponse(
    @SerializedName("status_code")
    val responseCode: String,
    @SerializedName("status_message")
    val message: String
)
