package com.faizzfanani.service_github.data

import com.faizzfanani.service_github.domain.api.GithubApi
import com.faizzfanani.service_github.domain.converter.remoteToDomain
import com.faizzfanani.service_github.domain.model.GithubUser
import javax.inject.Inject

class GithubApiImpl @Inject constructor(
    private val api: GithubApiService
) : GithubApi {
    override suspend fun getGithubUser(): List<GithubUser> {
        return api.getUser().map { it.remoteToDomain() }
    }

    override suspend fun searchGithubUser(username: String): GithubUser {
        return api.searchUser(username).remoteToDomain()
    }
}