package com.chatappfrontend.data.model.mapper

import com.chatappfrontend.data.model.User
import com.example.network.model.UserDto

fun UserDto.toUser() = User(
    id = id,
    username = username,
    email = email
)