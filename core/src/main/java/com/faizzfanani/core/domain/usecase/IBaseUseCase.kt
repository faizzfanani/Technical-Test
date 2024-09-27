package com.faizzfanani.core.domain.usecase

interface IBaseUseCase {

    interface Callback{
        fun unauthorize()
        fun needUpdate()
        fun underMaintainance()
        fun noInternetConnection()
        fun serverError()
        fun showErrorMessage(message:String)
    }

    interface AllowExecuteCallback{
        suspend fun allow();
    }

    var callback: Callback?

    suspend fun allowExecute(allow: AllowExecuteCallback)
    fun checkError(throwable: Throwable):Boolean
}