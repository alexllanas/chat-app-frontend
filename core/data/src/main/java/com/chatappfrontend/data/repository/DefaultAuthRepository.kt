package com.chatappfrontend.data.repository

import com.chatappfrontend.common.ResultWrapper
import com.chatappfrontend.domain.model.User
import com.chatappfrontend.data.mapper.toUser
import com.chatappfrontend.domain.repository.AuthRepository
import com.example.network.CAFNetworkDataSource
import com.example.network.utils.safeApiCall
import com.example.security.DataStoreManager
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
    private val network: CAFNetworkDataSource,
    private val dataStoreManager: DataStoreManager
) : AuthRepository {

    override suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): ResultWrapper<User> {

        return safeApiCall(
            apiCall = {
                network.registerUser(
                    username = username,
                    email = email,
                    password = password
                )
            },
            onSuccess = { authResponse ->
                dataStoreManager.saveUserSession(
                    userId = authResponse.id,
                    username = authResponse.username,
                    email = authResponse.email,
                    accessToken = authResponse.accessToken,
                    lastLogin = authResponse.lastLogin ?: "",
                    createdAt = authResponse.createdAt
                )
                authResponse.toUser()
            }
        )
    }


    override suspend fun login(
        email: String,
        password: String
    ): ResultWrapper<User> {

        return safeApiCall(
            apiCall = {
                network.login(
                    email = email,
                    password = password
                )
            },
            onSuccess = { authResponse ->
                dataStoreManager.saveUserSession(
                    userId = authResponse.id,
                    username = authResponse.username,
                    email = authResponse.email,
                    accessToken = authResponse.accessToken,
                    lastLogin = authResponse.lastLogin ?: ""
                )
                authResponse.toUser()
            }
        )
    }

    override suspend fun logout() {
        dataStoreManager.clearUserSession()
    }
}