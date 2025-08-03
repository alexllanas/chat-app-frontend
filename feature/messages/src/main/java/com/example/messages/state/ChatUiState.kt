package com.example.messages.state

import com.chatappfrontend.domain.model.Message

data class ChatUiState(
    val username: String = "",
    val message: String = "",
    val messages: List<Message> = emptyList(),
)