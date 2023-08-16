package com.faizfanani.movieapp.interactor.usecase

import com.faizfanani.movieapp.data.repository.MainRepository
import com.faizfanani.movieapp.interactor.uimodel.Movie
import com.faizfanani.movieapp.interactor.util.StatusResult
import javax.inject.Inject

/**
 * Created by Moh.Faiz Fanani on 01/08/2023
 */
interface GetMoviesUseCase {
    suspend operator fun invoke(genre: String, page: Int): StatusResult<List<Movie>>
}

class GetMoviesUseCaseImpl @Inject constructor(private val mainRepository: MainRepository): GetMoviesUseCase{
    override suspend fun invoke(genre: String, page: Int): StatusResult<List<Movie>> {
        return mainRepository.getMovieByGenre(genre, page)
    }
}