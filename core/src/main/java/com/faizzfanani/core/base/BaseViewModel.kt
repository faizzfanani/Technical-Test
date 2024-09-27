package com.faizzfanani.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faizzfanani.core.domain.usecase.IBaseUseCase
import com.faizzfanani.core.utils.Event

abstract class BaseViewModel: ViewModel() {

    private val usecases = arrayListOf<BaseUseCase>()
    val unauthorizedEvent = MutableLiveData<Event<Boolean>>()
    val needUpdateEvent = MutableLiveData<Event<Boolean>>()
    val underMaintenanceEvent = MutableLiveData<Event<Boolean>>()
    val noInternetConnectionEvent = MutableLiveData<Event<Boolean>>()
    val serverErrorEvent = MutableLiveData<Event<Boolean>>()
    val showErrorMessageEvent = MutableLiveData<Event<String>>()
    private val mutableLoadingEvent = MutableLiveData<Int>()

    fun addUseCase(useCase: BaseUseCase){
        usecases.add(useCase)
        useCase.setCallback(object : IBaseUseCase.Callback{
            override fun unauthorize() {
                unauthorizedEvent.value = Event(true)
            }

            override fun needUpdate() {
                needUpdateEvent.value = Event(true)
            }

            override fun underMaintainance() {
                underMaintenanceEvent.value = Event(true)
            }

            override fun noInternetConnection() {
                noInternetConnectionEvent.value = Event(true)
            }

            override fun serverError() {
                serverErrorEvent.value = Event(true)
            }

            override fun showErrorMessage(message: String) {
                setLoading(false, true)
                showErrorMessageEvent.value = Event(message)
            }
        })
    }

    protected fun setLoading(show:Boolean, force:Boolean = false){
        if(force){
            if(show){
                mutableLoadingEvent.value = 1
            } else {
                mutableLoadingEvent.value = 0
            }
        } else {
            if (show) {
                mutableLoadingEvent.value = mutableLoadingEvent.value?.plus(1) ?: 1
            } else {
                val loadingCount = mutableLoadingEvent.value ?: 0
                mutableLoadingEvent.value = maxOf(loadingCount - 1, 0)
            }
        }
    }
}