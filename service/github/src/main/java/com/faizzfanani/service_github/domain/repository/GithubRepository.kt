package com.faizzfanani.service_github.domain.repository

import com.faizzfanani.core.utils.Result
import com.faizzfanani.core_storage.dao.GithubDao
import com.faizzfanani.service_github.domain.api.GithubApi
import com.faizzfanani.service_github.domain.converter.domainToEntity
import com.faizzfanani.service_github.domain.converter.entityToDomain
import com.faizzfanani.service_github.domain.model.GithubUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubApi: GithubApi,
    private val githubDao: GithubDao
) {
    fun getGithubUser(username: String): Flow<Result<List<GithubUser>>> {
        return flow {
            try {
                val source = githubApi.getGithubUser(username)
                val localData = githubDao.getUsers().map { it.entityToDomain() }
                if (source.isNotEmpty()){
                    if (localData != source)
                        githubDao.insertUsers(source.map { it.domainToEntity() })
                    emit(Result.Success(localData))
                }
            }catch (e: Exception){
                emit(Result.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}