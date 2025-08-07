package com.example.messages.state

import com.chatappfrontend.domain.model.ChatInfo

data class ChatListUiState(
    val username: String = "",
    val chatInfos: List<ChatInfo> = emptyList()
)