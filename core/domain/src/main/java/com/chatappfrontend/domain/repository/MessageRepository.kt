package com.chatappfrontend.domain.repository

import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.domain.model.Chat
import com.chatappfrontend.domain.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

interface MessageRepository {

    val incomingMessages: SharedFlow<Message>

    suspend fun sendMessage(
        recipientId: String,
        content: String
    ): ResultWrapper<Unit>

    suspend fun getChats(): ResultWrapper<List<Chat>>

    suspend fun getMessages(chatId: String): ResultWrapper<List<Message>>

    suspend fun checkIfChatExists(userId: String): ResultWrapper<String>
}