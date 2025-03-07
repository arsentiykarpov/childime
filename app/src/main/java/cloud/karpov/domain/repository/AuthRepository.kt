package cloud.karpov.domain.repository

import kotlinx.coroutines.flow.Flow
import cloud.karpov.data.Profile

interface AuthRepository {
  suspend fun login(login: String, pass: String): Profile
}
