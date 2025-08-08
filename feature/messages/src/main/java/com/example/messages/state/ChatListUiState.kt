package com.example.messages.state

import com.chatappfrontend.domain.model.ChatInfo

data class ChatListUiState(
    val username: String = "",
    val chats: List<ChatInfo> = emptyList(),
    val isLoading: Boolean = true
)