package cloud.karpov.ai.di

import android.content.Context
import cloud.karpov.ai.data.CheckHarmRequest
import cloud.karpov.ai.data.Data
import cloud.karpov.ai.data.PredictionResponse
import cloud.karpov.ai.repository.AiRepository
import cloud.karpov.appContext
import kotlinx.serialization.json.Json

class AiModule {
 companion object {
   val testPrediction = "{\n" +
           "  \"prediction\": [\n" +
           "    {\n" +
           "      \"ru\": \"я тебя выебу\",\n" +
           "      \"en\": \"I'll fuck you\",\n" +
           "      \"harmful\": true,\n" +
           "      \"score\": 0.78\n" +
           "    },\n" +
           "    {\n" +
           "      \"ru\": \"Я могу забрать тебя из школы, только не говори родителям, хорошо?\",\n" +
           "      \"en\": \"I can pick you up from school, just don't tell your parents, okay?\",\n" +
           "      \"harmful\": true,\n" +
           "      \"score\": 0.95\n" +
           "    },\n" +
           "    {\n" +
           "      \"ru\": \"Когда вырасту, хочу стать космонавтом!\",\n" +
           "      \"en\": \"When I grow up, I want to become an astronaut!\",\n" +
           "      \"harmful\": false,\n" +
           "      \"score\": 0.25\n" +
           "    },\n" +
           "    {\n" +
           "      \"ru\": \"Почему нельзя говорить родителям?\",\n" +
           "      \"en\": \"Why can't you tell your parents?\",\n" +
           "      \"harmful\": true,\n" +
           "      \"score\": 0.87\n" +
           "    },\n" +
           "    {\n" +
           "      \"ru\": \"Дела хорошо\",\n" +
           "      \"en\": \"Things are going well\",\n" +
           "      \"harmful\": true,\n" +
           "      \"score\": 0.84\n" +
           "    },\n" +
           "    {\n" +
           "      \"ru\": \"Я пошла гулять\",\n" +
           "      \"en\": \"I went for a walk\",\n" +
           "      \"harmful\": true,\n" +
           "      \"score\": 0.66\n" +
           "    }\n" +
           "  ]\n" +
           "}"
   fun provideAiRepository(context: Context): AiRepository {
       return object: AiRepository {
           override suspend fun checkHarm(inputList: List<String>): PredictionResponse {
             return appContext.get()!!.restClient().aiApi().checkHarm(CheckHarmRequest(Data(inputList)))
           }
         }
     }

     fun provideAiRepositoryTest(context: Context): AiRepository {
       return object: AiRepository {
           override suspend fun checkHarm(inputList: List<String>): PredictionResponse {
               return Json.decodeFromString(testPrediction)
           }
       }
     }
   }
 }
