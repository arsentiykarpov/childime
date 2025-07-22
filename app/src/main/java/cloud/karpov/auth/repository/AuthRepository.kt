package cloud.karpov.auth.repository

import cloud.karpov.auth.data.Profile

interface AuthRepository {
   fun login(login: String, pass: String): Profile
}