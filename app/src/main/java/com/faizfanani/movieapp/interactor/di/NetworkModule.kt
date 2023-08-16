package com.faizfanani.movieapp.interactor.di

import android.content.Context
import android.content.pm.ApplicationInfo
import com.faizfanani.movieapp.data.network.api.MainApi
import com.faizfanani.movieapp.data.network.interceptor.NoConnectionInterceptor
import com.faizfanani.movieapp.utils.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @ApiHeader
    @Singleton
    @Provides
    fun provideApiHeader(@ApplicationContext appContext: Context): String {
        return getStringMetadata(appContext, "API_HEADER")!!
    }

    @AccessToken
    @Singleton
    @Provides
    fun provideAccessToken(@ApplicationContext appContext: Context): String {
        return getStringMetadata(appContext, "API_READ_ACCESS_TOKEN")!!
    }

    @ServiceUrl
    @Singleton
    @Provides
    fun provideServiceUrl(@ApplicationContext appContext: Context): String {
        return getStringMetadata(appContext, "SERVICE_URL")!!
    }

    @Singleton
    @Provides
    fun provideNoConnectionInterceptor(@ApplicationContext appContext: Context) =
        NoConnectionInterceptor(appContext)

    @AuthNetworkInterceptorClient
    @Singleton
    @Provides
    fun provideAuthNetworkInterceptor(
        @ApplicationContext appContext: Context,
        noConnectionInterceptor: NoConnectionInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            readTimeout(30, TimeUnit.SECONDS)
            writeTimeout(30, TimeUnit.SECONDS)
            connectTimeout(10, TimeUnit.SECONDS)
            addInterceptor(noConnectionInterceptor)
            if ((appContext.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            }
            cache(null)
        }.build()
    }

    @ServiceRetrofit
    @Singleton
    @Provides
    fun provideServiceRetrofit(
        @ServiceUrl baseUrl: String,
        @AuthNetworkInterceptorClient client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(baseUrl)
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build()
    }

    @Singleton
    @Provides
    fun provideMainApi(@ServiceRetrofit retrofit: Retrofit): MainApi {
        return retrofit.create(MainApi::class.java)
    }
}