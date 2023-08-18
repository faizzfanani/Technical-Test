package com.faizfanani.movieapp.interactor.uimodel

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */

data class MovieDetail(
    val id: Int,
    val isAdult: Boolean,
    val originalTitle: String,
    val title: String,
    val genres: List<Genre>,
    val overview: String,
    val originalLanguage: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val backdropPath: String,
    val posterPath: String,
)
