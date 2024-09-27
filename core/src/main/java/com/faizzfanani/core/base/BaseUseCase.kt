package com.faizzfanani.core.base

import com.faizzfanani.core.domain.usecase.IBaseUseCase

abstract class BaseUseCase(private val baseUseCase: IBaseUseCase) {

    fun setCallback(callback: IBaseUseCase.Callback){
        baseUseCase.callback = callback
    }

    protected suspend fun allowExecute(allow: suspend () -> Unit){
        baseUseCase.allowExecute(object: IBaseUseCase.AllowExecuteCallback{
            override suspend fun allow() {
                allow.invoke()
            }
        })
    }

    protected fun checkError(throwable: Throwable): Boolean {
        return baseUseCase.checkError(throwable)
    }

}