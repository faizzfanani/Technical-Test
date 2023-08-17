package com.faizfanani.movieapp.data.converter

import com.faizfanani.movieapp.interactor.uimodel.Genre
import com.faizfanani.movieapp.interactor.uimodel.Movie
import com.faizfanani.movieapp.data.network.model.GenreResponse
import com.faizfanani.movieapp.data.network.model.MovieResponse

/**
 * Created by Moh.Faiz Fanani on 01/08/2023
 */

fun GenreResponse.toGenreUI() = Genre(
    genreId = genreId,
    genreName = genreName
)

fun MovieResponse.toMovieUI(formattedDate: String?) = Movie(
    id = id ?: 0,
    isAdult = isAdult ?: false,
    originalTitle = originalTitle ?: "",
    title = title ?: "",
    genreIDs = genreIDs ?: listOf(),
    overview = overview ?: "",
    originalLanguage = originalLanguage ?: "",
    releaseDate = formattedDate ?: "",
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0,
    backdropPath = backdropPath ?: "",
    posterPath = posterPath ?: "",
)
