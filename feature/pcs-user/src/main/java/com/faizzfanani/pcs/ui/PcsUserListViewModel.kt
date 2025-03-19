package com.faizzfanani.pcs.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.faizzfanani.core.base.BaseViewModel
import com.faizzfanani.core.utils.Event
import com.faizzfanani.service_pcs.domain.model.PcsUser
import com.faizzfanani.service_pcs.domain.usecase.GetPcsUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PcsUserListViewModel @Inject constructor(
    private val getPcsUserListUseCase: GetPcsUserListUseCase,
): BaseViewModel() {

    init {
        addUseCase(getPcsUserListUseCase)
    }

    val errorEvent = MutableLiveData<Event<String>>()
    val onLoadingEvent = MutableLiveData<Event<Boolean>>()

    private val _userList = MutableStateFlow<List<PcsUser>>(emptyList())
    val userList: StateFlow<List<PcsUser>> = _userList

    var isLoading = false

    fun getPcsUserList(){
        viewModelScope.launch {
            getPcsUserListUseCase.execute(
                output = GetPcsUserListUseCase.Output(
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
}