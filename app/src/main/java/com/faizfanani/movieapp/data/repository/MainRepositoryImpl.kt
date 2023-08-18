package com.faizfanani.movieapp.data.repository

import com.faizfanani.movieapp.data.converter.toGenreUI
import com.faizfanani.movieapp.data.converter.toMovieDetailUI
import com.faizfanani.movieapp.data.converter.toMovieUI
import com.faizfanani.movieapp.data.network.api.MainApi
import com.faizfanani.movieapp.interactor.uimodel.Genre
import com.faizfanani.movieapp.interactor.uimodel.Movie
import com.faizfanani.movieapp.interactor.uimodel.MovieDetail
import com.faizfanani.movieapp.interactor.util.StatusResult
import com.faizfanani.movieapp.utils.*
import com.faizfanani.movieapp.utils.exception.CustomMessageException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Created by Moh.Faiz Fanani on 17/03/2023
 */
class MainRepositoryImpl @Inject constructor(
    private val mainApi: MainApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @ApiHeader private val apiHeader: String,
    @AccessToken private val accessToken: String,
    @ImageUrl private val imageUrl: String
): MainRepository {

    override suspend fun getGenreList(): StatusResult<List<Genre>> {
        return withContext(ioDispatcher) {
            try {
                val reqHeader = hashMapOf(
                    apiHeader to accessToken,
                )
                val response = mainApi.getGenreList(reqHeader)
                val genreList = response?.genreList
                if (!genreList.isNullOrEmpty()) {
                    return@withContext StatusResult.Success(genreList.map { it.toGenreUI() })
                }
                throw CustomMessageException(Constants.DATA_IS_EMPTY)
            } catch (e: Exception) {
                Timber.tag("Get genres").e(e)
                return@withContext StatusResult.Error(e)
            }
        }
    }

    override suspend fun getMovies(genre: String?, page: Int): StatusResult<List<Movie>> {
        return withContext(ioDispatcher) {
            try {
                val reqHeader = hashMapOf(
                    apiHeader to accessToken,
                )
                val response = mainApi.getMovies(reqHeader, genre, page)
                val movieList = response.movieList
                if (movieList.isEmpty()) {
                    throw CustomMessageException(Constants.DATA_IS_EMPTY)
                } else {
                    return@withContext StatusResult.Success(
                        movieList.map { movie ->
                            val date = movie.releaseDate?.let {
                                StringUtils.formatStringToDate(
                                    it,
                                    Constants.ORIGINAL_DATE_FORMAT,
                                    Locale.ENGLISH
                                )
                            }
                            val formattedDate = StringUtils.formatDateTime(
                                date,
                                Constants.DISPLAY_DATE_FORMAT,
                                Locale("en")
                            )
                            movie.copy(
                                posterPath = imageUrl + movie.posterPath,
                                backdropPath = imageUrl + movie.backdropPath
                            ).toMovieUI(formattedDate)
                        }
                    )
                }
            } catch (e: Exception) {
                Timber.tag("Get movies").e(e)
                return@withContext StatusResult.Error(e)
            }
        }
    }

    override suspend fun searchMovies(keyword: String?, page: Int): StatusResult<List<Movie>> {
        return withContext(ioDispatcher) {
            try {
                val reqHeader = hashMapOf(
                    apiHeader to accessToken,
                )
                val response = mainApi.searchMovies(reqHeader, keyword, page)
                val movieList = response.movieList
                if (movieList.isEmpty()) {
                    throw CustomMessageException(Constants.DATA_IS_EMPTY)
                } else {
                    return@withContext StatusResult.Success(
                        movieList.map { movie ->
                            val date = movie.releaseDate?.let {
                                StringUtils.formatStringToDate(
                                    it,
                                    Constants.ORIGINAL_DATE_FORMAT,
                                    Locale.ENGLISH
                                )
                            }
                            val formattedDate = StringUtils.formatDateTime(
                                date,
                                Constants.DISPLAY_DATE_FORMAT,
                                Locale("en")
                            )
                            movie.copy(
                                posterPath = imageUrl + movie.posterPath,
                                backdropPath = imageUrl + movie.backdropPath
                            ).toMovieUI(formattedDate)
                        }
                    )
                }
            } catch (e: Exception) {
                Timber.tag("Search movies").e(e)
                return@withContext StatusResult.Error(e)
            }
        }
    }
    override suspend fun getMovieDetail(movieID: Int): StatusResult<MovieDetail> {
        return withContext(ioDispatcher) {
            try {
                val reqHeader = hashMapOf(
                    apiHeader to accessToken,
                )
                val response = mainApi.getDetailMovie(reqHeader, movieID)

                val genres = response.genres?.map { it.toGenreUI() }
                val date = response.releaseDate?.let {
                    StringUtils.formatStringToDate(
                        it,
                        Constants.ORIGINAL_DATE_FORMAT,
                        Locale.ENGLISH
                    )
                }
                val formattedDate = StringUtils.formatDateTime(
                    date,
                    Constants.DISPLAY_DATE_FORMAT,
                    Locale("en")
                )

                return@withContext StatusResult.Success(
                    response.copy(
                        posterPath = imageUrl + response.posterPath,
                        backdropPath = imageUrl + response.backdropPath
                    ).toMovieDetailUI(formattedDate, genres)
                )
            } catch (e: Exception) {
                Timber.tag("Get movie detail").e(e)
                return@withContext StatusResult.Error(e)
            }
        }
    }
}