package com.faizfanani.movieapp.interactor.usecase

import com.faizfanani.movieapp.data.repository.MainRepository
import com.faizfanani.movieapp.interactor.uimodel.Genre
import com.faizfanani.movieapp.interactor.util.StatusResult
import javax.inject.Inject

/**
 * Created by Moh.Faiz Fanani on 01/08/2023
 */
interface GetGenreUseCase {
    suspend operator fun invoke(): StatusResult<List<Genre>>
}

class GetGenreUseCaseImpl @Inject constructor(private val mainRepository: MainRepository): GetGenreUseCase{
    override suspend fun invoke(): StatusResult<List<Genre>> {
        return mainRepository.getGenreList()
    }
}