package com.chatappfrontend.common

sealed class NetworkResult<T : Any> {
    data class Success<T : Any>(val data: T) : NetworkResult<T>()
    data class Error<T : Any>(val code: Int, val message: String?) : NetworkResult<T>()
    data class Exception<T : Any>(val e: Throwable) : NetworkResult<T>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success(data=$data)"
            is Error -> "Error(code=$code, message=$message)"
            is Exception -> "Exception(e=${e.message})"
        }
    }
}