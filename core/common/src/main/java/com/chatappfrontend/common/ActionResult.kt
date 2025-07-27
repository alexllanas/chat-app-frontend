package com.chatappfrontend.common

sealed class ActionResult<out T> {
    data class Success<T>(val data: T) : ActionResult<T>()
    data class Error(val code: Int? = null, val message: String) : ActionResult<Nothing>()
    data class Exception(val exception: Throwable) : ActionResult<Nothing>()
    data object Ignored : ActionResult<Nothing>()
}

inline fun <T, R> ActionResult<T>.map(transform: (T) -> R): ActionResult<R> {
    return when (this) {
        is ActionResult.Success -> ActionResult.Success(transform(data))
        is ActionResult.Error -> ActionResult.Error(code, message)
        is ActionResult.Exception -> ActionResult.Exception(exception)
        ActionResult.Ignored -> ActionResult.Ignored
    }
}