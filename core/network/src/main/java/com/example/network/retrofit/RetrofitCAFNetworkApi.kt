package com.example.network.retrofit

import com.example.network.model.AuthenticationResponseDTO
import com.example.network.model.ChatDTO
import com.example.network.model.ChatListDTO
import com.example.network.model.LoginRequestDTO
import com.example.network.model.MessageDTO
import com.example.network.model.MessageListDTO
import com.example.network.model.RegistrationRequestDTO
import com.example.network.model.UserDTO
import com.example.network.model.UserListDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

internal interface RetrofitCAFNetworkApi {

     @POST("register")
     suspend fun registerUser(@Body registrationRequestDTO: RegistrationRequestDTO): Response<AuthenticationResponseDTO>

     @POST("login")
     suspend fun login(@Body loginRequestDto: LoginRequestDTO): Response<AuthenticationResponseDTO>

    @GET("users")
    suspend fun getUsers(): Response<UserListDTO>

    @GET("users/{userId}/chats")
    suspend fun getChats(@Path("userId") userId: String): Response<ChatListDTO>

    @GET("chats/{chatId}/messages")
    suspend fun getMessages
                (@Path("chatId") chatId: String
    ): Response<MessageListDTO>

    @GET("chats/exists")
    suspend fun checkIfChatExists(
        @Query("userId") userId: String,
        @Query("recipientId") recipientId: String
    ): Response<String>
}