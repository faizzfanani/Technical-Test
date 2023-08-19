package com.faizfanani.movieapp.interactor.usecase

import com.faizfanani.movieapp.data.repository.MainRepository
import com.faizfanani.movieapp.interactor.uimodel.Review
import com.faizfanani.movieapp.interactor.util.StatusResult
import javax.inject.Inject

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */
interface GetReviewsUseCase {
    suspend operator fun invoke(movieID: Int): StatusResult<List<Review>>
}

class GetReviewsUseCaseImpl @Inject constructor(private val mainRepository: MainRepository): GetReviewsUseCase{
    override suspend fun invoke(movieID: Int): StatusResult<List<Review>> {
        return mainRepository.getReviews(movieID)
    }
}