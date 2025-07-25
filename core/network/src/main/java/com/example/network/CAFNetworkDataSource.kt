package com.example.network

import com.example.model.ProtectedResponse
import com.example.model.User
import retrofit2.Response

interface CAFNetworkDataSource {
    suspend fun registerUser(
        email: String,
        password: String
    ): Response<User>

    suspend fun testTokenAuthentication(): ProtectedResponse
}