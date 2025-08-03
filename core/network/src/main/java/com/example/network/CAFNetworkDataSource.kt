package com.example.network

import com.example.network.model.AuthenticationResponseDTO
import com.example.network.model.ChatDTO
import com.example.network.model.ChatListDTO
import com.example.network.model.MessageDTO
import com.example.network.model.MessageListDTO
import com.example.network.model.UserDTO
import com.example.network.model.UserListDTO
import retrofit2.Response

interface CAFNetworkDataSource {
    suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): Response<AuthenticationResponseDTO>

    suspend fun login(
        email: String,
        password: String
    ): Response<AuthenticationResponseDTO>

    suspend fun getUsers(): Response<UserListDTO>

    suspend fun getChats(userId: String): Response<ChatListDTO>

    suspend fun getMessages(chatId: String): Response<MessageListDTO>

    suspend fun checkIfChatExists(userId: String, recipientId: String): Response<String>

}