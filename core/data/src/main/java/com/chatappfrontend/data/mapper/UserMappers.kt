package com.chatappfrontend.data.mapper

import com.chatappfrontend.domain.model.User
import com.example.network.model.AuthenticatedUserDto
import com.example.network.model.UserDto

fun AuthenticatedUserDto.toUser() = User(
    id = id,
    username = username,
    email = email
)

fun UserDto.toUser() = User(
    id = id,
    username = username,
    email = email
)