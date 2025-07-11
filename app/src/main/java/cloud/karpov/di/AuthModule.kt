package cloud.karpov.di

import android.content.Context
import cloud.karpov.domain.repository.AuthRepository
import cloud.karpov.data.User
import cloud.karpov.data.Profile
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.Flow

class AuthModule {
 companion object {
   fun provideAuthRepository(context: Context): AuthRepository {
     return object : AuthRepository {
       override suspend fun login(login: String, pass: String): Profile {
         return Profile(User("name", "pass"))
       }
     }
   }
 }

}
