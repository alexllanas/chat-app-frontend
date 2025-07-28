package com.example.network.di

import com.example.network.CAFNetworkDataSource
import com.example.network.retrofit.RetrofitCAFNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ApiModule {

    @Binds
    fun binds(impl: RetrofitCAFNetwork) : CAFNetworkDataSource

}