package cloud.karpov

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cloud.karpov.data.GeneralError
import cloud.karpov.domain.repository.AuthRepository
import cloud.karpov.data.Profile
import cloud.karpov.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import cloud.karpov.mvi.*
import cloud.karpov.usecase.InitialUseCase
import cloud.karpov.usecase.LoginUseCase
import cloud.karpov.usecase.LoginViewState
import cloud.karpov.usecase.LoginAction
import kotlinx.coroutines.flow.map

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
