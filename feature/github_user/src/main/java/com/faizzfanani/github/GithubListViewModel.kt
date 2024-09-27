package com.faizzfanani.github

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.faizzfanani.core.base.BaseViewModel
import com.faizzfanani.core.utils.Event
import com.faizzfanani.service_github.domain.model.GithubUser
import com.faizzfanani.service_github.domain.usecase.GetGithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubListViewModel @Inject constructor(
    private val getGithubUserUseCase: GetGithubUserUseCase
): BaseViewModel() {

    init {
        addUseCase(getGithubUserUseCase)
    }

    val successEvent = MutableLiveData<Event<List<GithubUser>>>()
    val errorEvent = MutableLiveData<Event<String>>()
    val onLoadingEvent = MutableLiveData<Event<Boolean>>()

    val pageSize = 10
    val isLastPage = false

    fun getGithubUser(username: String){
        viewModelScope.launch {
            getGithubUserUseCase.execute(username = username,
                output = GetGithubUserUseCase.Output(
                    success = {
                        successEvent.value = Event(it)
                    },
                    error = {
                        errorEvent.value = Event(it)
                    },
                    loading = {
                        onLoadingEvent.value = Event(it)
                    }
                )
            )
        }
    }
}