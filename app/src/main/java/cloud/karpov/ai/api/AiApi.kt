package cloud.karpov.ai.api

import cloud.karpov.ai.data.CheckHarmRequest
import cloud.karpov.ai.data.PredictionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AiApi {
  @GET("/")
  suspend fun checkhealth(): Any

  @POST("/predict/")
  suspend fun checkHarm(@Body input: CheckHarmRequest): PredictionResponse
}
