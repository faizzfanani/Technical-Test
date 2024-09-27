package com.faizzfanani.service_github.data

import com.faizzfanani.service_github.data.remote.response.GithubUserResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {

    @GET("users")
    suspend fun getUser(
        @Query("q") type: String = "type:user",
        @Query("sort") sort: String = "created",
        @Query("order") order: String = "desc",
        @Query("page") page: Int,
        @Query("per_page") size: Int
    ) : List<GithubUserResponse>

    @GET("users/{username}")
    suspend fun searchUser(@Path("username") username: String) : GithubUserResponse
}