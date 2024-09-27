package com.faizzfanani.core.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.faizzfanani.core.utils.GithubApiToken
import com.faizzfanani.core.utils.GithubBaseUrl
import com.faizzfanani.core.utils.GithubRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.faizzfanani.core.utils.getStringMetadata
import okhttp3.CertificatePinner
import okhttp3.Interceptor
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
    fun provideOkHttpClient(@ApplicationContext appContext: Context, @GithubApiToken apiKey: String): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val referrerInterceptor = Interceptor { chain ->
            val chainBuilder = chain.request()

            val urlWithApiKey = chainBuilder.url.newBuilder()
                .addQueryParameter("apiKey", apiKey)
                .build()

            val request = chainBuilder.newBuilder()
                .url(urlWithApiKey)
                .build()

            chain.proceed(request)
        }

//        val certificatePinner = CertificatePinner.Builder()
//            .add("newsapi.org", "sha256/Y9mvm0exBk1JoQ57f9Vm28jKo5lFm/woKcVxrYxu80o=")
//            .build()

        builder.addInterceptor(referrerInterceptor)
        builder.addInterceptor(ChuckerInterceptor.Builder(appContext).build())
        //builder.certificatePinner(certificatePinner)
        builder.readTimeout(60, TimeUnit.SECONDS)
        builder.connectTimeout(60, TimeUnit.SECONDS)

        return builder.build()
    }

    @GithubRetrofit
    @Provides
    @Singleton
    fun provideNewsRetrofit(okHttpClient: OkHttpClient, @GithubBaseUrl baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}