package com.example.messages.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chatappfrontend.common.Result
import com.chatappfrontend.common.UiEvent
import com.chatappfrontend.domain.LogoutUseCase
import com.example.security.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageListViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    fun logout() {
        viewModelScope.launch {
            val result = logoutUseCase.invoke()
            when (result) {
                is Result.Success -> {
                    _uiEvent.emit(UiEvent.Navigate("login"))
                }
                is Result.Failure -> { }
            }
        }
    }
}
