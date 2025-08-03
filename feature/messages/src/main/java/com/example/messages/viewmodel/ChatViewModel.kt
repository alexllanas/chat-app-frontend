package com.example.messages.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.data.websocket.WebSocketManager
import com.chatappfrontend.domain.model.Message
import com.chatappfrontend.domain.repository.MessageRepository
import com.chatappfrontend.ui.BaseViewModel
import com.example.messages.state.ChatUiState
import com.example.security.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val messageRepository: MessageRepository,
    private val webSocketManager: WebSocketManager,
    private val dataStoreManager: DataStoreManager
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(value = ChatUiState())
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            val userId = dataStoreManager.getUserId() ?: ""
            val username = dataStoreManager.getUsername() ?: ""
            _uiState.value = _uiState.value.copy(
                username = username
            )

            webSocketManager.connect(userId = userId)

            messageRepository.incomingMessages.collect { message ->
                Log.d("ChatViewModel", "Incoming message: $message")
                _uiState.value = _uiState.value.copy(
                    messages = _uiState.value.messages + message
                )
            }
        }
    }

    fun sendMessage(chatId: String, userId: String) {
        if (uiState.value.message.isBlank()) {
            Log.d("ChatViewModel", "Message is blank, not sending")
            return
        }
        viewModelScope.launch {
            Log.d(
                "ChatViewModel",
                "Sending message: ${uiState.value.message} to chatId: $chatId, userId: $userId"
            )
            messageRepository.sendMessage(userId, uiState.value.message)
            dataStoreManager.getUserId()?.let {
                _uiState.value = _uiState.value.copy(
                    messages = _uiState.value.messages + Message(
                        id = chatId,
                        content = uiState.value.message,
                        senderId = it,
                        recipientId = userId,
                        createdAt = "System.currentTimeMillis()",
                        isRead = false,
                    ),
                )
            } ?: Log.e("ChatViewModel", "User ID not found in DataStore")
            _uiState.value = _uiState.value.copy(message = "")
        }
    }

    fun initializeData(chatId: String?, userId: String?) {
        Log.d("ChatViewModel", "initializeData called with chatId: $chatId, userId: $userId")
        if (chatId != null) {
            Log.d("ChatViewModel", "ChatId is not null, fetching messages for chatId: $chatId")
            getMessages(chatId = chatId)
        } else if (userId != null) {
            Log.d(
                "ChatViewModel",
                "UserId is not null, checking if chat exists for userId: $userId"
            )
            checkIfChatExists(userId = userId)
        } else {
            Log.d("ChatViewModel", "Both chatId and userId are null, cannot fetch messages")
            // Show error or handle the case where both chatId and userId are null
        }
    }

    private fun checkIfChatExists(userId: String) {
        viewModelScope.launch {
            val result = messageRepository.checkIfChatExists(userId)
            if (result is ResultWrapper.Success) {
                Log.d("ChatViewModel", "Chat exists with ID: ${result.data}")
                getMessages(chatId = result.data)
            } else {
                Log.d("ChatViewModel", "Chat does not exist for userId: $userId")
                // Handle error or exception
                // if 404 does not exist, empty list
            }
        }
    }

    private fun getMessages(chatId: String) {
        viewModelScope.launch {
            val result = messageRepository.getMessages(chatId = chatId)
            when (result) {
                is ResultWrapper.Success -> {
                    Log.d("ChatViewModel", "Messages fetched successfully for chatId: $chatId")
                    _uiState.value = _uiState.value.copy(
                        messages = result.data,
                    )
                }

                is ResultWrapper.Error -> {

                    // Handle error
                }

                is ResultWrapper.Exception -> {
                    // Handle exception
                }

                ResultWrapper.Ignored -> {
                    // Handle ignored case
                }
            }
        }
    }

    fun setMessage(message: String) {
        _uiState.value = _uiState.value.copy(message = message)
    }

    fun disconnect() {
        Log.d("ChatViewModel", "Disconnecting WebSocket")
        webSocketManager.disconnect()
    }

}
