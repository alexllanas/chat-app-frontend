package com.example.messages.state

import com.chatappfrontend.domain.model.Chat

data class ChatListUiState(
    val username: String = "",
    val chats: List<Chat> = emptyList()
)