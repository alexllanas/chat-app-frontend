package com.chatappfrontend.auth.viewmodel.state

data class RegisterUiState(
    val username: String = "alex",
    val email: String = "alex@example.com",
    val password: String = "password",
    val confirmPassword: String = "password",
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
    val errorMessage: String? = null,
    val showDialog: Boolean = false,
    val dialogTitle: String = "",
    val dialogBody: String = ""
)