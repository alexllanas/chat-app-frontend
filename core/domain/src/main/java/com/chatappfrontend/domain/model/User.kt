package com.chatappfrontend.domain.model

data class User(
    val id: String,

    val username: String,

    val email: String,

    val chatId: String? = null,
)

