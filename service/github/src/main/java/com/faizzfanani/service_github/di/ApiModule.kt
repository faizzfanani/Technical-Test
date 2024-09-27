package com.faizzfanani.service_github.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.faizzfanani.service_github.data.GithubApiImpl
import com.faizzfanani.service_github.domain.api.GithubApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {
    @Binds
    @Singleton
    abstract fun bindGithubApi(api: GithubApiImpl): GithubApi
}