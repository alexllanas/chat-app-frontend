package com.chatappfrontend.ui

import androidx.lifecycle.ViewModel
import com.chatappfrontend.common.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

abstract class BaseViewModel: ViewModel() {

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent: SharedFlow<UiEvent> = _uiEvent

    protected suspend fun emitUiEvent(event: UiEvent) {
        _uiEvent.emit(event)
    }
}