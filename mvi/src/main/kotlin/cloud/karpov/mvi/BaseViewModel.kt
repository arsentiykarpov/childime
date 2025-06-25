package cloud.karpov.mvi

import kotlinx.coroutines.flow.combine
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseViewModel<USECASE, VIEWSTATE: MviViewState, ACTION: MviAction>: ViewModel(), MviViewModel<USECASE, VIEWSTATE, ACTION>{

    private val _partialViewStates = MutableSharedFlow<VIEWSTATE>()
    protected val partialViewStates: Flow<VIEWSTATE> = _partialViewStates

    val viewState: StateFlow<VIEWSTATE> =
        partialViewStates
            .scan(getInitialViewState()) { currentState, partial ->
                reduceViewState(currentState, partial)
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                initialValue = getInitialViewState()
            )

  abstract fun getInitialViewState(): VIEWSTATE
    
}

