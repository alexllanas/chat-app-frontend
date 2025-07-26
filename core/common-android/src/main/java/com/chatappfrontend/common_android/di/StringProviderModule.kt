package com.example.common_android.di

import android.content.Context
import com.example.common_android.DefaultStringProvider
import com.chatappfrontend.common_android.StringProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class StringProviderModule {

    @Provides
    fun provideStringProvider(@ApplicationContext context: Context): StringProvider {
        return DefaultStringProvider(context = context)
    }
}