package com.chatappfrontend.common

sealed class ResultWrapper<out T> {
    data class Success<T>(val data: T) : ResultWrapper<T>()
    data class Failure(val code: Int? = null, val message: String) : ResultWrapper<Nothing>()
    data class Error(val exception: Throwable? = null) : ResultWrapper<Nothing>()
}

inline fun <T, R> ResultWrapper<T>.map(transform: (T) -> R): ResultWrapper<R> {
    return when (this) {
        is ResultWrapper.Success -> ResultWrapper.Success(transform(data))
        is ResultWrapper.Failure -> ResultWrapper.Failure(code, message)
        is ResultWrapper.Error -> ResultWrapper.Error(exception)
    }
}