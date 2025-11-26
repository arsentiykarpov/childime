package cloud.karpov.ai.repository

import cloud.karpov.ai.data.PredictionResponse
import cloud.karpov.ai.data.Messages


interface AiRepository {
   suspend fun checkHarm(inputList: List<String>): PredictionResponse
   fun lastPrediction(): PredictionResponse
   fun surroundMessages(count: Int): Messages
}
