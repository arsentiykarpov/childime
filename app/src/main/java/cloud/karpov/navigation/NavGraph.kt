package cloud.karpov.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cloud.karpov.ai.di.AiModule
import cloud.karpov.auth.di.AuthModule
import cloud.karpov.auth.ui.LoginScreen
import cloud.karpov.home.ui.HomeScreen
import cloud.karpov.keyboardApp

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("login") {
            LoginScreen(navController, AuthModule.provideAuthRepository(LocalContext.current))
        }
        composable("home") {
            HomeScreen(keyboardApp.get()!!.editorInstance.value, navController, AiModule.provideAiRepository(LocalContext.current))
        }
    }
}
