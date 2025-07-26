package com.chatappfrontend.data.repository

import com.chatappfrontend.common.NetworkResult
import com.chatappfrontend.common.Result
import com.example.model.User

interface AuthRepository {
    suspend fun registerUser(
        email: String,
        password: String
    ): NetworkResult<User>

    suspend fun login(
        email: String,
        password: String
    ): NetworkResult<User>

    suspend fun logout(): Result
}