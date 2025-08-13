package com.chatappfrontend.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS chats (
                id TEXT NOT NULL PRIMARY KEY,
                userId TEXT NOT NULL,
                username TEXT NOT NULL,
                lastMessage TEXT NOT NULL,
                lastMessageTimeStamp TEXT NOT NULL
            )
        """.trimIndent()
        )
    }
}
