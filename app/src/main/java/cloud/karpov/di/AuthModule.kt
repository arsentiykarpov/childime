package cloud.karpov.di

import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.Provides
import dagger.Module
import android.content.Context
import cloud.karpov.domain.repository.AuthRepository
import cloud.karpov.data.User
import cloud.karpov.data.Profile
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.Flow

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
  
  @Provides
  fun provideAuthRepository(@ApplicationContext context: Context): AuthRepository {
    return object : AuthRepository {
      override suspend fun login(login: String, pass: String): Profile {
        return Profile(User("name", "pass"))
      }
    }   
  }

}
