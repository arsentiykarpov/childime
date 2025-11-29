package cloud.karpov.details.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Arrangement
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import cloud.karpov.ai.repository.AiRepository
import cloud.karpov.details.usecase.DetailsViewState
import cloud.karpov.details.viewmodel.DetailsViewModel
import cloud.karpov.details.viewmodel.DetailsViewModelFactory


enum class RiskLevel {
    LOW, MEDIUM, HIGH
}

data class ChatMessage(
    val id: String,
    val author: String,
    val text: String,
    val riskLevel: RiskLevel,
    val timestamp: String,
    val isFromChild: Boolean
)


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DetailsScreen(
    id: String,
    navController: NavController,
    repo: AiRepository
) {
    val viewModel: DetailsViewModel =
        viewModel(factory = DetailsViewModelFactory(repo, LocalContext.current))
    val viewState = viewModel.viewState.collectAsState()
    val state: DetailsViewState = viewState.value


    when (state) {
        is DetailsViewState.Loading -> {
        }

        is DetailsViewState.OK -> {
            Text("OK")
            val prediction = viewModel.findPrediction(id)
            val riskScore = prediction.score * 100
            var riskLevel = RiskLevel.HIGH
            if (riskScore < 50) {
                riskLevel = RiskLevel.LOW
            }
            if (riskScore > 50 && riskScore < 74) {
                riskLevel = RiskLevel.MEDIUM
            }
            val flaggedMessage = ChatMessage(id, "", prediction.ru, riskLevel, "", true)
            val surroundMessages: List<ChatMessage> =
                viewModel.surroundMessages(3).messages.map { it ->
                    ChatMessage(it.id, "", it.content, riskLevel, "", true)
                }
            val allMessages = surroundMessages + listOf<ChatMessage>(flaggedMessage)
            BullyingContextScreen(id, allMessages, riskLevel, riskScore.toInt(), {
              navController.popBackStack()
            }, {}, {}, {})
        }

        is DetailsViewState.Error -> {
            Text(state.error.error.message!!)
        }

        is DetailsViewState.DebugViewState -> TODO()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BullyingContextScreen(
    flaggedMessageId: String,
    messages: List<ChatMessage>,
    riskLevel: RiskLevel,
    riskScore: Int,
    onBackClick: () -> Unit,
    onMarkFalsePositive: () -> Unit,
    onMarkBullying: () -> Unit,
    onShowMoreContext: () -> Unit
) {
    val flaggedIndex = remember(messages, flaggedMessageId) {
        messages.indexOfFirst { it.id == flaggedMessageId }
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text("Проверка сообщения") }, navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack, contentDescription = "Назад"
                )
            }
        })
    }, bottomBar = {
        BottomActionBar(
            onMarkFalsePositive = onMarkFalsePositive,
            onMarkBullying = onMarkBullying,
            onShowMoreContext = onShowMoreContext
        )
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            RiskHeader(riskLevel = riskLevel, score = riskScore)

            Spacer(Modifier.height(12.dp))

            if (flaggedIndex == -1) {
                Text(
                    text = "Не удалось найти выделенное сообщение в контексте.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.75f)
                )
            } else {
                ContextMessagesList(
                    messages = messages, flaggedIndex = flaggedIndex
                )
            }
        }
    }
}


@Composable
fun RiskHeader(
    riskLevel: RiskLevel, score: Int
) {
    val (label, color) = when (riskLevel) {
        RiskLevel.LOW -> "Низкий риск" to Color(0xFF3C8C4F)
        RiskLevel.MEDIUM -> "Средний риск" to Color(0xFFD68F2B)
        RiskLevel.HIGH -> "Высокий риск" to MaterialTheme.colorScheme.error
    }

    Card(
        modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ), elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Warning, contentDescription = null, tint = color
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = label, style = MaterialTheme.typography.titleMedium, color = color
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "$score%",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = "Система обнаружила повышенную токсичность. Проверьте контекст, чтобы принять решение.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
            )
        }
    }
}


@Composable
fun ContextMessagesList(
    messages: List<ChatMessage>, flaggedIndex: Int
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.7f),
                shape = RoundedCornerShape(6.dp)
            )
            .clip(RoundedCornerShape(6.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
    ) {
        itemsIndexed(messages) { index, message ->
            when {
                index < flaggedIndex -> {
                    if (index == 0) {
                        SectionHeader("Сообщения до")
                    }
                    ContextMessageRow(
                        message = message, isBefore = true
                    )
                }

                index == flaggedIndex -> {
                    SectionHeader("Подозрительное сообщение")
                    FlaggedMessageRow(message = message)
                }

                index > flaggedIndex -> {
                    if (index == flaggedIndex + 1) {
                        SectionHeader("Сообщения после")
                    }
                    ContextMessageRow(
                        message = message, isBefore = false
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}


@Composable
fun ContextMessageRow(
    message: ChatMessage, isBefore: Boolean
) {
    val alpha = 0.65f

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .height(IntrinsicSize.Min)
        )

        Spacer(Modifier.width(8.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (message.isFromChild) "Ребёнок" else "Собеседник",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = message.timestamp,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
            }

            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)
            )
        }
    }
}


@Composable
fun FlaggedMessageRow(
    message: ChatMessage
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .width(3.dp)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.error)
        )

        Spacer(Modifier.width(8.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .background(
                    color = MaterialTheme.colorScheme.error.copy(alpha = 0.06f),
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (message.isFromChild) "Ребёнок" else "Собеседник",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = message.timestamp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Spacer(Modifier.weight(1f))

                val (label, color) = when (message.riskLevel) {
                    RiskLevel.LOW -> "Низкий риск" to Color(0xFF3C8C4F)
                    RiskLevel.MEDIUM -> "Средний риск" to Color(0xFFD68F2B)
                    RiskLevel.HIGH -> "Высокий риск" to MaterialTheme.colorScheme.error
                }

                Icon(
                    imageVector = Icons.Filled.Warning,
                    contentDescription = null,
                    tint = color
                )
            }

            Spacer(Modifier.height(2.dp))

            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable fun BottomActionBar(
    onMarkFalsePositive: () -> Unit,
    onMarkBullying: () -> Unit,
    onShowMoreContext: () -> Unit
) {
    Surface(tonalElevation = 2.dp) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TextButton(onClick = onMarkFalsePositive) {
                Text("Ложное срабатывание")
            }

            TextButton(onClick = onShowMoreContext) {
                Text("Больше контекста")
            }

            Spacer(Modifier.weight(1f, fill = false))

            Button(
                onClick = onMarkBullying,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Text("Буллинг", color = MaterialTheme.colorScheme.onError)
            }
        }
    }
}

