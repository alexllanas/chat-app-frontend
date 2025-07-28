package com.example.messages.viewmodel

import androidx.lifecycle.viewModelScope
import com.chatappfrontend.common.ActionResult
import com.chatappfrontend.common.UiEvent
import com.chatappfrontend.common.navigation.Screen
import com.chatappfrontend.domain.GetUsersUseCase
import com.chatappfrontend.domain.LogoutUseCase
import com.chatappfrontend.ui.BaseViewModel
import com.example.messages.state.ConversationListUiState
import com.example.messages.state.NewMessageUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversationListViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val getUsersUseCase: GetUsersUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(value = ConversationListUiState())
    val uiState: StateFlow<ConversationListUiState> = _uiState.asStateFlow()

    fun logout() {
        viewModelScope.launch {

            try {
                logoutUseCase.invoke()
                emitUiEvent(event = UiEvent.Navigate(Screen.Login.route))
            } catch (e: Exception) {
//                emitUiEvent(event = UiEvent.ShowError(message = e.message ?: "An error occurred"))
            }
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            val result = getUsersUseCase.invoke()
            when (result) {
                is ActionResult.Success -> {
                    _uiState.value = _uiState.value.copy(users = result.data)
                }

                is ActionResult.Error -> {
                }

                is ActionResult.Exception -> TODO()
                ActionResult.Ignored -> TODO()
            }
        }
    }
}
