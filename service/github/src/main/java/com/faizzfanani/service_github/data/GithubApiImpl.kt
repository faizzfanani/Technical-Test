package com.faizzfanani.service_github.data

import com.faizzfanani.service_github.domain.api.GithubApi
import com.faizzfanani.service_github.domain.converter.remoteToDomain
import com.faizzfanani.service_github.domain.model.GithubUser
import javax.inject.Inject

class GithubApiImpl @Inject constructor(
    private val api: GithubApiService
) : GithubApi {
    override suspend fun getGithubUser(username: String): List<GithubUser> {
        return api.getUser(username).map { it.remoteToDomain() }
    }
}