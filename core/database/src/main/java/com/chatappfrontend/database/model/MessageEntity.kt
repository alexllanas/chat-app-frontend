package com.chatappfrontend.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "messages")
class MessageEntity (

    @PrimaryKey
    val id: String,

    val chatId: String,

    val senderId: String,

    val recipientId: String,

    val content: String,

    val createdAt: String,

    val isRead: Boolean
)