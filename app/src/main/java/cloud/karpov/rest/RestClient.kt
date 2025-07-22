package cloud.karpov.rest

import cloud.karpov.ai.api.AiApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

private const val BASE_URL = "http://localhost:8000/"

class RestClient {
    lateinit var retrofitClient: Retrofit
    private val contentType = "application/json; charset=UTF8".toMediaType()

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Options: NONE, BASIC, HEADERS, BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    private val json = Json {
        ignoreUnknownKeys = true // prevents errors on extra fields
        isLenient = true
    }

    constructor() {
        this.retrofitClient = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    fun client(): Retrofit {
        return retrofitClient
    }

    fun aiApi(): AiApi {
        return client().create(AiApi::class.java)
    }

}
