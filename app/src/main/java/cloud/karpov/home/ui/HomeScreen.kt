package cloud.karpov.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cloud.karpov.ai.data.Prediction
import cloud.karpov.ai.repository.AiRepository
import cloud.karpov.home.usecase.HomeViewState
import cloud.karpov.home.viewmodel.HomeViewModel
import cloud.karpov.home.viewmodel.HomeViewModelFactory
import dev.patrickgold.florisboard.ime.editor.AbstractEditorInstance

@Composable
fun HomeScreen(
    editorInstance: AbstractEditorInstance,
    navController: NavController,
    repo: AiRepository,
) {
    val viewModel: HomeViewModel =
        viewModel(factory = HomeViewModelFactory(repo, LocalContext.current))
    val viewState = viewModel.viewState.collectAsState()
    val state: HomeViewState = viewState.value
    var input by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }

    val content = editorInstance.activeContentFlow.collectAsState()

    when (state) {
        is HomeViewState.Loading -> {
            Text("Loading")
            viewModel.sendTestData()
        }

        is HomeViewState.OK -> {
            ChatList(
                entries = state.predict.prediction,
                onItemClick = { prediction ->
                    navController.navigate("details/${prediction}")
                }
            )
        }

        is HomeViewState.Error -> {
            Text(state.error.error.message!!)
        }

        is HomeViewState.DebugViewState -> {
            output = state.input
        }
    }
}

@Composable
fun NormalChatEntryView(
    score: Float,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier.padding(20.dp),
    onClick: () -> Unit = {}
) {
    val (icon, bgColor) = when {
        score >= 0.75f -> Icons.Filled.Warning to Color(0xFFB00020)
        score >= 0.4f -> Icons.Filled.Info to Color(0xFFFFA000)
        else -> Icons.Filled.CheckCircle to Color(0xFF2E7D32)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },          // клик по айтему
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(24.dp)
                .clip(CircleShape)
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(16.dp)
            )
        }

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
fun ChatList(
    entries: List<Prediction>,
    onItemClick: (String) -> Unit
) {
    LazyColumn {
        items(entries) { prediction ->
            val score = prediction.score
            val title = prediction.ru
            val subtitle = prediction.harmful.toString()

            NormalChatEntryView(
                score = score,
                title = title,
                subtitle = subtitle,
                onClick = { onItemClick(prediction.en.hashCode().toString()) }
            )
        }
    }
}

