package com.example.database

import com.example.database.model.MessageEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getMessages(chatId: String): Flow<List<MessageEntity>>

    suspend fun insertMessages(messages: List<MessageEntity>)
}