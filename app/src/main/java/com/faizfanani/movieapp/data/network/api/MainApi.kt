package com.faizfanani.movieapp.data.network.api

import com.faizfanani.movieapp.data.network.model.GenreListResponse
import com.faizfanani.movieapp.data.network.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Query

/**
 * Created by Moh.Faiz Fanani on 01/08/2023
 */
interface MainApi {
    @GET("genre/movie/list")
    suspend fun getGenreList(
        @HeaderMap requestHeader: Map<String, String>,
    ): GenreListResponse?

    @GET("discover/movie")
    suspend fun getMovies(
        @HeaderMap requestHeader: Map<String, String>,
        @Query("with_genres") genre: String?,
        @Query("page") page: Int,
    ): MovieListResponse

    @GET("search/movie")
    suspend fun searchMovies(
        @HeaderMap requestHeader: Map<String, String>,
        @Query("query") keyword: String?,
        @Query("page") page: Int,
    ): MovieListResponse

}