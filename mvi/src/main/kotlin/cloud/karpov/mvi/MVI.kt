package cloud.karpov.mvi

import kotlinx.coroutines.flow.*

interface MviViewModel <USECASE, VIEWSTATE: MviViewState, ACTION: MviAction> {
  fun bindActions()
  fun sendAction(action: ACTION)
  fun reduceViewState(fullViewState: VIEWSTATE, partialViewState: VIEWSTATE): VIEWSTATE
}

interface MviUseCase<ACTION: MviAction, VIEWSTATE: MviViewState> {
  operator fun invoke(action: ACTION): Flow<VIEWSTATE>
}
interface MviAction{}
interface MviViewState{}
