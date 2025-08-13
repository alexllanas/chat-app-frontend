package com.chatappfrontend.app.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chatappfrontend.app.navigation.AppNavHost

@Composable
internal fun CAFAppScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AppNavHost()
    }
}