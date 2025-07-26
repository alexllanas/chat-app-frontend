package com.chatappfrontend.auth.viewmodel

import androidx.lifecycle.viewModelScope
import com.chatappfrontend.auth.viewmodel.state.LoginUiState
import com.chatappfrontend.common.NetworkResult
import com.chatappfrontend.common.UiEvent
import com.chatappfrontend.domain.LoginUseCase
import com.chatappfrontend.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(value = LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login() {
        viewModelScope.launch {
            val result = loginUseCase.invoke(
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
}