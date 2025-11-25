package cloud.karpov.ai.di

import android.app.Application
import android.content.Context
import androidx.annotation.RawRes
import cloud.karpov.R
import cloud.karpov.ai.data.CheckHarmRequest
import cloud.karpov.ai.data.Data
import cloud.karpov.ai.data.Messages
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
            return object : AiRepository {
                var lastPrediction: PredictionResponse? = null
                override suspend fun checkHarm(inputList: List<String>): PredictionResponse {
                    lastPrediction =  appContext.get()!!.restClient().aiApi()
                        .checkHarm(CheckHarmRequest(Data(inputList)))
                    return lastPrediction!!
                }

                override fun lastPrediction(): PredictionResponse {
                    return lastPrediction!!
                }

                override fun surroundMessages(count: Int): Messages {
                    TODO("Not yet implemented")
                }
            }
        }

        fun testRepo(app: Application): AiRepository {
              return object : AiRepository {
                override suspend fun checkHarm(inputList: List<String>): PredictionResponse {
                    return Json.decodeFromString(testPrediction)
                }

                override fun lastPrediction(): PredictionResponse {
                    return Json.decodeFromString(testPrediction)
                }

                override fun surroundMessages(count: Int): Messages {
                    return Json.decodeFromString(app.readRawJson(R.raw.testsurrounding))
                }
            }
        }

        lateinit var testRepo: AiRepository

        fun provideAiRepositoryTest(context: Context): AiRepository {
            testRepo = testRepo(context as Application)
            return testRepo
        }
    }
}

fun Application.readRawJson(@RawRes id: Int): String =
    resources.openRawResource(id).bufferedReader().use { it.readText() }

