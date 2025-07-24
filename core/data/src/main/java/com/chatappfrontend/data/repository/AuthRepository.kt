package com.chatappfrontend.data.repository

import com.example.model.User

interface AuthRepository {
    suspend fun registerUser(
        email: String,
        password: String
    ): User
}