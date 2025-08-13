package com.chatappfrontend.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chatappfrontend.database.model.ChatInfoEntity
import com.chatappfrontend.database.model.MessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @Query("SELECT * FROM messages WHERE chatId = :chatId ORDER BY createdAt DESC")
    fun getMessages(chatId: String): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(messages: List<MessageEntity>)



    @Query("SELECT * FROM chats ORDER BY lastMessageTimeStamp DESC")
    fun getChats(): Flow<List<ChatInfoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChats(chatInfo: List<ChatInfoEntity>)

}