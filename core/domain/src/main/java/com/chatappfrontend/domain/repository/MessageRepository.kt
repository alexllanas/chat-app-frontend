package com.chatappfrontend.domain.repository

import com.chatappfrontend.common.DataResource
import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.domain.model.ChatInfo
import com.chatappfrontend.domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface MessageRepository {

    val incomingMessages: SharedFlow<Message>

    suspend fun sendMessage(
        recipientId: String,
        content: String
    ): ResultWrapper<Message.Status>

    suspend fun getChats(currentUserId: String): Flow<DataResource<List<ChatInfo>>>

    suspend fun insertMessages(messages: List<Message>)

    suspend fun getMessages(chatId: String): Flow<DataResource<List<Message>>>

}