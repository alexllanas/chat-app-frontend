package com.example.security

import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.chatappfrontend.common.Result
import com.example.security.di.DataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore = context.DataStore
    private val JWT_KEY = stringPreferencesKey("jwt_token")

    private val tokenFlow: Flow<String?> = dataStore.data
        .map { prefs ->
            prefs[JWT_KEY]?.let {
                try {
                    CryptUtil.decrypt(it)
                } catch (e: Exception) {
                    null
                }
            }
        }

    suspend fun saveToken(token: String) {
        val encrypted = CryptUtil.encrypt(token)
        dataStore.edit { prefs -> prefs[JWT_KEY] = encrypted }
    }

    suspend fun getToken(): String? {
        return tokenFlow.firstOrNull()
    }

    suspend fun clearToken(): Result {
        try {
            dataStore.edit { prefs -> prefs.remove(JWT_KEY) }
        } catch (e: Exception) {
            return Result.Failure(e.message ?: "Failed to clear token")
        }
        return Result.Success
    }
}