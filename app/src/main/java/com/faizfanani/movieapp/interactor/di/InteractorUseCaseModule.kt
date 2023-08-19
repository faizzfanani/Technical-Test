package com.faizfanani.movieapp.interactor.di

import com.faizfanani.movieapp.interactor.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */

@Module
@InstallIn(ViewModelComponent::class)
abstract class InteractorUseCaseModule {
    @ViewModelScoped
    @Binds
    abstract fun bindGetGenreUseCase(impl: GetGenreUseCaseImpl): GetGenreUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindGetMoviesUseCase(impl: GetMoviesUseCaseImpl): GetMoviesUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindSearchMoviesUseCase(impl: SearchMoviesUseCaseImpl): SearchMoviesUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindGetMovieDetailUseCase(impl: GetMovieDetailUseCaseImpl): GetMovieDetailUseCase

    @ViewModelScoped
    @Binds
    abstract fun bindGetReviewsUseCase(impl: GetReviewsUseCaseImpl): GetReviewsUseCase
}