package com.faizfanani.movieapp.data.repository

import com.faizfanani.movieapp.interactor.uimodel.Genre
import com.faizfanani.movieapp.interactor.uimodel.Movie
import com.faizfanani.movieapp.interactor.util.StatusResult

/**
 * Created by Moh.Faiz Fanani on 01/08/2023
 */
interface MainRepository {
    suspend fun getGenreList(): StatusResult<List<Genre>>
    suspend fun getMovies(genre: String?, page: Int) : StatusResult<List<Movie>>
}