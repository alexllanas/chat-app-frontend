package com.chatappfrontend.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.chatappfrontend.navigation.AppNavHost

@Composable
internal fun CAFAppScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AppNavHost()
    }
}