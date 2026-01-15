package com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.domain.usecase.professional.SearchProfessionalsBySkillUseCase
import com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.events.SearchProfessionalBySkillEvent
import com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.state.SearchProfessionalBySkillUIState
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
 * ViewModel for professional search by skill
 * Following the same pattern as SkillsViewModel
 */
@HiltViewModel
class SearchProfessionalBySkillViewModel @Inject constructor(
    private val searchProfessionalsBySkillUseCase: SearchProfessionalsBySkillUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<SearchProfessionalBySkillUIState> = 
        MutableStateFlow(SearchProfessionalBySkillUIState())
    val uiState: StateFlow<SearchProfessionalBySkillUIState> = _uiState.asStateFlow()

    private val _appSideEffectChannel = Channel<AppSideEffect>()
    val sideEffectChannel = _appSideEffectChannel.receiveAsFlow()

    fun onEvent(event: SearchProfessionalBySkillEvent) {
        when (event) {
            is SearchProfessionalBySkillEvent.OnSearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = event.query) }
            }
            SearchProfessionalBySkillEvent.OnSearchClicked -> {
                performSearch()
            }
            SearchProfessionalBySkillEvent.OnClearSearch -> {
                _uiState.update { 
                    SearchProfessionalBySkillUIState() 
                }
            }
        }
    }

    private fun performSearch() {
        val query = _uiState.value.searchQuery.trim()
        
        if (query.isEmpty()) {
            viewModelScope.launch {
                _appSideEffectChannel.send(
                    AppSideEffect.ShowToast("Digite uma habilidade para buscar")
                )
            }
            return
        }

        viewModelScope.launch {
            searchProfessionalsBySkillUseCase.invoke(
                parameters = SearchProfessionalsBySkillUseCase.Parameters(skill = query)
            ).collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                },
                onSuccess = { professionals ->
                    _uiState.update {
                        it.copy(
                            professionals = professionals,
                            isLoading = false,
                            hasSearched = true,
                            errorMessage = null
                        )
                    }
                    
                    // Show toast with result count
                    val message = if (professionals.isEmpty()) {
                        "Nenhum profissional encontrado"
                    } else {
                        "${professionals.size} profissional(is) encontrado(s)"
                    }
                    _appSideEffectChannel.send(AppSideEffect.ShowToast(message))
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            hasSearched = true,
                            errorMessage = error.message ?: "Erro ao buscar profissionais"
                        )
                    }
                    _appSideEffectChannel.send(
                        AppSideEffect.ShowToast(
                            error.message ?: "Erro ao buscar profissionais"
                        )
                    )
                }
            )
        }
    }
}
