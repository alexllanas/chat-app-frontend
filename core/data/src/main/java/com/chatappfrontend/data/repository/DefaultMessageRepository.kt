package com.chatappfrontend.data.repository

import android.util.Log
import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.data.MessagePayloadDTO
import com.chatappfrontend.data.mapper.toChatInfo
import com.chatappfrontend.data.mapper.toChatSession
import com.chatappfrontend.data.mapper.toMessage
import com.chatappfrontend.data.mapper.toMessageEntity
import com.chatappfrontend.data.websocket.WebSocketManager
import com.chatappfrontend.domain.model.ChatInfo
import com.chatappfrontend.domain.model.ChatSession
import com.chatappfrontend.domain.model.Message
import com.chatappfrontend.domain.repository.MessageRepository
import com.example.database.LocalDataSource
import com.example.database.model.MessageEntity
import com.example.network.RemoteDataSource
import com.example.network.model.MessageDTO
import com.example.network.utils.safeApiCall
import com.example.security.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import networkBoundResource
import javax.inject.Inject

class DefaultMessageRepository @Inject constructor(
    private val webSocketManager: WebSocketManager,
    private val dataStoreManager: DataStoreManager,
    private val network: RemoteDataSource,
    private val database: LocalDataSource
) : MessageRepository {

    override val incomingMessages: SharedFlow<Message> = webSocketManager.incomingMessages

    override suspend fun sendMessage(
        recipientId: String,
        content: String
    ): ResultWrapper<Message.Status> {
        val userId = dataStoreManager.getUserId() ?: ""
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

    override suspend fun getChats(): ResultWrapper<List<ChatInfo>> {
        dataStoreManager.getUserId()?.let { userId ->
            Log.d("DefaultMessageRepository", "Fetching chats for userId: $userId")
            return safeApiCall(
                apiCall = {
                    Log.d(
                        "DefaultMessageRepository",
                        "Calling network.getChats with userId: $userId"
                    )
                    network.getChats(userId)
                },
                onSuccess = { body ->
                    Log.d("DefaultMessageRepository", "Received chats: ${body.chats}")
                    body.chats.map { it.toChatInfo() }
                }
            )
        }
        Log.e("DefaultMessageRepository", "User ID not found in DataStore")
        return ResultWrapper.Exception(
            Exception("User ID not found in DataStore")
        )
    }

//    override suspend fun getMessagesResource(chatId: String, userId: String) = networkBoundResource(
//        query = {
//            database.getMessages(chatId = chatId)
//        },
//        fetch = {
//            network.getMessages(chatId, userId)
//        },
//        saveFetchResult = {
//            database.insertMessages(
//                it.body()?.messages?.map { message -> message.toMessageEntity() } ?: emptyList())
//        },
//        shouldFetch = {
//            true
//        }
//    )

    override suspend fun getChatSession(
        chatId: String?,
        userId: String?
    ): ResultWrapper<ChatSession> {
        return safeApiCall(
            apiCall = {
                Log.d("DefaultMessageRepository", "Fetching messages for chatId: $chatId")
                network.getMessages(chatId, userId)
            },
            onSuccess = { body ->
                Log.d("DefaultMessageRepository", "Received messages: ${body}")
                insertMessages(body.messages.map { it.toMessage() })
                body.toChatSession()
            }
        )
    }

    override suspend fun insertMessages(messages: List<Message>) {
        val messageEntities = messages.map { it.toMessageEntity() }
        database.insertMessages(messageEntities)
    }

    override suspend fun getMessages(chatId: String): Flow<List<Message>> {
        return database.getMessages(chatId = chatId)
            .map { list ->
                list.map { entity ->
                    entity.toMessage()
                }
            }
    }
}