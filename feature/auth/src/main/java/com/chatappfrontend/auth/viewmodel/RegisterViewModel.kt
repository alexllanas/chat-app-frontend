package com.chatappfrontend.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chatappfrontend.auth.viewmodel.state.RegisterUiState
import com.chatappfrontend.domain.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun registerUser() {
//        if (validateInput()) return
        viewModelScope.launch {
            registerUserUseCase.invoke(
                uiState.value.email,
                uiState.value.password,
            )
        }
    }

    private fun validateInput(): Boolean {
        if (
            uiState.value.email.isBlank() ||
            uiState.value.password.isBlank() ||
            uiState.value.confirmPassword.isBlank()
        ) {
            _uiState.update {
                it.copy(errorMessage = "Fields can't be empty")
            }
            return true
        } else if (uiState.value.password != uiState.value.confirmPassword) {
            _uiState.update {
                it.copy(errorMessage = "Passwords do not match")
            }
            return true
        } else if (uiState.value.password.length < 6) {
            _uiState.update {
                it.copy(errorMessage = "Password too short")
            }
            return true
        }
        return false
    }

    fun setEmail(email: String) {
        _uiState.update {
            it.copy(email = email)
        }
    }

    fun setPassword(password: String) {
        _uiState.update {
            it.copy(password = password)
        }
    }

    fun setConfirmPassword(confirmPassword: String) {
        _uiState.update {
            it.copy(confirmPassword = confirmPassword)
        }
    }
}