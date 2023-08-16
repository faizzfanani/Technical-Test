package com.faizfanani.movieapp.interactor.di

import com.faizfanani.movieapp.data.repository.MainRepository
import com.faizfanani.movieapp.data.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRepository(impl: MainRepositoryImpl): MainRepository
}