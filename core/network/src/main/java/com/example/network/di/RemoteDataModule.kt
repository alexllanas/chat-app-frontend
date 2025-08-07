package com.example.network.di

import com.example.network.RemoteDataSource
import com.example.network.retrofit.RemoteDataSourceImpl
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