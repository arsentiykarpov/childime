package cloud.karpov.ui

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Column
import androidx.navigation.NavHostController
import cloud.karpov.AuthViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cloud.karpov.AuthViewModelFactory
import cloud.karpov.di.AuthModule
import cloud.karpov.domain.repository.AuthRepository
import cloud.karpov.usecase.LoginAction
import cloud.karpov.usecase.LoginViewState

@Composable
fun LoginScreen(
    navController: NavController,
    repo: AuthRepository
) {
    val viewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(repo))
    val viewState = viewModel.viewState.collectAsState()
    val state: LoginViewState = viewState.value
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(state) {
        if (state is LoginViewState.OK) {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    when (state) {
        is LoginViewState.Loading -> {
            Text("Loading")
        }

        is LoginViewState.OK -> {
        }

        is LoginViewState.Error -> {
            Text(state.error.error.message!!)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            label = { Text("Username") },
            enabled = true,
            readOnly = false,
            onValueChange = { username = it },
            value = username,
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            label = { Text("Password") },
            enabled = true,
            readOnly = false,
            value = password,
            onValueChange = { password = it },
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            viewModel.login(username, password)
        }) {
            Text("Login")
        }
    }
}
