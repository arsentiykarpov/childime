package cloud.karpov.auth.di

import android.content.Context
import cloud.karpov.auth.data.Profile
import cloud.karpov.auth.data.User
import cloud.karpov.auth.repository.AuthRepository

class AuthModule {
 companion object {
   fun provideAuthRepository(context: Context): AuthRepository {
       return object: AuthRepository {
           override fun login(
               login: String,
               pass: String
           ): Profile {
               return Profile(User(login, "test token"))
           }
       }
     }
   }
 }