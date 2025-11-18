package cloud.karpov.details.usecase

import cloud.karpov.auth.data.GeneralError
import cloud.karpov.mvi.MviAction
import cloud.karpov.mvi.MviUseCase
import cloud.karpov.mvi.MviViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class InitialUseCase : MviUseCase<DetailsAction, DetailsViewState> {
    override fun invoke(action: DetailsAction): Flow<DetailsViewState> {
        return flowOf(DetailsViewState.Loading)
    }
}

sealed class DetailsAction : MviAction {
    class InitDetailsAction : DetailsAction()
}

sealed class DetailsViewState : MviViewState {
    object Loading : DetailsViewState()
    data class OK(var message: String) : DetailsViewState()
    data class Error(var error: GeneralError) : DetailsViewState()

    data class DebugViewState(var input: String) : DetailsViewState()
}
