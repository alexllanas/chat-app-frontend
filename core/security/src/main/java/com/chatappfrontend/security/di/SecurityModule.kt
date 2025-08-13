package com.chatappfrontend.security.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.chatappfrontend.security.DataStoreManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATA_STORE_NAME = "secure_prefs"

val Context.DataStore: DataStore<Preferences> by preferencesDataStore(name = DATA_STORE_NAME)

@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): DataStoreManager {
        return DataStoreManager(context)
    }
}