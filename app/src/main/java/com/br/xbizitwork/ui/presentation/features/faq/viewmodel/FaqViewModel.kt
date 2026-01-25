package com.br.xbizitwork.ui.presentation.features.faq.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.usecase.faq.GetFaqSectionsUseCase
import com.br.xbizitwork.ui.presentation.features.faq.events.FaqEvent
import com.br.xbizitwork.ui.presentation.features.faq.state.FaqQuestionUiState
import com.br.xbizitwork.ui.presentation.features.faq.state.FaqSectionUiState
import com.br.xbizitwork.ui.presentation.features.faq.state.FaqUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para a tela de FAQ
 * Segue o padrão do SkillsViewModel
 */
@HiltViewModel
class FaqViewModel @Inject constructor(
    private val getFaqSectionsUseCase: GetFaqSectionsUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<FaqUiState> = MutableStateFlow(FaqUiState())
    val uiState: StateFlow<FaqUiState> = _uiState.asStateFlow()

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
            getFaqSectionsUseCase.invoke().collectUiState(
                onLoading = {
                    logInfo("FAQ_VM", "⏳ Loading FAQ sections...")
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = { sections ->
                    logInfo("FAQ_VM", "✅ FAQ sections loaded: ${sections.size} sections")
                    
                    // Converte modelos de domínio para UiState
                    val sectionsUiState = sections.map { section ->
                        FaqSectionUiState(
                            id = section.id,
                            title = section.title,
                            description = section.description,
                            order = section.order,
                            questions = section.questions.map { question ->
                                FaqQuestionUiState(
                                    id = question.id,
                                    question = question.question,
                                    answer = question.answer,
                                    order = question.order,
                                    isExpanded = false
                                )
                            },
                            isExpanded = false
                        )
                    }
                    
                    _uiState.update {
                        it.copy(
                            sections = sectionsUiState,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                },
                onFailure = { error ->
                    logInfo("FAQ_VM", "❌ Error loading FAQ: ${error.message}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Erro ao carregar FAQ"
                        )
                    }
                }
            )
        }
    }

    /**
     * Alterna o estado de expansão de uma seção
     */
    private fun toggleSection(sectionId: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                sections = currentState.sections.map { section ->
                    if (section.id == sectionId) {
                        section.copy(isExpanded = !section.isExpanded)
                    } else {
                        section
                    }
                }
            )
        }
    }

    /**
     * Alterna o estado de expansão de uma questão dentro de uma seção
     */
    private fun toggleQuestion(sectionId: Int, questionId: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                sections = currentState.sections.map { section ->
                    if (section.id == sectionId) {
                        section.copy(
                            questions = section.questions.map { question ->
                                if (question.id == questionId) {
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
}
