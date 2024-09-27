package com.faizzfanani.github

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.faizzfanani.core.base.BaseViewModel
import com.faizzfanani.core.utils.Event
import com.faizzfanani.service_github.domain.model.GithubUser
import com.faizzfanani.service_github.domain.usecase.GetGithubUserUseCase
import com.faizzfanani.service_github.domain.usecase.SearchGithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubListViewModel @Inject constructor(
    private val getGithubUserUseCase: GetGithubUserUseCase,
    private val searchGithubUserUseCase: SearchGithubUserUseCase,
): BaseViewModel() {

    init {
        addUseCase(getGithubUserUseCase)
        addUseCase(searchGithubUserUseCase)
    }

    val successListEvent = MutableLiveData<Event<List<GithubUser>>>()
    val successDetailEvent = MutableLiveData<Event<GithubUser>>()
    val errorEvent = MutableLiveData<Event<String>>()
    val onLoadingEvent = MutableLiveData<Event<Boolean>>()

    val pageSize = 30
    var currentPage = 1
    var isLastPage = false
    var isLoading = false

    fun getGithubUser(){
        viewModelScope.launch {
            getGithubUserUseCase.execute(
                page = currentPage,
                size = pageSize,
                output = GetGithubUserUseCase.Output(
                    success = {
                        successListEvent.value = Event(it)
                    },
                    error = {
                        errorEvent.value = Event(it)
                    },
                    loading = {
                        onLoadingEvent.value = Event(it)
                        isLoading = it
                    }
                )
            )
        }
    }

    fun searchGithubUser(username: String){
        viewModelScope.launch {
            searchGithubUserUseCase.execute(username = username,
                output = SearchGithubUserUseCase.Output(
                    success = {
                        successDetailEvent.value = Event(it)
                    },
                    error = {
                        errorEvent.value = Event(it)
                    },
                    loading = {
                        onLoadingEvent.value = Event(it)
                        isLoading = it
                    }
                )
            )
        }
    }
}