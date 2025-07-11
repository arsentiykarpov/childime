package cloud.karpov.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cloud.karpov.ui.LoginScreen
import cloud.karpov.ui.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen()
        }
        composable("home") {
            HomeScreen()
        }
    }
}
