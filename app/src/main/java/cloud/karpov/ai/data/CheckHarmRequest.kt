package cloud.karpov.ai.data

import kotlinx.serialization.Serializable

@Serializable
data class CheckHarmRequest(val data: Data)

@Serializable
data class Data(val input: List<String>)
