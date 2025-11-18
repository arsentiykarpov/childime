package cloud.karpov.details.ui

import android.annotation.SuppressLint
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cloud.karpov.ai.repository.AiRepository
import cloud.karpov.details.usecase.DetailsViewState
import cloud.karpov.details.viewmodel.DetailsViewModel
import cloud.karpov.details.viewmodel.DetailsViewModelFactory

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DetailsScreen(
    navController: NavController,
    repo: AiRepository
) {
    val viewModel: DetailsViewModel =
        viewModel(factory = DetailsViewModelFactory(repo, LocalContext.current))
    val viewState = viewModel.viewState.collectAsState()
    val state: DetailsViewState = viewState.value
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }

    when (state) {
        is DetailsViewState.Loading -> {
            Text("Loading")
        }

        is DetailsViewState.OK -> {
        }

        is DetailsViewState.Error -> {
            Text(state.error.error.message!!)
        }

        is DetailsViewState.DebugViewState -> TODO()
    }
}

