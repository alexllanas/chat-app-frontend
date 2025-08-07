package com.example.network

import com.example.network.model.AuthenticationResponseDTO
import com.example.network.model.ChatListInfoDTO
import com.example.network.model.ChatSessionDTO
import com.example.network.model.UserListDTO
import retrofit2.Response

interface RemoteDataSource {
    suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): Response<AuthenticationResponseDTO>

    suspend fun login(
        email: String,
        password: String
    ): Response<AuthenticationResponseDTO>

    suspend fun getUsers(id: String): Response<UserListDTO>

    suspend fun getChats(userId: String): Response<ChatListInfoDTO>

    suspend fun getMessages(chatId: String?, userId: String?): Response<ChatSessionDTO>
}