package com.chatappfrontend.common

sealed class ResultWrapper<out T> {
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Error(val code: Int? = null, val message: String) : ResultWrapper<Nothing>()
    data class Exception(val exception: Throwable) : ResultWrapper<Nothing>()
    data object Ignored : ResultWrapper<Nothing>()
}

inline fun <T, R> ResultWrapper<T>.map(transform: (T) -> R): ResultWrapper<R> {
    return when (this) {
        is ResultWrapper.Success -> ResultWrapper.Success(transform(data))
        is ResultWrapper.Error -> ResultWrapper.Error(code, message)
        is ResultWrapper.Exception -> ResultWrapper.Exception(exception)
        ResultWrapper.Ignored -> ResultWrapper.Ignored
    }
}