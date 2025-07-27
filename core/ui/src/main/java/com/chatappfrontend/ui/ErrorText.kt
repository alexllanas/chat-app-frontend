package com.chatappfrontend.ui

import androidx.compose.runtime.Composable
import androidx.compose.material3.*
import androidx.compose.ui.Modifier

@Composable
fun ErrorText(
    modifier: Modifier = Modifier,
    errorMessage: String?
) {
    if (!errorMessage.isNullOrBlank()) {
        Text(
            modifier = modifier,
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}