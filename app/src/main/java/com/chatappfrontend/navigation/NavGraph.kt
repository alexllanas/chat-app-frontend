package com.chatappfrontend.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.chatappfrontend.auth.ui.LoginScreen
import com.chatappfrontend.auth.ui.RegisterScreen
import com.chatappfrontend.common.navigation.Screen
import com.example.messages.ui.MessageListScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "register"
    ) {

        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToSignUp = {
                    navController.navigate(Screen.Register.route) {
                        popUpTo("login") { inclusive = false }
                    }
                },
                onLoginSuccess = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                navigateToLoginScreen = {
                    navController.navigate(Screen.Login.route)
                },
                onRegisterSuccess = { route ->
                    navController.navigate(route)
                }
            )
        }

        composable(Screen.MessageList.route) {
             MessageListScreen(
                 onLogout = { route ->
                     navController.navigate(route)
                 }
             )
        }
    }
}