package com.example.security

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.chatappfrontend.common.Result
import com.example.security.di.DataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val dataStore = context.DataStore
    private val ACCESS_TOKEN_KEY = stringPreferencesKey("access_token")
    private val USER_ID_KEY = stringPreferencesKey("user_id")
    private val EMAIL_KEY = stringPreferencesKey("email")

    private suspend fun <T> saveEncryptedValue(
        key: Preferences.Key<String>,
        value: T
    ) {
        val encrypted = CryptUtil.encrypt(value.toString())
        dataStore.edit { prefs ->
            prefs[key] = encrypted
        }
    }

    private suspend fun getEncryptedValue(key: Preferences.Key<String>): String? {
        return dataStore.data
            .map { prefs ->
                prefs[key]?.let {
                    try {
                        CryptUtil.decrypt(it)
                    } catch (e: Exception) {
                        null
                    }
                }
            }
            .firstOrNull()
    }

    suspend fun saveAccessToken(accessToken: String) {
        saveEncryptedValue(ACCESS_TOKEN_KEY, accessToken)
    }

    suspend fun getAccessToken(): String? {
        return getEncryptedValue(ACCESS_TOKEN_KEY)
    }

    suspend fun saveUserId(userId: String) {
        saveEncryptedValue(key = USER_ID_KEY, value = userId)
    }

    suspend fun getUserId(): String? {
        return getEncryptedValue(key = USER_ID_KEY)
    }

    suspend fun saveEmail(email: String) {
        saveEncryptedValue(key = EMAIL_KEY, value = email)
    }

    suspend fun getEmail(): String? {
        return getEncryptedValue(EMAIL_KEY)
    }

    private suspend fun clearKeys(vararg keys: Preferences.Key<*>) {
        try {
            dataStore.edit { prefs ->
                keys.forEach { key ->
                    prefs.remove(key)
                }
            }
        } catch (e: Exception) {
            throw RuntimeException("Failed to clear keys: ${e.message}", e)
        }
    }

    suspend fun saveUserSession(
        accessToken: String,
        userId: String,
        email: String
    ): Result {
        return try {
            saveAccessToken(accessToken = accessToken)
            saveUserId(userId = userId)
            saveEmail(email = email)
            Result.Success
        } catch (e: Exception) {
            Result.Failure(e.message ?: "Failed to save session")
        }
    }

    suspend fun clearUserSession(): Result {
        try {
            clearKeys(
                ACCESS_TOKEN_KEY,
                USER_ID_KEY,
                EMAIL_KEY
            )
        } catch (e: Exception) {
            return Result.Failure(e.message ?: "Failed to clear session")
        }
        return Result.Success
    }
}