package com.faizzfanani.pcs.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.faizzfanani.core.base.BaseViewModel
import com.faizzfanani.core.utils.Event
import com.faizzfanani.pcs.PcsFeatureConstant.Companion.EMPTY_STRING
import com.faizzfanani.service_pcs.domain.model.PcsUser
import com.faizzfanani.service_pcs.domain.usecase.GetPcsUserDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PcsUserDetailViewModel @Inject constructor(
    private val getPcsUserDetailUseCase: GetPcsUserDetailUseCase,
): BaseViewModel() {

    init {
        addUseCase(getPcsUserDetailUseCase)
    }

    val successDetailEvent = MutableLiveData<Event<PcsUser>>()
    val errorEvent = MutableLiveData<Event<String>>()
    val onLoadingEvent = MutableLiveData<Event<Boolean>>()
    var isLoading = false
    var userId = EMPTY_STRING

    fun getUserDetailByUsername(){
        viewModelScope.launch {
            getPcsUserDetailUseCase.execute(id = userId,
                output = GetPcsUserDetailUseCase.Output(
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