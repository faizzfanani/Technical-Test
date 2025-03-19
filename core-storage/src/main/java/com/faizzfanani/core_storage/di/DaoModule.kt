package com.faizzfanani.core_storage.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.faizzfanani.core_storage.dao.GithubDao
import com.faizzfanani.core_storage.dao.PcsDao
import com.faizzfanani.core_storage.database.LocalStorageDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DaoModule {
    @Provides
    @Singleton
    fun provideGithubDao(db: LocalStorageDatabase): GithubDao {
        return db.githubDao()
    }

    @Provides
    @Singleton
    fun providePcsDao(db: LocalStorageDatabase): PcsDao {
        return db.pcsDao()
    }
}