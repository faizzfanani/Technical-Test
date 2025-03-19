package com.faizzfanani.service_pcs.domain.repository

import com.faizzfanani.core.utils.Result
import com.faizzfanani.core_storage.dao.PcsDao
import com.faizzfanani.service_pcs.domain.api.PcsApi
import com.faizzfanani.service_pcs.domain.converter.domainToEntity
import com.faizzfanani.service_pcs.domain.converter.entityToDomain
import com.faizzfanani.service_pcs.domain.model.PcsUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PcsRepository @Inject constructor(
    private val pcsApi: PcsApi,
    private val pcsDao: PcsDao
) {
    fun getPcsUserList(): Flow<Result<List<PcsUser>>> {
        return flow {
            try {
                val source = pcsApi.getUserList()
                val localData = pcsDao.getUsers().map { it.entityToDomain() }
                if (source.isNotEmpty()){
                    if (localData != source)
                        pcsDao.insertUsers(source.map { it.domainToEntity() })
                    emit(Result.Success(localData))
                }
            }catch (e: Exception){
                emit(Result.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getPcsUserDetail(id: String): Flow<Result<PcsUser>> {
        return flow {
            try {
                val localData = pcsDao.getUserById(id).entityToDomain()
                emit(Result.Success(localData))
            }catch (e: Exception){
                emit(Result.Error("User not found"))
            }
        }.flowOn(Dispatchers.IO)
    }
}