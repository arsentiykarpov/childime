package cloud.karpov

import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cloud.karpov.data.GeneralError
import javax.inject.Inject
import cloud.karpov.domain.repository.AuthRepository
import cloud.karpov.data.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import cloud.karpov.mvi.*
import cloud.karpov.usecase.LoginUseCase
import cloud.karpov.usecase.LoginViewState
import cloud.karpov.usecase.LoginAction

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : MviViewModel<LoginUseCase, LoginViewState, LoginAction>, ViewModel() {
  private val _profile = MutableStateFlow<LoginViewState>(LoginViewState.Loading)
  val profile = _profile.asStateFlow()

  private val actions = MutableStateFlow<LoginAction>(LoginAction.InitLoginAction())

  init {
    viewModelScope.launch {
      actions.asStateFlow().flatMapMerge { value ->

        flow {
          emit(value)
        }
      }
   }
  }

  fun login(name: String, pass: String) {
    viewModelScope.launch {
      try {
        val result = withContext(Dispatchers.IO) {
          authRepository.login(name, pass)
        }
        _profile.emit(LoginViewState.OK(result))
      } catch (exception: Throwable) {
        _profile.emit(LoginViewState.Error(GeneralError(exception)))
      }
    }
  }

  fun onAction(action: LoginAction) {
    viewModelScope.launch {
      actions.emit(action);
    }
  }

  override fun bindActions() {

  }


  override fun reduceViewState(partialViewState: LoginViewState): LoginViewState {
    return partialViewState
  }

  override fun  sendAction(action: LoginAction) {
    
  }
}
