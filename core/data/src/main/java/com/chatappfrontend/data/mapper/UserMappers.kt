package com.chatappfrontend.data.mapper

import com.chatappfrontend.domain.model.User
import com.example.network.model.AuthenticationResponseDTO
import com.example.network.model.UserDTO

fun AuthenticationResponseDTO.toUser() = User(
    id = id,
    username = username,
    email = email
)

fun UserDTO.toUser() = User(
    id = id,
    username = username,
    email = email,
    chatId = chatId
)