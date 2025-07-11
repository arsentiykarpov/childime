package cloud.karpov.mvi

import kotlinx.coroutines.flow.combine
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import kotlin.reflect.KClass

abstract class BaseViewModel<USECASE : MviUseCase<ACTION, VIEWSTATE>, VIEWSTATE : MviViewState, ACTION : MviAction> :
    ViewModel(), MviViewModel<USECASE, VIEWSTATE, ACTION> {
    private val actionToUsecaseMapping: MutableMap<KClass<out ACTION>, USECASE> = mutableMapOf()
    private lateinit var actions: MutableStateFlow<ACTION>
    private val _partialViewStates = MutableSharedFlow<VIEWSTATE>()
    protected val partialViewStates: Flow<VIEWSTATE> = _partialViewStates
    private val lazyInit by lazy {
        viewModelScope.launch {
            withContext(
                Dispatchers.IO,
                {
                    actions = MutableStateFlow<ACTION>(getInititalAction())
                    bindActions()
                    actions.flatMapLatest { action ->
                        val usecase =
                            requireNotNull(actionToUsecaseMapping[action::class]) { "Usecase must not be null" }
                        usecase(action)
                    }.collect { vs -> _partialViewStates.emit(vs as VIEWSTATE) }
                }
            )
        }
    }

    fun start() {
        lazyInit // triggers once, only on first call
    }
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

    abstract fun getInititalAction(): ACTION

    fun bindAction(action: KClass<out ACTION>, usecase: USECASE) {
        actionToUsecaseMapping[action] = usecase
    }

    override fun sendAction(action: ACTION) {
        viewModelScope.launch {
            actions.emit(action)
        }
    }
}

