package com.example.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class UserEntity(
    @PrimaryKey
    val id: String,
    val username: String,
    val email: String,
    val chatId: String
)