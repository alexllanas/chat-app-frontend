package com.chatappfrontend.database

import com.chatappfrontend.database.dao.MessageDao
import com.chatappfrontend.database.dao.UserDao
import com.chatappfrontend.database.model.ChatInfoEntity
import com.chatappfrontend.database.model.MessageEntity
import com.chatappfrontend.database.model.UserEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val messageDao: MessageDao,
    private val userDao: UserDao
) : LocalDataSource {

    override fun getMessages(chatId: String): Flow<List<MessageEntity>> {
        return messageDao.getMessages(chatId = chatId)
    }
    override suspend fun insertMessages(messages: List<MessageEntity>) {
        messageDao.insertMessages(messages = messages)
    }

    override fun getChats(): Flow<List<ChatInfoEntity>> {
        return messageDao.getChats()
    }
    override suspend fun insertChats(chats: List<ChatInfoEntity>) {
        return messageDao.insertChats(chatInfo = chats)
    }

    override fun getUsers(): Flow<List<UserEntity>> {
        return userDao.getUsers()
    }
    override suspend fun insertUsers(users: List<UserEntity>) {
        userDao.insertUsers(users = users)
    }
}