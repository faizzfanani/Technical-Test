package com.faizzfanani.github.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.faizzfanani.core.base.BaseViewModel
import com.faizzfanani.core.utils.Event
import com.faizzfanani.service_github.domain.model.GithubUser
import com.faizzfanani.service_github.domain.usecase.GetGithubUserUseCase
import com.faizzfanani.service_github.domain.usecase.SearchGithubUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    val successDetailEvent = MutableLiveData<Event<GithubUser>>()
    val errorEvent = MutableLiveData<Event<String>>()
    val onLoadingEvent = MutableLiveData<Event<Boolean>>()

    private val _userList = MutableStateFlow<List<GithubUser>>(emptyList())
    val userList: StateFlow<List<GithubUser>> = _userList

    val pageSize = 30
    var currentPage = 1
    var isLoading = false

    fun getGithubUserList(){
        viewModelScope.launch {
            getGithubUserUseCase.execute(
                page = currentPage,
                size = pageSize,
                output = GetGithubUserUseCase.Output(
                    success = {
                        _userList.value = it
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

    fun userByUsername(username: String){
        viewModelScope.launch {
            searchGithubUserUseCase.execute(username = username,
                output = SearchGithubUserUseCase.Output(
                    success = {
                        _userList.value = listOf(it)
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