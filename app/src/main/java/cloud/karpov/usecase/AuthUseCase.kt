package cloud.karpov.usecase

import cloud.karpov.mvi.MviUseCase
import cloud.karpov.mvi.MviAction
import cloud.karpov.mvi.MviViewState
import cloud.karpov.data.User
import cloud.karpov.data.Profile
import cloud.karpov.data.GeneralError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class LoginUseCase: MviUseCase<LoginAction, LoginViewState> {

    override suspend fun onAction(action: LoginAction): Flow<LoginViewState> {
      return flowOf(LoginViewState.Loading)
  }
    
}

sealed class LoginAction: MviAction {
  class InitLoginAction: LoginAction()
  data class UsernameChanged(var name: String) : LoginAction()
  data class PassChanged(var pass: String): LoginAction()
  data class Login(var name: String, var pass: String): LoginAction()
}

sealed class LoginViewState: MviViewState {
  object Loading: LoginViewState()
  data class OK(var profile: Profile): LoginViewState()
  data class Error(var error: GeneralError): LoginViewState()
}


