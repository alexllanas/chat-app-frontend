package com.chatappfrontend.domain

import android.util.Log
import com.chatappfrontend.data.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) {
        val result = authRepository.registerUser(
            email = email,
            password = password
        )
        Log.d("RegisterUserUseCase", "User registered: $result")
    }
}