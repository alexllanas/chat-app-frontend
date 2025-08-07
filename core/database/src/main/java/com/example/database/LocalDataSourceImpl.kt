package com.example.database

import com.example.database.dao.MessageDao
import com.example.database.model.MessageEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val messageDao: MessageDao
) : LocalDataSource {

    override fun getMessages(chatId: String): Flow<List<MessageEntity>> {
        return messageDao.getMessages(chatId = chatId)
    }

    override suspend fun insertMessages(messages: List<MessageEntity>) {
        messageDao.insertMessages(messages = messages)
    }
}