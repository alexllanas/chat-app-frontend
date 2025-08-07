package com.example.database.di

import androidx.room.Room
import com.example.database.AppDatabase
import com.example.database.LocalDataSource
import com.example.database.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataModule {

    @Binds
    @Singleton
    fun bindsLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource
}