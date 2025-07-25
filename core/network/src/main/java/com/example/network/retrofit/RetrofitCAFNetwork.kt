package com.example.network.retrofit

import com.example.model.ProtectedResponse
import com.example.model.User
import com.example.network.CAFNetworkDataSource
import com.example.network.model.RegisterRequest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitCAFNetworkApi {

     @POST("register")
     suspend fun registerUser(@Body registerRequest: RegisterRequest): Response<User>

     @GET("protected")
     suspend fun testTokenAuthentication(): ProtectedResponse
}

@Singleton
internal class RetrofitCAFNetwork @Inject constructor(
    networkJson: Json,
    okHttpClient: OkHttpClient
) : CAFNetworkDataSource {

    private val networkApi =
        Retrofit.Builder()
//            .baseUrl("http://52.15.221.75:3000/api/v1/")
            .baseUrl("http://10.0.2.2:3000/api/v1/")
            .client(okHttpClient)
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(RetrofitCAFNetworkApi::class.java)

    override suspend fun registerUser(email: String, password: String): Response<User> {
        return networkApi.registerUser(
            registerRequest = RegisterRequest(email = email, password = password)
        )
    }

    override suspend fun testTokenAuthentication(): ProtectedResponse {
        return networkApi.testTokenAuthentication()
    }

}