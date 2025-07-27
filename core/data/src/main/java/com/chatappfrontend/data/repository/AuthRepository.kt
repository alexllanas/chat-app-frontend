package com.chatappfrontend.data.repository

import com.chatappfrontend.common.ActionResult
import com.chatappfrontend.data.model.User

interface AuthRepository {
    suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): ActionResult<User>

    suspend fun login(
        email: String,
        password: String
    ): ActionResult<User>

    suspend fun logout()
}