package com.chatappfrontend.messages.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.chatappfrontend.common.DataResource
import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.common.util.formatCurrentTime
import com.chatappfrontend.common.util.formatMessageTime
import com.chatappfrontend.data.websocket.WebSocketManager
import com.chatappfrontend.domain.model.Message
import com.chatappfrontend.domain.repository.MessageRepository
import com.chatappfrontend.ui.BaseViewModel
import com.chatappfrontend.messages.state.ChatUiState
import com.chatappfrontend.security.DataStoreManager
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
                messageRepository.insertMessages(listOf(message))
                if (userId != message.senderId) {
                    _uiState.value = _uiState.value.copy(
                        messages = _uiState.value.messages + message
                    )
                }
            }
        }
    }

    fun sendMessage(chatId: String, recipientId: String) {
        if (uiState.value.textFieldInput.isBlank()) {
            return
        }
        viewModelScope.launch {
            val userId = dataStoreManager.getUserId() ?: ""
            val tempMessageId = UUID.randomUUID().toString()
            val newMessage = Message(
                id = tempMessageId,
                chatId = chatId,
                content = uiState.value.textFieldInput,
                senderId = userId,
                recipientId = recipientId,
                createdAt = formatCurrentTime()
            )
            val result = messageRepository.sendMessage(
                recipientId = recipientId,
                content = uiState.value.textFieldInput
            )
            _uiState.value = _uiState.value.copy(
                messages = listOf(newMessage) + _uiState.value.messages,
                textFieldInput = ""
            )
            when (result) {
                is ResultWrapper.Success<Message.Status> -> {
//                    if (result.data == Message.Status.FAILED) {
//                        _uiState.value.messages.map {
//                            if (tempMessageId == it.id) {
//                                newMessage.copy(status = Message.Status.FAILED)
//                            } else {
//                                it
//                            }
//                        }
//                    }
                }

                is ResultWrapper.Failure -> {}
                is ResultWrapper.Error -> {}
            }

        }
    }

    fun initializeData(chatId: String?) {
        if (chatId != null) {
            getMessages(chatId = chatId)
        }
    }

    private fun getMessages(chatId: String?) {
        viewModelScope.launch {
            val messages = messageRepository.getMessages(chatId = chatId ?: "")
            messages.collect { resource ->
                when (resource) {
                    is DataResource.Error<*> -> {

                    }

                    is DataResource.Loading<*> -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is DataResource.Success<*> -> {
                        _uiState.update {
                            it.copy(
                                messages = resource.data?.map { message ->
                                    message.copy(
                                        createdAt = formatMessageTime(message.createdAt)
                                    )
                                } ?: listOf(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
        }
    }

    fun setMessage(message: String) {
        _uiState.value = _uiState.value.copy(textFieldInput = message)
    }

    fun disconnect() {
        webSocketManager.disconnect()
    }

    override fun onCleared() {
        disconnect()
    }
}