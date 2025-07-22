package cloud.karpov.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cloud.karpov.auth.repository.AuthRepository
import cloud.karpov.mvi.BaseViewModel
import cloud.karpov.mvi.MviUseCase
import cloud.karpov.auth.usecase.InitialUseCase
import cloud.karpov.auth.usecase.LoginAction
import cloud.karpov.auth.usecase.LoginUseCase
import cloud.karpov.auth.usecase.LoginViewState

class AuthViewModel constructor(private val authRepository: AuthRepository) :
    BaseViewModel<MviUseCase<LoginAction, LoginViewState>, LoginViewState, LoginAction>() {

    init {
        start()
    }

    fun login(name: String, pass: String) {
        sendAction(LoginAction.Login(name, pass))
    }

    override fun bindActions() {
        bindAction(LoginAction.InitLoginAction::class, InitialUseCase())
        bindAction(LoginAction.Login::class, LoginUseCase(repository = authRepository))
    }

    override fun reduceViewState(
        fullViewState: LoginViewState,
        partialViewState: LoginViewState
    ): LoginViewState {
        return partialViewState
    }

    override fun getInitialViewState(): LoginViewState {
        return LoginViewState.Loading
    }

    override fun getInititalAction(): LoginAction {
        return LoginAction.InitLoginAction()
    }
}

class AuthViewModelFactory(private val repo: AuthRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
