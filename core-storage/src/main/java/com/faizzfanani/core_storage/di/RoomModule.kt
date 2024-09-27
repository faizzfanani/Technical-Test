package com.faizzfanani.core_storage.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.faizzfanani.core_storage.database.LocalStorageDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {
    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext appContext: Context): LocalStorageDatabase {
        return Room.databaseBuilder(
            appContext, LocalStorageDatabase::class.java, "Local-Storage.db"
        ).build()
    }
}