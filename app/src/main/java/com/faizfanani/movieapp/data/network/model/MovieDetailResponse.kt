package com.faizfanani.movieapp.data.network.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */

data class MovieDetailResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("adult")
    val isAdult: Boolean?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("genres")
    val genres: List<GenreResponse>?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
)
