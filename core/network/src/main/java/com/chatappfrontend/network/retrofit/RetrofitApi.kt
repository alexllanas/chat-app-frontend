package com.chatappfrontend.network.retrofit

import com.chatappfrontend.network.model.AuthenticationResponseDTO
import com.chatappfrontend.network.model.ChatListInfoDTO
import com.chatappfrontend.network.model.LoginRequestDTO
import com.chatappfrontend.network.model.MessageDTO
import com.chatappfrontend.network.model.RegistrationRequestDTO
import com.chatappfrontend.network.model.UserListDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

internal interface RetrofitApi {

     @POST("register")
     suspend fun registerUser(@Body registrationRequestDTO: RegistrationRequestDTO): Response<AuthenticationResponseDTO>

     @POST("login")
     suspend fun login(@Body loginRequestDto: LoginRequestDTO): Response<AuthenticationResponseDTO>

    @GET("users/{id}")
    suspend fun getUsers(@Path("id") id: String): Response<UserListDTO>

    @GET("users/{userId}/chats")
    suspend fun getChats(@Path("userId") userId: String): Response<ChatListInfoDTO>

    @GET("chats/{chatId}")
    suspend fun getMessages
                (@Path("chatId") chatId: String?,
    ): Response<List<MessageDTO>>
}