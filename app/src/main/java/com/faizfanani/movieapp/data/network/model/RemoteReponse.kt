package com.faizfanani.movieapp.data.network.model

/**
 * Created by Moh.Faiz Fanani on 01/08/2023
 */
data class RemoteResponse<T>(
    val info: String,
    val status: Int,
    val content: T
)