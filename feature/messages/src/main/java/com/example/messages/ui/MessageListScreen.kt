package com.example.messages.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.chatappfrontend.common.UiEvent
import com.example.messages.viewmodel.MessageListViewModel

@Composable
fun MessageListScreen(
    modifier: Modifier = Modifier,
    viewModel: MessageListViewModel = hiltViewModel(),
    onLogout: (String) -> Unit,
) {

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> {
                    onLogout(event.route)
                }
                is UiEvent.ShowSnackbar -> {}
            }
        }
    }
    Column(modifier = modifier) {
        Text("Message List Screen")
        TextButton(
            onClick = viewModel::logout,
        ) {
            Text("Logout")
        }
    }
}