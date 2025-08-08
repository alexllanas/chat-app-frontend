package com.example.messages.state

import com.chatappfrontend.domain.model.User

data class NewMessageUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = true
)