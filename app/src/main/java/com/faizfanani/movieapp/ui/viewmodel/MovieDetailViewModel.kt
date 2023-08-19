package com.faizfanani.movieapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faizfanani.movieapp.interactor.uimodel.MovieDetail
import com.faizfanani.movieapp.interactor.uimodel.Review
import com.faizfanani.movieapp.interactor.usecase.GetMovieDetailUseCase
import com.faizfanani.movieapp.interactor.usecase.GetReviewsUseCase
import com.faizfanani.movieapp.interactor.usecase.GetTrailerUrlUseCase
import com.faizfanani.movieapp.interactor.util.StatusResult
import com.faizfanani.movieapp.interactor.util.onErrorException
import com.faizfanani.movieapp.interactor.util.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Moh.Faiz Fanani on 17/08/2023
 */
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getReviewsUseCase: GetReviewsUseCase,
    private val getTrailerUrlUseCase: GetTrailerUrlUseCase,
) : ViewModel(){

    val movieDetail = MutableLiveData<StatusResult<MovieDetail>>()
    val reviews = MutableLiveData<StatusResult<List<Review>>>()
    val trailerUrl = MutableLiveData<StatusResult<String>>()
    val movieID = MutableLiveData<Int>()

    fun fetch() {
        movieID.value?.let { movieID ->
            movieDetail.postValue(StatusResult.Loading())
            reviews.postValue(StatusResult.Loading())
            trailerUrl.postValue(StatusResult.Loading())
            viewModelScope.launch {
                getMovieDetailUseCase(movieID)
                    .onSuccess {
                        movieDetail.postValue(StatusResult.Success(it))
                    }
                    .onErrorException { e, _ ->
                        movieDetail.postValue(StatusResult.Error(e))
                    }
                getReviewsUseCase(movieID)
                    .onSuccess {
                        reviews.postValue(StatusResult.Success(it))
                    }
                    .onErrorException { e, _ ->
                        reviews.postValue(StatusResult.Error(e))
                    }
                getTrailerUrlUseCase(movieID)
                    .onSuccess {
                        trailerUrl.postValue(StatusResult.Success(it))
                    }
                    .onErrorException { e, _ ->
                        trailerUrl.postValue(StatusResult.Error(e))
                    }
            }
        }
    }
}