package com.faizzfanani.core.domain.usecase

import com.faizzfanani.core.domain.util.AppInfo
import javax.inject.Inject

class BaseUseCaseImpl @Inject constructor(
    private val appInfo: AppInfo
): IBaseUseCase {
    override var callback: IBaseUseCase.Callback? = null

    override suspend fun allowExecute(allow: IBaseUseCase.AllowExecuteCallback) {
        if(appInfo.isInternetAvailable()){
            allow.allow()
        }
        else {
            callback?.noInternetConnection()
        }
    }

    override fun checkError(throwable: Throwable): Boolean {
        return false
    }
}