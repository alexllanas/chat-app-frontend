package com.chatappfrontend.domain.repository

import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.domain.model.User

interface AuthRepository {
    suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): ResultWrapper<User>

    suspend fun login(
        email: String,
        password: String
    ): ResultWrapper<User>

    suspend fun logout()
}