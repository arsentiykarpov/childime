package cloud.karpov.ai.data

import kotlinx.serialization.Serializable

@Serializable
data class Prediction(val ru: String, val en: String, val harmful: Boolean, val score: Float)
