package cloud.karpov.home.usecase

import cloud.karpov.ai.data.PredictionResponse
import cloud.karpov.ai.repository.AiRepository
import cloud.karpov.auth.data.GeneralError
import cloud.karpov.mvi.MviAction
import cloud.karpov.mvi.MviUseCase
import cloud.karpov.mvi.MviViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

open class PredictUseCase(private val repository: AiRepository) :
    MviUseCase<HomeAction, HomeViewState> {
    override fun invoke(action: HomeAction): Flow<HomeViewState> {
        return when (action) {
            is HomeAction.Predict -> {
                flow {
                    try {
                        val prediciton = repository.checkHarm(action.input)
                        emit(HomeViewState.OK(prediciton))
                    } catch (ex: Throwable) {
                        emit(HomeViewState.Error(GeneralError(ex)))
                    }
                }
            }

            is HomeAction.InitHomeAction -> {
                flowOf()
            }

            else -> {flowOf()}
        }
    }
}

class UserInputUseCase : MviUseCase<HomeAction, HomeViewState> {
    override fun invoke(action: HomeAction): Flow<HomeViewState> {
        return flowOf(HomeViewState.DebugViewState((action as HomeAction.UserInputAction).input))
    }
}

class InitialUseCase : MviUseCase<HomeAction, HomeViewState> {
    override fun invoke(action: HomeAction): Flow<HomeViewState> {
        return flowOf(HomeViewState.Loading)
    }
}

sealed class HomeAction : MviAction {
    class InitHomeAction : HomeAction()
    data class Predict(var input: List<String>) : HomeAction()
    data class UserInputAction(val input: String): HomeAction()
}

sealed class HomeViewState : MviViewState {
    object Loading : HomeViewState()
    data class OK(var predict: PredictionResponse) : HomeViewState()
    data class Error(var error: GeneralError) : HomeViewState()

    data class DebugViewState(var  input: String): HomeViewState()
}
