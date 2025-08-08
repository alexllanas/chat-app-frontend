package com.example.messages.viewmodel

import androidx.lifecycle.viewModelScope
import com.chatappfrontend.common.Resource
import com.chatappfrontend.common.UiEvent
import com.chatappfrontend.common.formatChatDate
import com.chatappfrontend.common.navigation.Screen
import com.chatappfrontend.domain.LogoutUseCase
import com.chatappfrontend.domain.repository.MessageRepository
import com.chatappfrontend.ui.BaseViewModel
import com.example.messages.state.ChatListUiState
import com.example.security.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val dataStoreManager: DataStoreManager,
    private val messageRepository: MessageRepository
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(value = ChatListUiState())
    val uiState: StateFlow<ChatListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreManager.getUsername()?.let {
                _uiState.value = _uiState.value.copy(username = it)
            }
        }
    }

    // TODO: move to settings screen
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

    fun getChats() {
        viewModelScope.launch {
            val userId = dataStoreManager.getUserId() ?: ""
            val result = messageRepository.getChats(currentUserId = userId)
            result.collect { resource ->
                when (resource) {
                    is Resource.Error<*> -> {}
                    is Resource.Loading<*> -> {
                        _uiState.update {
                            it.copy(
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Success<*> -> {
                        _uiState.value = _uiState.value.copy(
                            chats = resource.data?.map { chat ->
                                chat.copy(
                                    lastMessageTimeStamp = formatChatDate(chat.lastMessageTimeStamp)
                                )
                            } ?: listOf(),
                            isLoading = false
                        )
                    }
                }
            }
//            when (val result = messageRepository.getChats()) {
//                is ResultWrapper.Success -> {
//                    _uiState.value = _uiState.value.copy(
//                        chatInfos = result.data,
//                    )
//                }
//                is ResultWrapper.Error -> {
//                }
//                is ResultWrapper.Exception -> {
//                }
//                ResultWrapper.Ignored -> {}
//            }
        }
    }

}
