package com.faizzfanani.service_github.domain.api

import com.faizzfanani.service_github.domain.model.GithubUser

interface GithubApi {
    suspend fun getGithubUser(
        page: Int,
        size: Int
    ): List<GithubUser>

    suspend fun searchGithubUser(
        username: String
    ): GithubUser
}