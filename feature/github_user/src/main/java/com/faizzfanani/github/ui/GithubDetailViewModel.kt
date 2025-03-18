package com.faizzfanani.github.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.faizzfanani.core.base.BaseViewModel
import com.faizzfanani.core.utils.Event
import com.faizzfanani.github.GithubFeatureConstant.Companion.EMPTY_STRING
import com.faizzfanani.service_github.domain.model.GithubUser
import com.faizzfanani.service_github.domain.usecase.SearchGithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubDetailViewModel @Inject constructor(
    private val searchGithubUserUseCase: SearchGithubUserUseCase,
): BaseViewModel() {

    init {
        addUseCase(searchGithubUserUseCase)
    }

    val successDetailEvent = MutableLiveData<Event<GithubUser>>()
    val errorEvent = MutableLiveData<Event<String>>()
    val onLoadingEvent = MutableLiveData<Event<Boolean>>()
    var isLoading = false
    var username = EMPTY_STRING

    fun getUserDetailByUsername(){
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