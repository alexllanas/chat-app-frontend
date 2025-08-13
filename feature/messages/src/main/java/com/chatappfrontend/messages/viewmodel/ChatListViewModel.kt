package com.chatappfrontend.messages.viewmodel

import androidx.lifecycle.viewModelScope
import com.chatappfrontend.common.DataResource
import com.chatappfrontend.common.UiEvent
import com.chatappfrontend.common.util.formatChatDate
import com.chatappfrontend.common.navigation.Screen
import com.chatappfrontend.domain.repository.AuthRepository
import com.chatappfrontend.domain.repository.MessageRepository
import com.chatappfrontend.ui.BaseViewModel
import com.chatappfrontend.messages.state.ChatListUiState
import com.chatappfrontend.security.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val messageRepository: MessageRepository,
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(value = ChatListUiState())
    val uiState: StateFlow<ChatListUiState> = _uiState.asStateFlow()

    init {
        getChats()
    }

    fun getChats() {
        viewModelScope.launch {
            val userId = dataStoreManager.getUserId() ?: ""
            val result = messageRepository.getChats(currentUserId = userId)

            result.collect { resource ->
                when (resource) {
                    is DataResource.Loading<*> -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is DataResource.Success<*> -> {
                        val chats = resource.data?.map { chat ->
                            chat.copy(
                                lastMessageTimeStamp = formatChatDate(chat.lastMessageTimeStamp)
                            )
                        } ?: listOf()

                        _uiState.update {
                            it.copy(
                                chats = chats,
                                isLoading = false
                            )
                        }
                    }

                    is DataResource.Error<*> -> {
                        showDialog(title = "An unexpected error occurred.")
                    }
                }
            }
        }
    }


    // TODO: move to settings screen
    fun logout() {
        viewModelScope.launch {
            try {
                authRepository.logout()
                emitUiEvent(event = UiEvent.Navigate(Screen.Login.route))
            } catch (_: Exception) {
                showDialog(
                    title = "An unexpected error occurred."
                )
            }
        }
    }
}
