package com.chatappfrontend.common

sealed class Result {
    data object Success : Result()
    data class Failure(val message: String) : Result()
}