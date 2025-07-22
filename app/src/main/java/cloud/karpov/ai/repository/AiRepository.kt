package cloud.karpov.ai.repository

import clound.karpov.ai.data.Prediction

interface AiRepository {
   fun checkHarm(inputList: List<String>): Prediction
}