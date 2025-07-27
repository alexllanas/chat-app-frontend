package com.chatappfrontend.domain

import com.chatappfrontend.common.ActionResult
import com.chatappfrontend.data.repository.AuthRepository
import javax.inject.Inject
import kotlin.jvm.Throws

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
){

    suspend operator fun invoke() {
        authRepository.logout()
    }
}