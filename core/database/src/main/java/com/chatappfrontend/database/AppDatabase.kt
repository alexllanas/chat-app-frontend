package com.chatappfrontend.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chatappfrontend.database.dao.MessageDao
import com.chatappfrontend.database.dao.UserDao
import com.chatappfrontend.database.model.ChatInfoEntity
import com.chatappfrontend.database.model.MessageEntity
import com.chatappfrontend.database.model.UserEntity

@Database(
    entities = [MessageEntity::class, ChatInfoEntity::class, UserEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun userDao(): UserDao
}