package com.faizfanani.movieapp.interactor.usecase

import com.faizfanani.movieapp.data.repository.MainRepository
import com.faizfanani.movieapp.interactor.util.StatusResult
import javax.inject.Inject

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */
interface GetTrailerUrlUseCase {
    suspend operator fun invoke(movieID: Int): StatusResult<String>
}

class GetTrailerUrlUseCaseImpl @Inject constructor(private val mainRepository: MainRepository): GetTrailerUrlUseCase{
    override suspend fun invoke(movieID: Int): StatusResult<String> {
        return mainRepository.getVideoUrl(movieID)
    }
}