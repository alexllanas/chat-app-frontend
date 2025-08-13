package com.chatappfrontend.data.di

import com.chatappfrontend.data.repository.DefaultAuthRepository
import com.chatappfrontend.data.repository.DefaultMessageRepository
import com.chatappfrontend.data.repository.DefaultUserRepository
import com.chatappfrontend.data.websocket.WebSocketManager
import com.chatappfrontend.database.LocalDataSource
import com.chatappfrontend.network.RemoteDataSource
import com.chatappfrontend.security.DataStoreManager
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
        database: LocalDataSource
    ): DefaultUserRepository {
        return DefaultUserRepository(
            db = database,
            api = network
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
            api = network,
            db = database
        )
    }
}