package cloud.karpov.ai.di

import android.content.Context
import cloud.karpov.ai.data.CheckHarmRequest
import cloud.karpov.ai.data.Data
import cloud.karpov.ai.data.Prediction
import cloud.karpov.ai.repository.AiRepository
import cloud.karpov.appContext

class AiModule {
 companion object {
   fun provideAiRepository(context: Context): AiRepository {
       return object: AiRepository {
           override suspend fun checkHarm(inputList: List<String>): Prediction {
             return appContext.get()!!.restClient().aiApi().checkHarm(CheckHarmRequest(Data(inputList)))
           }
         }
     }
   }
 }
