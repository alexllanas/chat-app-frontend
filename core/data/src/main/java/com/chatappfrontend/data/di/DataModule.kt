package com.chatappfrontend.data.di

import com.chatappfrontend.data.repository.AuthRepository
import com.chatappfrontend.data.repository.DefaultAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {

    @Binds
    abstract fun bindsAuthRepository(
        authRepository: DefaultAuthRepository
    ): AuthRepository
}