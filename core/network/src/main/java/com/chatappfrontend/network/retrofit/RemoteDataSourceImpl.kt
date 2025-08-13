package com.chatappfrontend.network.retrofit

import com.example.network.BuildConfig
import com.chatappfrontend.network.RemoteDataSource
import com.chatappfrontend.network.model.AuthenticationResponseDTO
import com.chatappfrontend.network.model.ChatListInfoDTO
import com.chatappfrontend.network.model.LoginRequestDTO
import com.chatappfrontend.network.model.MessageDTO
import com.chatappfrontend.network.model.RegistrationRequestDTO
import com.chatappfrontend.network.model.UserListDTO
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSourceImpl @Inject constructor(
    networkJson: Json,
    okHttpClient: OkHttpClient,
) : RemoteDataSource {

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
            .create(RetrofitApi::class.java)

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

    override suspend fun getUsers(id: String): Response<UserListDTO> {
        return networkApi.getUsers(id)
    }

    override suspend fun getChats(currentUserId: String): Response<ChatListInfoDTO> {
        return networkApi.getChats(userId = currentUserId)
    }

    override suspend fun getMessages(chatId: String?): Response<List<MessageDTO>> {
        return networkApi.getMessages(chatId = chatId)
    }
}