package com.chatappfrontend.messages.state

import com.chatappfrontend.domain.model.ChatInfo

data class ChatListUiState(
    val chats: List<ChatInfo> = emptyList(),
    val isLoading: Boolean = true
)