package com.chatappfrontend.domain.repository

import com.chatappfrontend.common.ActionResult
import com.chatappfrontend.domain.model.User

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

    suspend fun getUsers(): ActionResult<List<User>>
}