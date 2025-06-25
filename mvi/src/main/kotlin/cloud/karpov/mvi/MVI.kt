package cloud.karpov.mvi

import kotlinx.coroutines.flow.*

interface MviViewModel <USECASE, VIEWSTATE: MviViewState, ACTION: MviAction> {
  fun bindActions()
  fun sendAction(action: ACTION)
  fun reduceViewState(partialViewState: VIEWSTATE): VIEWSTATE
}

interface MviUseCase<ACTION: MviAction, VIEWSATE: MviViewState> {
  suspend fun onAction(action: ACTION): Flow<VIEWSATE>
}
interface MviAction{}
interface MviViewState{}
