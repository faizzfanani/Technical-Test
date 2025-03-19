package com.faizzfanani.service_pcs.data

import com.faizzfanani.service_pcs.data.remote.response.PcsUserResponse
import retrofit2.http.GET

interface PcsApiService {

    @GET("getData/test")
    suspend fun getUser() : List<PcsUserResponse>

}