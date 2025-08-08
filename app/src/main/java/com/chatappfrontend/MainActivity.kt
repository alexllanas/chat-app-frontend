package com.chatappfrontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.chatappfrontend.navigation.AppNavHost
import com.chatappfrontend.theme.ChatappfrontendTheme
import com.chatappfrontend.ui.CAFAppScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            ChatappfrontendTheme {
                CAFAppScreen()
            }
        }
    }
}
