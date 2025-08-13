package com.chatappfrontend.database.di

import android.content.Context
import androidx.room.Room
import com.chatappfrontend.database.AppDatabase
import com.chatappfrontend.database.MIGRATION_1_2
import com.chatappfrontend.database.dao.MessageDao
import com.chatappfrontend.database.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "app_database"
        )
            .fallbackToDestructiveMigration(true)
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    @Provides
    @Singleton
    fun providesMessageDao(database: AppDatabase): MessageDao {
        return database.messageDao()
    }

    @Provides
    @Singleton
    fun providesUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }
}