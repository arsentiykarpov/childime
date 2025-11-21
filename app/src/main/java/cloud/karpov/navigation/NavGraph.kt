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
import cloud.karpov.details.ui.DetailsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController, AuthModule.provideAuthRepository(LocalContext.current))
        }
        composable("home") {
          DetailsScreen(navController, AiModule.provideAiRepositoryTest(LocalContext.current))
            //HomeScreen(keyboardApp.get()!!.editorInstance.value, navController, AiModule.provideAiRepositoryTest(LocalContext.current))
        }
    }
}
