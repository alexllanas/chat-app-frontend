package com.chatappfrontend.data.repository

import com.chatappfrontend.common.ActionResult
import com.chatappfrontend.data.model.User
import com.chatappfrontend.data.model.mapper.toUser
import com.example.network.CAFNetworkDataSource
import com.example.network.NetworkResponseParser
import com.example.security.DataStoreManager
import javax.inject.Inject

internal class DefaultAuthRepository @Inject constructor(
    private val network: CAFNetworkDataSource,
    private val dataStoreManager: DataStoreManager
) : AuthRepository {

    override suspend fun registerUser(username: String, email: String, password: String): ActionResult<User> {
        val result = try {
            val response = network.registerUser(
                username = username,
                email = email,
                password = password
            )
            if (response.isSuccessful) {
                val userDto = response.body()
                if (userDto != null) {
                    dataStoreManager.saveUserSession(
                        accessToken = userDto.accessToken,
                        userId = userDto.id,
                        username = userDto.username,
                        email = userDto.email
                    )
                    val user = userDto.toUser()
                    ActionResult.Success(user)
                } else {
                    ActionResult.Error(response.code(), NetworkResponseParser.getErrorMessage(response))
                }
            } else {
                ActionResult.Error(response.code(), NetworkResponseParser.getErrorMessage(response))
            }
        } catch (e: Exception) {
            ActionResult.Exception(e)
        }
        return result
    }

    override suspend fun login(email: String, password: String): ActionResult<User> {
        val result = try {
            val response = network.login(
                email = email,
                password = password
            )
            if (response.isSuccessful) {
                val userDto = response.body()
                if (userDto != null) {
                    dataStoreManager.saveUserSession(
                        accessToken = userDto.accessToken,
                        username = userDto.username,
                        userId = userDto.id,
                        email = userDto.email
                    )
                    val user = userDto.toUser()
                    ActionResult.Success(user)
                } else {
                    ActionResult.Error(response.code(), NetworkResponseParser.getErrorMessage(response))
                }
            } else {
                ActionResult.Error(response.code(), NetworkResponseParser.getErrorMessage(response))
            }
        } catch (e: Exception) {
            ActionResult.Exception(e)
        }
        return result
    }

    override suspend fun logout() {
        dataStoreManager.clearUserSession()
    }
}