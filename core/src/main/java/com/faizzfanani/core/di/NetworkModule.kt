package com.faizzfanani.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.faizzfanani.core.utils.GithubApiToken
import com.faizzfanani.core.utils.GithubBaseUrl
import com.faizzfanani.core.utils.GithubRetrofit
import com.faizzfanani.core.utils.getStringMetadata
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @GithubBaseUrl
    @Singleton
    @Provides
    fun provideNewsUrl(@ApplicationContext appContext: Context): String {
        return getStringMetadata(appContext, "githubBaseUrl")!!
    }

    @GithubApiToken
    @Singleton
    @Provides
    fun provideNewsApiKey(@ApplicationContext appContext: Context): String {
        return getStringMetadata(appContext, "githubApiToken")!!
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(@ApplicationContext appContext: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()

        builder.addInterceptor(ChuckerInterceptor.Builder(appContext).build())
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)

        return builder.build()
    }

    @GithubRetrofit
    @Provides
    @Singleton
    fun provideGithubRetrofit(okHttpClient: OkHttpClient, @GithubBaseUrl baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}