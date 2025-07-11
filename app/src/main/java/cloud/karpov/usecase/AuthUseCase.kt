package cloud.karpov.usecase

import cloud.karpov.mvi.MviUseCase
import cloud.karpov.mvi.MviAction
import cloud.karpov.mvi.MviViewState
import cloud.karpov.data.Profile
import cloud.karpov.data.GeneralError
import cloud.karpov.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

open class LoginUseCase(private val repository: AuthRepository) :
    MviUseCase<LoginAction, LoginViewState> {
    override fun invoke(action: LoginAction): Flow<LoginViewState> {
        var currAction: LoginAction
        when (action) {
            is LoginAction.Login -> {
                currAction = action as LoginAction.Login
                return flowOf(LoginViewState.OK(repository.login(currAction.name, currAction.pass)))
            }

            is LoginAction.InitLoginAction -> TODO()
            is LoginAction.PassChanged -> TODO()
            is LoginAction.UsernameChanged -> TODO()
        }
    }
}

class InitialUseCase : MviUseCase<LoginAction, LoginViewState>{
    override fun invoke(action: LoginAction): Flow<LoginViewState> {
        return flowOf(LoginViewState.Loading)
    }
}

sealed class LoginAction : MviAction {
    class InitLoginAction : LoginAction()
    data class UsernameChanged(var name: String) : LoginAction()
    data class PassChanged(var pass: String) : LoginAction()
    data class Login(var name: String, var pass: String) : LoginAction()
}

sealed class LoginViewState : MviViewState {
    object Loading : LoginViewState()
    data class OK(var profile: Profile) : LoginViewState()
    data class Error(var error: GeneralError) : LoginViewState()
}
