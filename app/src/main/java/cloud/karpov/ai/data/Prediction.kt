package clound.karpov.ai.data

data class Prediction(val ru: String, val en: String, val harmful: Boolean, val score: Float)
