package com.faizfanani.movieapp.data.repository

import com.faizfanani.movieapp.data.converter.toGenreUI
import com.faizfanani.movieapp.data.converter.toMovieUI
import com.faizfanani.movieapp.interactor.uimodel.Genre
import com.faizfanani.movieapp.interactor.uimodel.Movie
import com.faizfanani.movieapp.data.network.api.MainApi
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
    @AccessToken private val accessToken: String
): MainRepository {

    override suspend fun getGenreList(): StatusResult<List<Genre>> {
        return withContext(ioDispatcher) {
            try {
                val reqHeader = hashMapOf(
                    apiHeader to accessToken,
                )
                val response = mainApi.getGenreList(reqHeader)
                if (response.status == 200) {
                    val genreList = response.content?.genreList
                    if (genreList.isNullOrEmpty()) {
                        throw CustomMessageException(response.info)
                    }
                    return@withContext StatusResult.Success(genreList.map { it.toGenreUI() })
                }
                throw CustomMessageException(response.info)
            } catch (e: Exception) {
                Timber.tag("Get genres").e(e)
                return@withContext StatusResult.Error(e)
            }
        }
    }

    override suspend fun getMovieByGenre(genre: String, page: Int): StatusResult<List<Movie>> {
        return withContext(ioDispatcher) {
            try {
                val reqHeader = hashMapOf(
                    apiHeader to accessToken,
                )
                val response = mainApi.getMoviesByGenre(reqHeader, genre, page)
                if (response.status == 200) {
                    val movieList = response.content.movieList
                    if (movieList.isEmpty()) {
                        throw CustomMessageException(response.info)
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
                                    Locale("in")
                                )
                                movie.toMovieUI(formattedDate)
                            }
                        )
                    }
                }
                throw CustomMessageException(response.info)
            } catch (e: Exception) {
                Timber.tag("Get movies by genre").e(e)
                return@withContext StatusResult.Error(e)
            }
        }
    }
}