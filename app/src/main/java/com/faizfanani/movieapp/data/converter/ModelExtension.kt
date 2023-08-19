package com.faizfanani.movieapp.data.converter

import com.faizfanani.movieapp.data.network.model.*
import com.faizfanani.movieapp.interactor.uimodel.*

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

fun MovieDetailResponse.toMovieDetailUI(formattedDate: String?, genres: List<Genre>?) = MovieDetail(
    id = id ?: 0,
    isAdult = isAdult ?: false,
    originalTitle = originalTitle ?: "",
    title = title ?: "",
    genres = genres ?: listOf(),
    overview = overview ?: "",
    originalLanguage = originalLanguage ?: "",
    releaseDate = formattedDate ?: "",
    voteAverage = voteAverage ?: 0.0,
    voteCount = voteCount ?: 0,
    backdropPath = backdropPath ?: "",
    posterPath = posterPath ?: "",
)

fun ReviewResponse.toReviewUI(formattedDate: String?, author: Author?) = Review(
    id = id ?: "",
    content = content ?: "",
    createdAt = formattedDate ?: "",
    author = author
)

fun AuthorResponse.toAuthorUI() = Author(
    name = name ?: "",
    username = username ?: "",
    avatarPath = avatarPath ?: "",
    rating = rating ?: 0
)
