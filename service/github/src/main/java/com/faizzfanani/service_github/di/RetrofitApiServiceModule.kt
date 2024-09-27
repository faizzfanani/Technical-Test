package com.faizzfanani.service_github.di

import com.faizzfanani.core.utils.GithubRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.faizzfanani.service_github.data.GithubApiService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitApiServiceModule {

    @Provides
    @Singleton
    fun provideGithubApiService(@GithubRetrofit retrofit: Retrofit): GithubApiService {
        return retrofit.create(GithubApiService::class.java)
    }
}