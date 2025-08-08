package com.example.network

import com.chatappfrontend.common.Resource
import com.example.network.model.AuthenticationResponseDTO
import com.example.network.model.ChatListInfoDTO
import com.example.network.model.MessageDTO
import com.example.network.model.UserDTO
import com.example.network.model.UserListDTO
import kotlinx.coroutines.flow.Flow
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