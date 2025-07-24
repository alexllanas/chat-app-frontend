package com.example.network

import com.example.model.User

interface CAFNetworkDataSource {
    suspend fun registerUser(
        email: String,
        password: String
    ): User
}