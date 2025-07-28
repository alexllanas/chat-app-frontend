package com.chatappfrontend.data.repository

import com.chatappfrontend.common.ActionResult
import com.chatappfrontend.domain.model.User
import com.chatappfrontend.data.mapper.toUser
import com.chatappfrontend.domain.repository.AuthRepository
import com.example.network.CAFNetworkDataSource
import com.example.network.utils.ResponseErrorParser
import com.example.security.DataStoreManager
import javax.inject.Inject

class DefaultAuthRepository @Inject constructor(
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
                    ActionResult.Error(response.code(), ResponseErrorParser.parseMessage(response.errorBody()?.string()))
                }
            } else {
                ActionResult.Error(response.code(), ResponseErrorParser.parseMessage(response.errorBody()?.string()))
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
                    ActionResult.Error(response.code(), ResponseErrorParser.parseMessage(response.errorBody()?.string()))
                }
            } else {
                ActionResult.Error(response.code(), ResponseErrorParser.parseMessage(response.errorBody()?.string()))
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