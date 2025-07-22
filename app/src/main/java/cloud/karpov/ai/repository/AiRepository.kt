package cloud.karpov.ai.repository

import cloud.karpov.ai.data.Prediction


interface AiRepository {
   suspend fun checkHarm(inputList: List<String>): Prediction
}
