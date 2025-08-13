package com.chatappfrontend.network.di

import com.chatappfrontend.network.RemoteDataSource
import com.chatappfrontend.network.retrofit.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataModule {

    @Binds
    fun bindsRemoteDataSource(impl: RemoteDataSourceImpl) : RemoteDataSource

}