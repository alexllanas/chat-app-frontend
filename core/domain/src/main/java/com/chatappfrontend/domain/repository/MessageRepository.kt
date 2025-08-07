package com.chatappfrontend.domain.repository

import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.domain.model.ChatInfo
import com.chatappfrontend.domain.model.ChatSession
import com.chatappfrontend.domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface MessageRepository {

    val incomingMessages: SharedFlow<Message>

    suspend fun sendMessage(
        recipientId: String,
        content: String
    ): ResultWrapper<Message.Status>

    suspend fun getChats(): ResultWrapper<List<ChatInfo>>

    suspend fun getChatSession(chatId: String?, userId: String?): ResultWrapper<ChatSession>

    suspend fun insertMessages(messages: List<Message>)

    suspend fun getMessages(chatId: String): Flow<List<Message>>

//    suspend fun getMessagesResource(chatId: String, userId: String): Flow<List<Message>>

}