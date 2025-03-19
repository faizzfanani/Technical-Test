package com.faizzfanani.service_pcs.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.faizzfanani.service_pcs.data.PcsApiImpl
import com.faizzfanani.service_pcs.domain.api.PcsApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {
    @Binds
    @Singleton
    abstract fun bindPcsApi(api: PcsApiImpl): PcsApi
}