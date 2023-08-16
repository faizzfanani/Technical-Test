package com.faizfanani.movieapp.interactor.util

import com.faizfanani.movieapp.data.network.model.DefaultResponse
import com.faizfanani.movieapp.utils.Constants
import com.faizfanani.movieapp.utils.exception.CustomMessageException
import com.faizfanani.movieapp.utils.exception.NoConnectivityException
import com.google.gson.Gson
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed interface StatusResult<T> {
    class Success<T>(val data: T) : StatusResult<T>
    class Error<T>(val e: Exception, val data: T? = null) : StatusResult<T>
    class Loading<T>(val data: T? = null) : StatusResult<T>
}

inline fun <T> StatusResult<T>.onSuccess(onSuccess: DataOnly<T>): StatusResult<T> {
    if (this is StatusResult.Success) {
        onSuccess(this.data)
    }
    return this
}

inline fun <T> StatusResult<T>.onErrorMessage(onError: MessageWithData<T>): StatusResult<T> {
    if (this is StatusResult.Error) {
        val message = when (this.e) {
            is NoConnectivityException, is ConnectException -> {
                Constants.ERROR_NO_CONNECTION
            }
            is SocketTimeoutException, is SocketException, is UnknownHostException -> {
                Constants.ERROR_NETWORK
            }
            is CustomMessageException -> {
                this.e.message
            }
            is HttpException -> {
                val gson = Gson()
                val errorBody = gson.fromJson(this.e.response()?.errorBody()?.string(), DefaultResponse::class.java)
                errorBody.message
            }
            else -> Constants.ERROR_SERVER
        }
        onError(message, this.data)
    }
    return this
}

inline fun <T> StatusResult<T>.onErrorException(onError: ErrorWithData<T>): StatusResult<T> {
    if (this is StatusResult.Error) {
        onError(this.e, this.data)
    }
    return this
}

inline fun <T> StatusResult<T>.onLoading(onLoading: DataOnlyNullable<T>): StatusResult<T> {
    if (this is StatusResult.Loading) {
        onLoading(this.data)
    }
    return this
}

fun <T> StatusResult<T>?.isLoading(): Boolean {
    return this is StatusResult.Loading
}

fun <T> StatusResult<T>?.getData(): T? {
    return this?.let {
        when(this) {
            is StatusResult.Loading -> this.data
            is StatusResult.Success -> this.data
            is StatusResult.Error -> this.data
        }
    }
}

private typealias DataOnly<T> = (data: T) -> Unit
private typealias DataOnlyNullable<T> = (data: T?) -> Unit
private typealias MessageWithData<T> = (message: String, data: T?) -> Unit
private typealias ErrorWithData<T> = (e: Exception, data: T?) -> Unit