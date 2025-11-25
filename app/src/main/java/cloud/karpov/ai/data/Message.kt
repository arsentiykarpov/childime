package cloud.karpov.ai.data

import kotlinx.serialization.Serializable

@Serializable
data class Messages(val messages: List<Message>)

@Serializable
data class Message(val id: String, val content: String)
