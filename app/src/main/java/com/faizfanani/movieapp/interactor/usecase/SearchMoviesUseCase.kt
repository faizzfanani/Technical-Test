package com.faizfanani.movieapp.interactor.usecase

import com.faizfanani.movieapp.data.repository.MainRepository
import com.faizfanani.movieapp.interactor.uimodel.Movie
import com.faizfanani.movieapp.interactor.util.StatusResult
import javax.inject.Inject

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */
interface SearchMoviesUseCase {
    suspend operator fun invoke(keyword: String?, page: Int): StatusResult<List<Movie>>
}

class SearchMoviesUseCaseImpl @Inject constructor(private val mainRepository: MainRepository): SearchMoviesUseCase{
    override suspend fun invoke(keyword: String?, page: Int): StatusResult<List<Movie>> {
        return mainRepository.searchMovies(keyword, page)
    }
}