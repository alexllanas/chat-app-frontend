package com.example.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chats")
class ChatInfoEntity (

    @PrimaryKey
    val id: String,
    val userId: String,
    val username: String,
    val lastMessage: String,
    val lastMessageTimeStamp: String,
)