package com.example.messages.viewmodel

import androidx.lifecycle.viewModelScope
import com.chatappfrontend.common.ActionResult
import com.chatappfrontend.common.UiEvent
import com.chatappfrontend.common.navigation.Screen
import com.chatappfrontend.domain.GetUsersUseCase
import com.chatappfrontend.ui.BaseViewModel
import com.example.messages.state.NewMessageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewMessageViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(value = NewMessageUiState())
    val uiState: StateFlow<NewMessageUiState> = _uiState.asStateFlow()

    fun getUsers() {
        viewModelScope.launch {
            try {
                val result = getUsersUseCase.invoke()
                when (result) {
                    is ActionResult.Success -> {
                        _uiState.value = _uiState.value.copy(users = result.data)
                    }
                    is ActionResult.Error -> {
//                        emitUiEvent(event = UiEvent.ShowError(message = result.message ?: "An error occurred"))
                    }
                    is ActionResult.Exception -> {
//                        emitUiEvent(event = UiEvent.ShowError(message = result.exception.message ?: "An error occurred"))
                    }
                    ActionResult.Ignored -> {}
                }
            } catch (e: Exception) {
//                emitUiEvent(event = UiEvent.ShowError(message = e.message ?: "An error occurred"))
            }
        }
    }
}
