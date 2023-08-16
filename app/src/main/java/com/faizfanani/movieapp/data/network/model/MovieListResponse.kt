package com.faizfanani.movieapp.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Moh.Faiz Fanani on 01/08/2023
 */
data class MovieListResponse(
    @SerializedName("results")
    val movieList: List<MovieResponse>,
)
