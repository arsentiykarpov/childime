package cloud.karpov.home.ui

import cloud.karpov.ai.data.Prediction

sealed interface ChatRow {
  val id: String

  data class Normal(val entry: ChatEntry): ChatRow{

      override val id: String = "normal"
}
  data class Dangerous(val entry: ChatEntry): ChatRow{

      override val id: String = "danger"
}
}

data class ChatEntry(val prediction: Prediction)

fun createChatRow(prediction: Prediction): ChatRow {
  if (prediction.score > 0.8) {
    return ChatRow.Dangerous(ChatEntry(prediction))
  } else {
    return ChatRow.Normal(ChatEntry(prediction))
  }
}
