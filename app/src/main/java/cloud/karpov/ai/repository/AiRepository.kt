package cloud.karpov.ai.repository

import cloud.karpov.ai.data.PredictionResponse


interface AiRepository {
   suspend fun checkHarm(inputList: List<String>): PredictionResponse
   fun lastPrediction(): PredictionResponse
}
