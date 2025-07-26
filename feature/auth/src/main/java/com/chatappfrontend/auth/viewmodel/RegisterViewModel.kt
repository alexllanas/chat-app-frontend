package com.chatappfrontend.auth.viewmodel

import androidx.lifecycle.viewModelScope
import com.chatappfrontend.auth.viewmodel.state.LoginUiState
import com.chatappfrontend.auth.viewmodel.state.RegisterUiState
import com.chatappfrontend.common.NetworkResult
import com.chatappfrontend.common.UiEvent
import com.chatappfrontend.domain.RegisterUserUseCase
import com.chatappfrontend.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase,
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(value = RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun registerUser() {
        if (validateInput()) return
        viewModelScope.launch {
            val result = registerUserUseCase.invoke(
                email = uiState.value.email,
                password = uiState.value.password,
            )
            when (result) {
                is NetworkResult.Success -> {
                    emitUiEvent(event = UiEvent.Navigate("message_list"))
                }
                is NetworkResult.Error -> {
                    _uiState.update { it.copy(errorMessage = result.message) }
                }
                is NetworkResult.Exception -> {
                    _uiState.update { it.copy(errorMessage = result.e.toString()) }
                }
            }
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
