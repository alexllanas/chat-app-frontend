package com.example.messages.viewmodel

import androidx.lifecycle.viewModelScope
import com.chatappfrontend.common.Result
import com.chatappfrontend.common.UiEvent
import com.chatappfrontend.domain.LogoutUseCase
import com.chatappfrontend.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageListViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : BaseViewModel() {

    fun logout() {
        viewModelScope.launch {
            val result = logoutUseCase.invoke()
            when (result) {
                is Result.Success -> {
                    emitUiEvent(event = UiEvent.Navigate("login"))
                }
                is Result.Failure -> { }
            }
        }
    }
}
