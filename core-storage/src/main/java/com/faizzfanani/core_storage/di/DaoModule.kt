package com.faizzfanani.core_storage.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.faizzfanani.core_storage.dao.GithubDao
import com.faizzfanani.core_storage.database.LocalStorageDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Provides
    @Singleton
    fun provideNewsDao(db: LocalStorageDatabase): GithubDao {
        return db.newsDao()
    }
}