package com.example.messages.state

import com.chatappfrontend.domain.model.User

data class ConversationListUiState(
    val users: List<User> = emptyList()
)