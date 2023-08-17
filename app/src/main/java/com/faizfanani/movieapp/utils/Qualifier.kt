package com.faizfanani.movieapp.utils

import javax.inject.Qualifier

/**
 * Created by Moh.Faiz Fanani on 17/03/2023
 */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ServiceUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ImageUrl

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ServiceRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthNetworkInterceptorClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiHeader

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AccessToken