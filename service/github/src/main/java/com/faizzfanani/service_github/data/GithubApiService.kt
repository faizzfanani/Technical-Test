package com.faizzfanani.service_github.data

import com.faizzfanani.service_github.data.remote.response.GithubUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    @GET("users/{username}")
    fun getUser(@Query("username") username: String?) : List<GithubUserResponse>
}