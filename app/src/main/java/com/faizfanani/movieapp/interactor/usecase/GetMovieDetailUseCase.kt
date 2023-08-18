package com.faizfanani.movieapp.interactor.usecase

import com.faizfanani.movieapp.data.repository.MainRepository
import com.faizfanani.movieapp.interactor.uimodel.MovieDetail
import com.faizfanani.movieapp.interactor.util.StatusResult
import javax.inject.Inject

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */
interface GetMovieDetailUseCase {
    suspend operator fun invoke(movieID: Int): StatusResult<MovieDetail>
}

class GetMovieDetailUseCaseImpl @Inject constructor(private val mainRepository: MainRepository): GetMovieDetailUseCase{
    override suspend fun invoke(movieID: Int): StatusResult<MovieDetail> {
        return mainRepository.getMovieDetail(movieID)
    }
}