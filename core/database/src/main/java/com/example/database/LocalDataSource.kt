package com.example.database

import com.example.database.model.ChatInfoEntity
import com.example.database.model.MessageEntity
import com.example.database.model.UserEntity
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getMessages(chatId: String): Flow<List<MessageEntity>>
    suspend fun insertMessages(messages: List<MessageEntity>)

    fun getChats(): Flow<List<ChatInfoEntity>>
    suspend fun insertChats(chats: List<ChatInfoEntity>)

    fun getUsers(): Flow<List<UserEntity>>
    suspend fun insertUsers(users: List<UserEntity>)

}