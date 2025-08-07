package com.chatappfrontend.data.di

import com.chatappfrontend.data.repository.DefaultAuthRepository
import com.chatappfrontend.data.repository.DefaultMessageRepository
import com.chatappfrontend.data.repository.DefaultUserRepository
import com.chatappfrontend.data.websocket.WebSocketManager
import com.example.database.LocalDataSource
import com.example.network.RemoteDataSource
import com.example.security.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDefaultAuthRepository(
        network: RemoteDataSource,
        dataStoreManager: DataStoreManager
    ): DefaultAuthRepository {
        return DefaultAuthRepository(
            network = network,
            dataStoreManager = dataStoreManager
        )
    }

    @Provides
    @Singleton
    fun provideDefaultUserRepository(
        network: RemoteDataSource,
        dataStoreManager: DataStoreManager
    ): DefaultUserRepository {
        return DefaultUserRepository(
            network = network,
            dataStoreManager = dataStoreManager
        )
    }

    @Provides
    @Singleton
    fun provideMessageRepository(
        webSocketManager: WebSocketManager,
        dataStoreManager: DataStoreManager,
        network: RemoteDataSource,
        database: LocalDataSource
    ): DefaultMessageRepository {
        return DefaultMessageRepository(
            webSocketManager = webSocketManager,
            dataStoreManager = dataStoreManager,
            network = network,
            database = database
        )
    }
}