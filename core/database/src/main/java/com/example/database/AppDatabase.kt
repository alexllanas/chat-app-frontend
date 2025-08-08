package com.example.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.database.dao.MessageDao
import com.example.database.dao.UserDao
import com.example.database.model.ChatInfoEntity
import com.example.database.model.MessageEntity
import com.example.database.model.UserEntity

@Database(
    entities = [MessageEntity::class, ChatInfoEntity::class, UserEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun userDao(): UserDao
}