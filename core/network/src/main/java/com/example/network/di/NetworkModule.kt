package com.example.network.di

import com.example.network.CAFNetworkDataSource
import com.example.network.retrofit.RetrofitCAFNetwork
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
internal interface NetworkModule {

    @Binds
    fun binds(impl: RetrofitCAFNetwork) : CAFNetworkDataSource

}