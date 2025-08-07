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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
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

            webSocketManager.connect(userId = userId)

            messageRepository.incomingMessages.collect { message ->
                Log.d("ChatViewModel", "Incoming message: $message")
                _uiState.value = _uiState.value.copy(
                    messages = _uiState.value.messages + message
                )
            }
        }
    }

    fun sendMessage(chatId: String, recipientId: String) {
        if (uiState.value.textFieldInput.isBlank()) {
            Log.d("ChatViewModel", "Message is blank, not sending")
            return
        }
        viewModelScope.launch {
            Log.d(
                "ChatViewModel",
                "Sending message: ${uiState.value.textFieldInput} to chatId: $chatId, userId: $recipientId"
            )
            val userId = dataStoreManager.getUserId() ?: ""
            val tempMessageId = UUID.randomUUID().toString()
            val newMessage = Message(
                id = tempMessageId,
                chatId = chatId,
                content = uiState.value.textFieldInput,
                senderId = userId,
                recipientId = recipientId
            )
            val result = messageRepository.sendMessage(recipientId, uiState.value.textFieldInput)
            when (result) {
                is ResultWrapper.Success<Message.Status> -> {
                    if (result.data == Message.Status.FAILED) {
                        _uiState.value.messages.map {
                            if (tempMessageId == it.id) {
                                newMessage.copy(status = Message.Status.FAILED)
                            } else {
                                it
                            }
                        }
                    }
                }
                is ResultWrapper.Error -> {}
                is ResultWrapper.Exception -> {}
                ResultWrapper.Ignored -> {}
            }
            _uiState.value = _uiState.value.copy(
                messages = listOf(newMessage) + _uiState.value.messages,
                textFieldInput = ""
            )
        }
    }

    fun initializeData(chatId: String?, userId: String?) {
        Log.d("ChatViewModel", "initializeData called with chatId: $chatId, userId: $userId")
        if (chatId != null) {
            Log.d("ChatViewModel", "ChatId is not null, fetching messages for chatId: $chatId")
            getMessages(chatId = chatId)
        } else {
            Log.d("ChatViewModel", "Both chatId and userId are null, cannot fetch messages")
            // Show error or handle the case where both chatId and userId are null
        }
    }

    private fun getMessages(chatId: String?) {
        viewModelScope.launch {
            val userId = dataStoreManager.getUserId()
            val result = messageRepository.getChatSession(chatId = chatId, userId)
//            val mess = messageRepository.getMessagesResource(chatId = chatId?: "", userId?: "")
            val messages = messageRepository.getMessages(chatId = chatId?: "")
            messages.collect { list ->
                list.forEach {
                    Log.d("chat", "getMessages: $it")
                }
                _uiState.update {
                    it.copy(
                        messages = list
                    )
                }
            }
//            when (result) {
//                is ResultWrapper.Success -> {
//                    Log.d("ChatViewModel", "Messages fetched successfully for chatId: $chatId")
//                    _uiState.value = _uiState.value.copy(
//                        chatId = result.data.chatId,
//                        recipientId = result.data.userId,
//                        username = result.data.username,
//                        messages = result.data.messages,
//                    )
//                }
//
//                is ResultWrapper.Error -> {
//
//                    // Handle error
//                }
//
//                is ResultWrapper.Exception -> {
//                    // Handle exception
//                }
//
//                ResultWrapper.Ignored -> {
//                    // Handle ignored case
//                }
//            }
        }
    }

    fun setMessage(message: String) {
        _uiState.value = _uiState.value.copy(textFieldInput = message)
    }

    fun disconnect() {
        Log.d("ChatViewModel", "Disconnecting WebSocket")
        webSocketManager.disconnect()
    }

}
