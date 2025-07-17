package com.chatappfrontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.chatappfrontend.navigation.AppNavHost
import com.chatappfrontend.theme.ChatappfrontendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChatappfrontendTheme {
                AppNavHost()
            }
        }
    }
}
