package com.faizzfanani.service_github.data

import com.faizzfanani.service_github.data.remote.response.GithubUserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {

    @GET("users")
    suspend fun getUser() : List<GithubUserResponse>

    @GET("users/{username}")
    suspend fun searchUser(@Path("username") username: String) : GithubUserResponse
}