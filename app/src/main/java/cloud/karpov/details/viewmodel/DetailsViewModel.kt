package cloud.karpov.details.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cloud.karpov.ai.data.Messages
import cloud.karpov.ai.data.Prediction
import cloud.karpov.ai.repository.AiRepository
import cloud.karpov.details.usecase.DetailsAction
import cloud.karpov.details.usecase.DetailsViewState
import cloud.karpov.details.usecase.InitialUseCase
import cloud.karpov.mvi.BaseViewModel
import cloud.karpov.mvi.MviUseCase

class DetailsViewModel constructor(private val aiRepository: AiRepository, val context: Context) :
    BaseViewModel<MviUseCase<DetailsAction, DetailsViewState>, DetailsViewState, DetailsAction>() {

    init {
        start()
    }

    fun findPrediction(id: String): Prediction {
        return aiRepository.lastPrediction().prediction.filter {
            it.en.hashCode().toString().equals(id)
        }.first()
    }

    fun surroundMessages(count: Int): Messages {
        return aiRepository.surroundMessages(count)
    }

    override fun bindActions() {
        bindAction(DetailsAction.InitDetailsAction::class, InitialUseCase())
    }

    override fun getInitialViewState(): DetailsViewState {
        return DetailsViewState.OK("")
    }

    override fun getInititalAction(): DetailsAction {
        return DetailsAction.InitDetailsAction()
    }

    override fun reduceViewState(
        fullViewState: DetailsViewState,
        partialViewState: DetailsViewState
    ): DetailsViewState {
        return partialViewState
    }
}

class DetailsViewModelFactory(private val repo: AiRepository, val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            return DetailsViewModel(repo, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
