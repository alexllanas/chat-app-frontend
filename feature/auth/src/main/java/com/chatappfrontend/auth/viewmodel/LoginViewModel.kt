package com.chatappfrontend.auth.viewmodel

import android.util.Patterns
import androidx.lifecycle.viewModelScope
import com.chatappfrontend.auth.viewmodel.state.LoginUiState
import com.chatappfrontend.common.NetworkResult
import com.chatappfrontend.common.UiEvent
import com.chatappfrontend.domain.LoginUseCase
import com.chatappfrontend.ui.BaseViewModel
import com.chatappfrontend.common_android.StringProvider
import com.example.common_android.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val stringProvider: StringProvider
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(value = LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login() {
        viewModelScope.launch {
            val errorMessage = getErrorMessage()
            if (errorMessage != null) {
                _uiState.update {
                    it.copy(errorMessage = errorMessage)
                }
                return@launch
            }

            _uiState.update {
                it.copy(isLoading = true, errorMessage = null)
            }

            val result = loginUseCase.invoke(
                email = uiState.value.email,
                password = uiState.value.password,
            )

            when (result) {
                is NetworkResult.Success -> {
                    emitUiEvent(event = UiEvent.Navigate("message_list"))
                }
                is NetworkResult.Error -> {
                    _uiState.update {   // failed to login
                        it.copy(isLoading = false, errorMessage = result.message)
                    }
                }
                is NetworkResult.Exception -> {
                    _uiState.update {   // unexpected error
                        it.copy(isLoading = false, errorMessage = result.e.toString())
                    }
                }
            }
        }
    }

    private fun getErrorMessage(): String? = with(uiState.value) {
        return when {
            email.isBlank() && password.isBlank() -> stringProvider.getString(R.string.error_email_password_empty)
            email.isBlank() -> stringProvider.getString(R.string.error_email_empty)
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> stringProvider.getString(R.string.error_invalid_email)
            password.isBlank() -> stringProvider.getString(R.string.error_password_empty)
            password.length < 6 -> stringProvider.getString(R.string.error_password_short)
            else -> null
        }
    }

    fun setEmail(email: String) {
        _uiState.update {
            it.copy(email = email, errorMessage = null)
        }
    }

    fun setPassword(password: String) {
        _uiState.update {
            it.copy(password = password, errorMessage = null)
        }
    }
}