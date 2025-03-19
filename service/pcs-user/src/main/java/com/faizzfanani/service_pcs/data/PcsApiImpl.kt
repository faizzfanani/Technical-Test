package com.faizzfanani.service_pcs.data

import com.faizzfanani.service_pcs.domain.api.PcsApi
import com.faizzfanani.service_pcs.domain.converter.remoteToDomain
import com.faizzfanani.service_pcs.domain.model.PcsUser
import javax.inject.Inject

class PcsApiImpl @Inject constructor(
    private val api: PcsApiService
) : PcsApi {

    override suspend fun getUserList(): List<PcsUser> {
        return api.getUser().map { it.remoteToDomain() }
    }
}