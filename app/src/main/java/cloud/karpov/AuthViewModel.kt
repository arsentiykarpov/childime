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

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository) : ViewModel() {
  private val _profile = MutableStateFlow<LoginUiState?>(LoginUiState.Loading)
  val profile = _profile.asStateFlow()

  private val actions = MutableStateFlow<AuthAction?>(null)

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
        _profile.emit(LoginUiState.Ok(result))
      } catch (exception: Throwable) {
        _profile.emit(LoginUiState.Error(GeneralError(exception)))
      }
    }
  }

  fun onAction(action: AuthAction) {
    viewModelScope.launch {
      actions.emit(action);
    }
  }
}

sealed class LoginUiState {
  object Loading : LoginUiState()
  data class Ok(val profile : Profile) : LoginUiState()
  data class Error(val error: GeneralError) : LoginUiState()
}

sealed class AuthAction {
  data class UsernameChanged(var name: String) : AuthAction()
  data class PassChanged(var pass: String): AuthAction()
  data class Login(var name: String, var pass: String): AuthAction()
}
