package com.chatappfrontend.data.di

import com.chatappfrontend.data.repository.DefaultAuthRepository
import com.example.network.CAFNetworkDataSource
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
        network: CAFNetworkDataSource,
        dataStoreManager: DataStoreManager
    ): DefaultAuthRepository {
        return DefaultAuthRepository(
            network = network,
            dataStoreManager = dataStoreManager
        )
    }
}