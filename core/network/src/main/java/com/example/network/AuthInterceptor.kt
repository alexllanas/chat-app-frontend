package com.example.network

import com.example.security.DataStoreManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request()
        val token = runBlocking { dataStoreManager.getAccessToken() }

        val authReq = if (!token.isNullOrBlank()) {
            req.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            req
        }
        return chain.proceed(authReq)
    }
}