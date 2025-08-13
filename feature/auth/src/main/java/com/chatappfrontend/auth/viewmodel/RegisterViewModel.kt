package com.chatappfrontend.auth.viewmodel

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.chatappfrontend.auth.viewmodel.state.RegisterUiState
import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.common.UiEvent
import com.chatappfrontend.common.navigation.Screen
import com.chatappfrontend.common_android.StringProvider
import com.chatappfrontend.domain.repository.AuthRepository
import com.chatappfrontend.ui.BaseViewModel
import com.example.common_android.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val stringProvider: StringProvider,
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(value = RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun registerUser() {
        viewModelScope.launch {
            val errorMessage = getErrorMessage()
            if (errorMessage != null) {
                _uiState.update { it.copy(errorMessage = errorMessage) }
                return@launch
            }

            _uiState.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            val result = authRepository.registerUser(
                username = uiState.value.username,
                email = uiState.value.email,
                password = uiState.value.password,
            )

            when (result) {
                is ResultWrapper.Success -> {
                    emitUiEvent(event = UiEvent.Navigate(Screen.ChatList.route))
                }

                is ResultWrapper.Failure -> {
                    if (result.code == 409)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        }
                }

                is ResultWrapper.Error -> {
                    _uiState.update {
                        it.copy(isLoading = false)
                    }
                    showDialog(
                        title = stringProvider.getString(R.string.dialog_title_sign_up_failure),
                        body = stringProvider.getString(R.string.dialog_body_sign_up_failure),
                    )
                }
            }
        }
    }

    private fun getErrorMessage(): String? = with(uiState.value) {
        return when {
            username.isBlank() -> stringProvider.getString(R.string.error_username_empty)
            email.isBlank() && password.isBlank() -> stringProvider.getString(R.string.error_email_password_empty)
            email.isBlank() -> stringProvider.getString(R.string.error_email_empty)
            !Patterns.EMAIL_ADDRESS.matcher(email)
                .matches() -> stringProvider.getString(R.string.error_invalid_email)

            password.isBlank() -> stringProvider.getString(R.string.error_password_empty)
            password.length < 6 -> stringProvider.getString(R.string.error_password_short)
            confirmPassword.isBlank() -> stringProvider.getString(R.string.error_confirm_password_empty)
            confirmPassword != password -> stringProvider.getString(R.string.error_passwords_no_match)
            else -> null
        }
    }

    fun setUsername(username: String) {
        _uiState.update { it.copy(username = username) }
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
