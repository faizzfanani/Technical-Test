package com.faizzfanani.service_github.domain.api

import com.faizzfanani.service_github.domain.model.GithubUser

interface GithubApi {
    suspend fun getGithubUser(
        username: String
    ): List<GithubUser>
}