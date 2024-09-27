package com.faizzfanani.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import com.faizzfanani.core.domain.usecase.BaseUseCaseImpl
import com.faizzfanani.core.domain.usecase.IBaseUseCase
import com.faizzfanani.core.domain.util.AppInfo

@Module
@InstallIn(ActivityComponent::class)
object BaseModule {

    @Provides
    fun provideBaseUseCase(
        appInfo: AppInfo
    ): IBaseUseCase = BaseUseCaseImpl(appInfo)
}