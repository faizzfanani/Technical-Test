package com.faizfanani.movieapp.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faizfanani.movieapp.interactor.uimodel.Genre
import com.faizfanani.movieapp.interactor.uimodel.Movie
import com.faizfanani.movieapp.interactor.usecase.GetGenreUseCase
import com.faizfanani.movieapp.interactor.usecase.GetMoviesUseCase
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
class HomeViewModel @Inject constructor(
    private val getGenreUseCase: GetGenreUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
) : ViewModel(){

    companion object {
        private const val FIRST_PAGE = 1
        const val SIZE = 20
    }

    val genre = MutableLiveData<StatusResult<List<Genre>>>()
    val movie = MutableLiveData<StatusResult<List<Movie>>>()
    private val tempList = arrayListOf<Movie>()
    private var currentPage = FIRST_PAGE
    val genreName = MutableLiveData<String>()
    var isLastPage = false

    private fun getGenre() {
        genre.postValue(StatusResult.Loading())
        viewModelScope.launch {
            getGenreUseCase()
                .onSuccess {
                    genre.postValue(StatusResult.Success(it))
                }
                .onErrorException { e, _ ->
                    genre.postValue(StatusResult.Error(e))
                }
        }
    }

    fun getMovies() {
        movie.postValue(StatusResult.Loading())
        viewModelScope.launch {
            getMoviesUseCase(genre = genreName.value, page = currentPage)
                .onSuccess {
                    isLastPage = if (it.size == SIZE) {
                        currentPage++
                        false
                    } else {
                        true
                    }
                    tempList.addAll(it)
                    movie.postValue(StatusResult.Success(tempList))
                }
                .onErrorException { e, _ ->
                    movie.postValue(StatusResult.Error(e))
                }
        }
    }
    fun refresh() {
        currentPage = FIRST_PAGE
        getGenre()
        getMovies()
    }
}