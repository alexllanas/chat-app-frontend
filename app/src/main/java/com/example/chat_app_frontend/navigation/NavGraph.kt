
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chat_app_frontend.LoginScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController, startDestination = "register") {

        composable("register") { RegisterScreen(
            onRegisterClicked = { email, password, confirmPassword ->
            },
            onNavigateToLogin = {
                navController.navigate("login") {
                }
            }
        ) }

        composable("login") { LoginScreen(
            onLoginClicked = { email, password -> },
            onNavigateToRegister = {
                navController.navigate("register") {
                    popUpTo("login") { inclusive = false }
                }
            }
        ) }
    }
}