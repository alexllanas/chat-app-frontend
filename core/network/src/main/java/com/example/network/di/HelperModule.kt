package com.example.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json

@Module
@InstallIn(SingletonComponent::class)
internal object HelperModule {

    @Provides
    internal fun providesNetworkJson(): Json = Json {
        ignoreUnknownKeys = true
    }
}