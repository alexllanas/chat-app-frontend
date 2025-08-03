package com.chatappfrontend.data.di

import com.chatappfrontend.data.repository.DefaultAuthRepository
import com.chatappfrontend.data.repository.DefaultMessageRepository
import com.chatappfrontend.data.repository.DefaultUserRepository
import com.chatappfrontend.domain.repository.AuthRepository
import com.chatappfrontend.domain.repository.MessageRepository
import com.chatappfrontend.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    fun bindsAuthRepository(impl: DefaultAuthRepository): AuthRepository

    @Binds
    fun bindsUserRepository(impl: DefaultUserRepository): UserRepository

    @Binds
    fun bindsMessageRepository(impl: DefaultMessageRepository): MessageRepository
}