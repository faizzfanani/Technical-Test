package com.faizzfanani.service_pcs.di

import com.faizzfanani.core.utils.PcsRetrofit
import com.faizzfanani.service_pcs.data.PcsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitApiServiceModule {

    @Provides
    @Singleton
    fun providePcsApiService(@PcsRetrofit retrofit: Retrofit): PcsApiService {
        return retrofit.create(PcsApiService::class.java)
    }
}