package com.br.xbizitwork.ui.presentation.features.faq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.model.faq.FaqQuestion
import com.br.xbizitwork.domain.model.faq.FaqSection
import com.br.xbizitwork.domain.usecase.faq.GetFaqSectionsUseCase
import com.br.xbizitwork.ui.presentation.features.faq.events.FaqEvent
import com.br.xbizitwork.ui.presentation.features.faq.state.FaqQuestionUiState
import com.br.xbizitwork.ui.presentation.features.faq.state.FaqSectionUiState
import com.br.xbizitwork.ui.presentation.features.faq.state.FaqUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para a tela de FAQ
 * Seguindo o mesmo padrão do SkillsViewModel
 */
@HiltViewModel
class FaqViewModel @Inject constructor(
    private val getFaqSectionsUseCase: GetFaqSectionsUseCase
) : ViewModel() {
    
    private val _uiState: MutableStateFlow<FaqUiState> = MutableStateFlow(FaqUiState())
    val uiState: StateFlow<FaqUiState> = _uiState.asStateFlow()

    private val _appSideEffectChannel = Channel<AppSideEffect>()
    val sideEffectChannel = _appSideEffectChannel.receiveAsFlow()

    init {
        loadFaqSections()
    }

    fun onEvent(event: FaqEvent) {
        when (event) {
            FaqEvent.OnLoadFaq -> loadFaqSections()
            is FaqEvent.OnToggleSection -> toggleSection(event.sectionId)
            is FaqEvent.OnToggleQuestion -> toggleQuestion(event.sectionId, event.questionId)
        }
    }

    /**
     * Carrega as seções de FAQ da API
     */
    private fun loadFaqSections() {
        viewModelScope.launch {
            logInfo("FAQ_VM", "🔄 Carregando seções de FAQ...")
            
            getFaqSectionsUseCase.invoke().collectUiState(
                onLoading = {
                    logInfo("FAQ_VM", "⏳ Loading...")
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = { sections ->
                    logInfo("FAQ_VM", "✅ ${sections.size} seções carregadas")
                    
                    // Converte domain model para UI state
                    val sectionUiStates = sections.map { section ->
                        section.toUiState()
                    }
                    
                    _uiState.update {
                        it.copy(
                            sections = sectionUiStates,
                            isLoading = false,
                            isSuccess = true
                        )
                    }
                },
                onFailure = { error ->
                    logInfo("FAQ_VM", "❌ Erro: ${error.message}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Erro ao carregar FAQ"
                        )
                    }
                    viewModelScope.launch {
                        _appSideEffectChannel.send(
                            AppSideEffect.ShowToast(error.message ?: "Erro ao carregar FAQ")
                        )
                    }
                }
            )
        }
    }

    /**
     * Expande/colapsa uma seção
     */
    private fun toggleSection(sectionId: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                sections = currentState.sections.map { section ->
                    if (section.id == sectionId) {
                        logInfo("FAQ_VM", "🔄 Toggle seção: ${section.title} -> ${!section.isExpanded}")
                        section.copy(isExpanded = !section.isExpanded)
                    } else {
                        section
                    }
                }
            )
        }
    }

    /**
     * Expande/colapsa uma pergunta dentro de uma seção
     */
    private fun toggleQuestion(sectionId: Int, questionId: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                sections = currentState.sections.map { section ->
                    if (section.id == sectionId) {
                        section.copy(
                            questions = section.questions.map { question ->
                                if (question.id == questionId) {
                                    logInfo("FAQ_VM", "🔄 Toggle pergunta: ${question.question} -> ${!question.isExpanded}")
                                    question.copy(isExpanded = !question.isExpanded)
                                } else {
                                    question
                                }
                            }
                        )
                    } else {
                        section
                    }
                }
            )
        }
    }

    /**
     * Extension function para converter domain model em UI state
     */
    private fun FaqSection.toUiState(): FaqSectionUiState {
        return FaqSectionUiState(
            id = this.id,
            title = this.title,
            description = this.description,
            order = this.order,
            questions = this.questions.map { it.toUiState() },
            isExpanded = false
        )
    }

    /**
     * Extension function para converter domain model em UI state
     */
    private fun FaqQuestion.toUiState(): FaqQuestionUiState {
        return FaqQuestionUiState(
            id = this.id,
            question = this.question,
            answer = this.answer,
            order = this.order,
            isExpanded = false
        )
    }
}
