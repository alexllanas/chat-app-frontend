package com.example.network

import com.example.network.model.UserDto
import retrofit2.Response

interface CAFNetworkDataSource {
    suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): Response<UserDto>

    suspend fun login(
        email: String,
        password: String
    ): Response<UserDto>
}