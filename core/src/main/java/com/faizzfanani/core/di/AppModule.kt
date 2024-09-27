package com.faizzfanani.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import com.faizzfanani.core.domain.util.AppInfo
import com.faizzfanani.core.utils.AppInfoImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppInfo(@ApplicationContext context: Context): AppInfo{
        return AppInfoImpl(context)
    }
}