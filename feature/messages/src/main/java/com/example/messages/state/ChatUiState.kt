package com.example.messages.state

import com.chatappfrontend.domain.model.Message

data class ChatUiState(
    val chatId: String = "",
    val recipientId: String = "",
    val username: String = "",
    val messages: List<Message> = emptyList(),
    val textFieldInput: String = "",
    val isLoading: Boolean = true
)