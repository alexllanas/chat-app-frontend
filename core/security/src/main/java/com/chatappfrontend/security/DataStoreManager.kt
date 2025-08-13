package com.chatappfrontend.security

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.security.di.DataStore
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
    private val USERNAME_KEY = stringPreferencesKey("username")
    private val EMAIL_KEY = stringPreferencesKey("email")
    private val LAST_LOGIN_KEY = stringPreferencesKey("last_login")
    private val CREATED_AT_KEY = stringPreferencesKey("created_at")

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

    suspend fun saveUsername(username: String) {
        saveEncryptedValue(key = USERNAME_KEY, value = username)
    }

    suspend fun getUsername(): String? {
        return getEncryptedValue(key = USERNAME_KEY)
    }

    suspend fun saveEmail(email: String) {
        saveEncryptedValue(key = EMAIL_KEY, value = email)
    }

    suspend fun getEmail(): String? {
        return getEncryptedValue(key = EMAIL_KEY)
    }

    suspend fun saveLastLogin(timestamp: String) {
        saveEncryptedValue(
            key = LAST_LOGIN_KEY,
            value = timestamp
        )
    }

    suspend fun getLastLogin(): String? {
        return getEncryptedValue(key = LAST_LOGIN_KEY)
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
        userId: String,
        username: String,
        email: String,
        accessToken: String,
        lastLogin: String,
        createdAt: String? = null
    ): ResultWrapper<Unit> {
        return try {
            saveUserId(userId = userId)
            saveUsername(username = username)
            saveEmail(email = email)
            saveAccessToken(accessToken = accessToken)
            saveLastLogin(timestamp = lastLogin)
            createdAt?.let { timestamp ->
                saveCreatedAt(timestamp = timestamp)
            }
            ResultWrapper.Success(Unit)
        } catch (e: Exception) {
            ResultWrapper.Error(e)
        }
    }

    suspend fun saveCreatedAt(timestamp: String) {
        saveEncryptedValue(
            key = CREATED_AT_KEY,
            value = timestamp
        )
    }

    suspend fun getCreatedAt(): String? {
        return getEncryptedValue(key = CREATED_AT_KEY)
    }

    suspend fun clearUserSession() {
        try {
            clearKeys(
                ACCESS_TOKEN_KEY,
                USER_ID_KEY,
                USERNAME_KEY,
                EMAIL_KEY,
                LAST_LOGIN_KEY,
                CREATED_AT_KEY
            )
        } catch (e: Exception) {
            throw RuntimeException("Failed to clear user session: ${e.message}", e)
        }
    }
}