package com.chatappfrontend.data.repository

import android.util.Log
import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.data.MessagePayloadDTO
import com.chatappfrontend.data.mapper.toChat
import com.chatappfrontend.data.mapper.toMessage
import com.chatappfrontend.data.websocket.WebSocketManager
import com.chatappfrontend.domain.model.Chat
import com.chatappfrontend.domain.model.Message
import com.chatappfrontend.domain.repository.MessageRepository
import com.example.network.CAFNetworkDataSource
import com.example.network.utils.safeApiCall
import com.example.security.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultMessageRepository @Inject constructor(
    private val webSocketManager: WebSocketManager,
    private val dataStoreManager: DataStoreManager,
    private val network: CAFNetworkDataSource
) : MessageRepository {

    override val incomingMessages: SharedFlow<Message> = webSocketManager.incomingMessages

    override suspend fun sendMessage(recipientId: String, content: String): ResultWrapper<Unit> {
        dataStoreManager.getUserId()?.let { userId ->
            val messageDTO = MessagePayloadDTO(
                type = "message",
                content = content,
                senderId = userId,
                recipientId = recipientId
            )
            Log.d("DefaultMessageRepository", "Sending message: $messageDTO")
            return webSocketManager.sendMessage(
                messagePayloadDTO = messageDTO,
            )
        }
        Log.e("DefaultMessageRepository", "User ID not found in DataStore")
        return ResultWrapper.Exception(
            Exception("User ID not found in DataStore")
        )
    }

    override suspend fun getChats(): ResultWrapper<List<Chat>> {
        dataStoreManager.getUserId()?.let { userId ->
            Log.d("DefaultMessageRepository", "Fetching chats for userId: $userId")
            return safeApiCall(
                apiCall = {
                    Log.d("DefaultMessageRepository", "Calling network.getChats with userId: $userId")
                    network.getChats(userId)
              },
                onSuccess = { body ->
                    Log.d("DefaultMessageRepository", "Received chats: ${body.chats}")
                    body.chats.map { it.toChat() }
                }
            )
        }
        Log.e("DefaultMessageRepository", "User ID not found in DataStore")
        return ResultWrapper.Exception(
            Exception("User ID not found in DataStore")
        )
    }

    override suspend fun getMessages(chatId: String): ResultWrapper<List<Message>> {
        return safeApiCall(
            apiCall = {
                Log.d("DefaultMessageRepository", "Fetching messages for chatId: $chatId")
                network.getMessages(chatId)
            },
            onSuccess = { body ->
                Log.d("DefaultMessageRepository", "Received messages: ${body.messages}")
                body.messages.map { it.toMessage() }
            }
        )
    }

    override suspend fun checkIfChatExists(userId: String): ResultWrapper<String> {
        dataStoreManager.getUserId()?.let { currentUserId ->
            return safeApiCall(
                apiCall = {
                    Log.d("DefaultMessageRepository", "Checking if chat exists for userId: $userId with currentUserId: $currentUserId")
                    network.checkIfChatExists(currentUserId, userId)
              },
                onSuccess = { chatId ->
                    Log.d("DefaultMessageRepository", "Chat exists with chatId: $chatId")
                    chatId
                }
            )
        }
        Log.e("DefaultMessageRepository", "User ID not found in DataStore")
        return ResultWrapper.Exception(
            Exception("User ID not found in DataStore")
        )
    }

    fun collectMessage() {

    }
}