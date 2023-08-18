package com.faizfanani.movieapp.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */
data class GenreResponse(
    @SerializedName("id")
    val genreId: Int,
    @SerializedName("name")
    val genreName: String,
)
