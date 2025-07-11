package cloud.karpov.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cloud.karpov.di.AuthModule
import cloud.karpov.ui.LoginScreen
import cloud.karpov.ui.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController, AuthModule.provideAuthRepository(LocalContext.current))
        }
        composable("home") {
            HomeScreen()
        }
    }
}
