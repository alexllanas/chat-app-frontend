package com.chatappfrontend.database.di

import com.chatappfrontend.database.LocalDataSource
import com.chatappfrontend.database.LocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataModule {

    @Binds
    @Singleton
    fun bindsLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource
}