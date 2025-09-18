package cloud.karpov.home.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cloud.karpov.ai.repository.AiRepository
import cloud.karpov.home.usecase.HomeViewState
import cloud.karpov.home.viewmodel.HomeViewModel
import cloud.karpov.home.viewmodel.HomeViewModelFactory
import dev.patrickgold.florisboard.ime.editor.AbstractEditorInstance
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    editorInstance: AbstractEditorInstance,
    navController: NavController,
    repo: AiRepository
) {
    val viewModel: HomeViewModel =
        viewModel(factory = HomeViewModelFactory(repo, LocalContext.current))
    val viewState = viewModel.viewState.collectAsState()
    val state: HomeViewState = viewState.value
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }

    @Composable
    fun NormmalChatEntryView(
        icon: ImageVector,
        score: Float,
        title: String,
        subtitle: String,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon on the left
            Box(
                modifier = Modifier
                    .size(20.dp)
                    .clip(CircleShape)
                    .background(Color.Red),
                contentAlignment = Alignment.Center
            ) {
              //  ext(
              //      text = score.toString(),
              //      color = textColor,
              //      style = MaterialTheme.typography.bodyMedium,
              //  )
            }

            // Title and subtitle stacked vertically
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }

    @Composable
    fun ChatList(entires: List<ChatEntry>) {
        LazyColumn {
            items(entires) { message ->
                if (message.prediction.score < 0.8) {
//                    NormmalChatEntryView()
                }
            }
        }
    }

 //   @Composable
 //   fun createAdapter(): AdaptAdapter<ChatEntry> {
 //       val context = LocalContext.current
 //       val rootView = LocalView.current as ViewGroup
 //       return adapt<ChatEntry> {
 //           create {
 //               val chatEntryView =
 //                   ComposableItemBinding.inflate(LayoutInflater.from(context), it, false)
 //               ViewSource.BindingViewSource(
 //                   chatEntryView,
 //                   ViewBinding::getRoot
 //               )
 //           }.bind {
 //               binding.composeView.
 //           }
 //       }
 //   }
    LaunchedEffect(state) {
//        if (state is HomeViewState.OK) {
//            navController.navigate("home") {
//                popUpTo("login") { inclusive = true }
//            }
//        }
    }

    when (state) {
        is HomeViewState.Loading -> {
            Text("Loading")
        }

        is HomeViewState.OK -> {
            Text("OK")
            val compiledOutput =
                state.predict.prediction.map { return@map it.ru + " score: " + it.score.toString() }
                    .joinToString(separator = ";")
            output = compiledOutput
        }

        is HomeViewState.Error -> {
            Text(state.error.error.message!!)
        }

        is HomeViewState.DebugViewState -> {
            output = state.input
        }
    }

    val content = editorInstance.activeContentFlow.collectAsState()
    MainScope().launch {
        content.apply {
            output = this.value.text
        }
    }
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    )
    {
        TextField(
            value = input,
            onValueChange = { input = it; viewModel.predict(input) },
            enabled = true,
            readOnly = false,
            label = { Text("Ввод") })

        TextField(
            value = output,
            onValueChange = { output = it },
            minLines = 20,
            enabled = true,
            readOnly = false,
            label = { Text("Backend answer") })
    }
}

