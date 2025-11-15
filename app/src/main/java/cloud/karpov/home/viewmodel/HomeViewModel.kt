package cloud.karpov.home.viewmodel


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cloud.karpov.ai.repository.AiRepository
import cloud.karpov.home.usecase.HomeAction
import cloud.karpov.home.usecase.HomeViewState
import cloud.karpov.home.usecase.InitialUseCase
import cloud.karpov.home.usecase.PredictUseCase
import cloud.karpov.home.usecase.UserInputUseCase
import cloud.karpov.mvi.BaseViewModel
import cloud.karpov.mvi.MviUseCase
import dev.patrickgold.florisboard.editorInstance
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

class HomeViewModel constructor(private val aiRepository: AiRepository, val context: Context) :
    BaseViewModel<MviUseCase<HomeAction, HomeViewState>, HomeViewState, HomeAction>() {

    val contentObserver = context.editorInstance().value.activeContentFlow

    init {
        start()
    }

    fun observerUserInput() {
        viewModelScope.launch {
            contentObserver.debounce(2000).collect {
                predict(it.text)
            }
        }
    }

    fun predict(input: String) {
        sendAction(
            HomeAction.Predict(
                mutableListOf(
                    "Пока родителей нет дома",
                    "Пожалуйста, не надо",
                    input
                )
            )
        )
    }

    fun sendTestData() {
      predict("test dummy")
    }

    override fun bindActions() {
        bindAction(HomeAction.InitHomeAction::class, InitialUseCase())
        bindAction(HomeAction.Predict::class, PredictUseCase(aiRepository))
        bindAction(HomeAction.UserInputAction::class, UserInputUseCase())
        observerUserInput()
        sendTestData()
    }

    override fun getInitialViewState(): HomeViewState {
        return HomeViewState.Loading
    }

    override fun getInititalAction(): HomeAction {
        return HomeAction.InitHomeAction()
    }

    override fun reduceViewState(
        fullViewState: HomeViewState,
        partialViewState: HomeViewState
    ): HomeViewState {
        return partialViewState
    }
}

class HomeViewModelFactory(private val repo: AiRepository, val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repo, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
