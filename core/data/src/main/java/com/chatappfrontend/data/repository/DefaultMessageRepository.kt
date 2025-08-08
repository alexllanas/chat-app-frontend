package com.chatappfrontend.data.repository

import android.util.Log
import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.data.MessagePayloadDTO
import com.chatappfrontend.data.mapper.toChatInfo
import com.chatappfrontend.data.mapper.toChatInfoEntity
import com.chatappfrontend.data.mapper.toMessage
import com.chatappfrontend.data.mapper.toMessageEntity
import com.chatappfrontend.data.websocket.WebSocketManager
import com.chatappfrontend.domain.model.Message
import com.chatappfrontend.domain.repository.MessageRepository
import com.example.database.LocalDataSource
import com.example.network.RemoteDataSource
import com.example.security.DataStoreManager
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.map
import networkBoundResource
import javax.inject.Inject

class DefaultMessageRepository @Inject constructor(
    private val webSocketManager: WebSocketManager,
    private val dataStoreManager: DataStoreManager,
    private val api: RemoteDataSource,
    private val db: LocalDataSource
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

    override suspend fun getChats(currentUserId: String) = networkBoundResource(
        query = {
            val x = db.getChats().map { list ->
                list.map { chatInfo ->
                    chatInfo.toChatInfo()
                }
            }
            x
        },
        fetch = {
            api.getChats(currentUserId = currentUserId).body()
        },
        saveFetchResult = { data ->
            val chatInfo = data?.chats?.map { it.toChatInfoEntity() } ?: listOf()
            db.insertChats(chatInfo)
        },
        shouldFetch = {
//            it.isEmpty()
            true
        }
    )

    override suspend fun insertMessages(messages: List<Message>) {
        val messageEntities = messages.map { it.toMessageEntity() }
        db.insertMessages(messageEntities)
    }

    override suspend fun getMessages(
        chatId: String,
    ) = networkBoundResource(
        query = {
            db.getMessages(chatId = chatId).map { list ->
                list.map {
                    it.toMessage()
                }
            }
        },
        fetch = {
            api.getMessages(
                chatId = chatId
            ).body()
        },
        saveFetchResult = { list ->
            val messages = list?.map { messageDTO -> messageDTO.toMessage() } ?: listOf()

            insertMessages(messages = messages)
        },
        shouldFetch = {
            // if last sync was > X mins/hours/days or is empty
            it.isEmpty()
        }
    )
}