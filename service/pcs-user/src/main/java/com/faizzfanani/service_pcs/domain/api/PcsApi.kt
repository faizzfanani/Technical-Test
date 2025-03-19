package com.faizzfanani.service_pcs.domain.api

import com.faizzfanani.service_pcs.domain.model.PcsUser


interface PcsApi {
    suspend fun getUserList(): List<PcsUser>
}