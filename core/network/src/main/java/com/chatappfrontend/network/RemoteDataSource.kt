package com.chatappfrontend.network

import com.chatappfrontend.network.model.AuthenticationResponseDTO
import com.chatappfrontend.network.model.ChatListInfoDTO
import com.chatappfrontend.network.model.MessageDTO
import com.chatappfrontend.network.model.UserListDTO
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

    suspend fun getUsers(currentUserId: String): Response<UserListDTO>

    suspend fun getChats(currentUserId: String): Response<ChatListInfoDTO>

    suspend fun getMessages(chatId: String?): Response<List<MessageDTO>>
}