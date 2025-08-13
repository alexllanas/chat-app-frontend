package com.chatappfrontend.common

sealed class DataResource<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : DataResource<T>(data)
    class Loading<T>(data: T? = null) : DataResource<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null) : DataResource<T>(data, throwable)
}