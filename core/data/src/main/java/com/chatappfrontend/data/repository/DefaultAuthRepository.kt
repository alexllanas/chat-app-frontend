package com.chatappfrontend.data.repository

import android.util.Log
import com.chatappfrontend.common.NetworkResult
import com.chatappfrontend.common.Result
import com.example.model.User
import com.example.network.CAFNetworkDataSource
import com.example.security.TokenManager
import javax.inject.Inject

internal class DefaultAuthRepository @Inject constructor(
    private val network: CAFNetworkDataSource,
    private val tokenManager: TokenManager
) : AuthRepository {

    override suspend fun registerUser(email: String, password: String): NetworkResult<User> {
        val result = try {
            val response = network.registerUser(
                email = email,
                password = password
            )
            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    tokenManager.saveToken(user.accessToken)
                    NetworkResult.Success(user)
                } else {
                    NetworkResult.Error(response.code(), "User registration failed: No user data returned")
                }
            } else {
                NetworkResult.Error(response.code(), "User registration failed: ${response.errorBody()?.string() ?: "Unknown error"}")
            }
        } catch (e: Exception) {
            NetworkResult.Exception(e)
        }
        return result
    }

    override suspend fun login(email: String, password: String): NetworkResult<User> {
        val result = try {
            val response = network.login(
                email = email,
                password = password
            )
            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    tokenManager.saveToken(user.accessToken)
                    NetworkResult.Success(user)
                } else {
                    NetworkResult.Error(response.code(), "Login failed: No user data returned")
                }
            } else {
                NetworkResult.Error(response.code(), "Login failed: ${response.errorBody()?.string() ?: "Unknown error"}")
            }
        } catch (e: Exception) {
            NetworkResult.Exception(e)
        }
        return result
    }

    override suspend fun logout(): Result {
        return tokenManager.clearToken()
    }
}