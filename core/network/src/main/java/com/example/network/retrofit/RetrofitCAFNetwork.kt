package com.example.network.retrofit

import com.example.network.BuildConfig
import com.example.network.CAFNetworkDataSource
import com.example.network.model.LoginDto
import com.example.network.model.RegisterDto
import com.example.network.model.UserDto
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitCAFNetworkApi {

     @POST("register")
     suspend fun registerUser(@Body registerDto: RegisterDto): Response<UserDto>

     @POST("login")
     suspend fun login(@Body loginDto: LoginDto): Response<UserDto>

}

@Singleton
class RetrofitCAFNetwork @Inject constructor(
    networkJson: Json,
    okHttpClient: OkHttpClient
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

    override suspend fun registerUser(username: String, email: String, password: String): Response<UserDto> {
        return networkApi.registerUser(
            registerDto = RegisterDto(
                username = username,
                email = email,
                password = password
            )
        )
    }

    override suspend fun login(email: String, password: String): Response<UserDto> {
        return networkApi.login(
            loginDto = LoginDto(
                email = email,
                password = password
            )
        )
    }
}