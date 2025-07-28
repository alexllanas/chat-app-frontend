package com.example.network

import com.example.network.model.AuthenticatedUserDto
import com.example.network.model.UserDto
import com.example.network.model.UsersDto
import retrofit2.Response

interface CAFNetworkDataSource {
    suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): Response<AuthenticatedUserDto>

    suspend fun login(
        email: String,
        password: String
    ): Response<AuthenticatedUserDto>

    suspend fun getUsers(): Response<UsersDto>

}