package com.chatappfrontend.data.di

import com.chatappfrontend.data.repository.DefaultAuthRepository
import com.chatappfrontend.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsAuthRepository(impl: DefaultAuthRepository): AuthRepository
}