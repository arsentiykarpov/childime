package cloud.karpov.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import cloud.karpov.ai.di.AiModule
import cloud.karpov.auth.di.AuthModule
import cloud.karpov.auth.ui.LoginScreen
import cloud.karpov.details.ui.DetailsScreen
import cloud.karpov.home.ui.HomeScreen
import cloud.karpov.keyboardApp

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    val aiRepo = remember {
        AiModule.provideAiRepositoryTest(context.applicationContext)
    }
    val authRepo = remember {
        AuthModule.provideAuthRepository(context)
    }

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                navController = navController,
                repo = authRepo
            )
        }

        composable("home") {
            HomeScreen(
                editorInstance = keyboardApp.get()!!.editorInstance.value,
                navController = navController,
                repo = aiRepo
            )
        }

        composable(
            route = "details/{predictionId}",
            arguments = listOf(
                navArgument("predictionId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("predictionId")
                ?: return@composable

            DetailsScreen(
                id = id,
                navController = navController,
                repo = aiRepo
            )
        }
    }
}

