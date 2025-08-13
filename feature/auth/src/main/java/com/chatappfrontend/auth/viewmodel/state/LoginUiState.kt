package com.chatappfrontend.auth.viewmodel.state

data class LoginUiState(
    val email: String = "alex@example.com",
    val password: String = "password",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val showDialog: Boolean = false,
    val dialogTitle: String = "",
    val dialogBody: String = ""
)