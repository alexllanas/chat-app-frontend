package com.chatappfrontend.domain.model

data class ChatSession(
    val chatId: String,

    val userId: String,

    val username: String,

    val messages: List<Message>
)