package com.chatappfrontend.data.mapper

import com.chatappfrontend.domain.model.User
import com.chatappfrontend.database.model.UserEntity
import com.chatappfrontend.network.model.AuthenticationResponseDTO
import com.chatappfrontend.network.model.UserDTO

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

fun UserDTO.toUserEntity() = UserEntity(
    id = id,
    username = username,
    email = email,
    chatId = chatId?: ""
)

fun UserEntity.toUser() = User(
    id = id,
    username = username,
    email = email,
    chatId = chatId
)
