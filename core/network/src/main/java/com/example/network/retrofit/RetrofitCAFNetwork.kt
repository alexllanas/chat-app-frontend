package com.example.network.retrofit

import com.example.network.BuildConfig
import com.example.network.CAFNetworkDataSource
import com.example.network.model.AuthenticationResponseDTO
import com.example.network.model.ChatDTO
import com.example.network.model.ChatListDTO
import com.example.network.model.LoginRequestDTO
import com.example.network.model.MessageDTO
import com.example.network.model.MessageListDTO
import com.example.network.model.RegistrationRequestDTO
import com.example.network.model.UserDTO
import com.example.network.model.UserListDTO
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitCAFNetwork @Inject constructor(
    networkJson: Json,
    okHttpClient: OkHttpClient,
) : CAFNetworkDataSource {

    private val baseUrl = if (BuildConfig.USE_REMOTE_SERVER) {
        BuildConfig.REMOTE_SERVER_URL
    } else {
        BuildConfig.LOCAL_SERVER_URL
    }

    private val networkApi =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(RetrofitCAFNetworkApi::class.java)

    override suspend fun registerUser(username: String, email: String, password: String): Response<AuthenticationResponseDTO> {
        return networkApi.registerUser(
            registrationRequestDTO = RegistrationRequestDTO(
                username = username,
                email = email,
                password = password
            )
        )
    }

    override suspend fun login(email: String, password: String): Response<AuthenticationResponseDTO> {
        return networkApi.login(
            loginRequestDto = LoginRequestDTO(
                email = email,
                password = password
            )
        )
    }

    override suspend fun getUsers(): Response<UserListDTO> {
        return networkApi.getUsers()
    }

    override suspend fun getChats(userId: String): Response<ChatListDTO> {
        return networkApi.getChats(userId = userId)
    }

    override suspend fun getMessages(chatId: String): Response<MessageListDTO> {
        return networkApi.getMessages(chatId = chatId)
    }

    override suspend fun checkIfChatExists(userId: String, recipientId: String): Response<String> {
        return networkApi.checkIfChatExists(userId = userId, recipientId = recipientId)
    }
}