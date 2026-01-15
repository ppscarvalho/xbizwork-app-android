package com.br.xbizitwork.ui.presentation.features.searchprofessional.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.usecase.skills.SearchProfessionalBySkillUseCase
import com.br.xbizitwork.ui.presentation.features.searchprofessional.events.SearchProfessionalBySkillEvent
import com.br.xbizitwork.ui.presentation.features.searchprofessional.state.SearchProfessionalBySkillUIState
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
 * ViewModel para a tela de busca de profissionais por habilidade
 * Seguindo o mesmo padrão do SkillsViewModel
 */
@HiltViewModel
class SearchProfessionalBySkillViewModel @Inject constructor(
    private val searchProfessionalBySkillUseCase: SearchProfessionalBySkillUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<SearchProfessionalBySkillUIState> = 
        MutableStateFlow(SearchProfessionalBySkillUIState())
    val uiState: StateFlow<SearchProfessionalBySkillUIState> = _uiState.asStateFlow()

    private val _appSideEffectChannel = Channel<AppSideEffect>()
    val sideEffectChannel = _appSideEffectChannel.receiveAsFlow()

    /**
     * Processa eventos da UI
     */
    fun onEvent(event: SearchProfessionalBySkillEvent) {
        when (event) {
            is SearchProfessionalBySkillEvent.OnSearchTermChanged -> updateSearchTerm(event.term)
            SearchProfessionalBySkillEvent.OnSearch -> executeSearch()
            SearchProfessionalBySkillEvent.OnClearSearch -> clearSearch()
        }
    }

    /**
     * Atualiza o termo de busca no estado
     */
    private fun updateSearchTerm(term: String) {
        _uiState.update { it.copy(searchTerm = term) }
    }

    /**
     * Executa a busca de profissionais por habilidade
     * Lógica de busca é tratada exclusivamente no backend
     */
    private fun executeSearch() {
        val searchTerm = _uiState.value.searchTerm.trim()
        
        if (searchTerm.isEmpty()) {
            logInfo("SEARCH_PROFESSIONAL_VM", "⚠️ Termo de busca vazio")
            return
        }

        viewModelScope.launch {
            logInfo("SEARCH_PROFESSIONAL_VM", "🔍 Buscando profissionais com skill: $searchTerm")

            searchProfessionalBySkillUseCase.invoke(
                parameters = SearchProfessionalBySkillUseCase.Parameters(skill = searchTerm)
            ).collectUiState(
                onLoading = {
                    logInfo("SEARCH_PROFESSIONAL_VM", "⏳ Carregando...")
                    _uiState.update { 
                        it.copy(
                            isLoading = true,
                            isEmpty = false,
                            errorMessage = null
                        )
                    }
                },
                onEmpty = {
                    logInfo("SEARCH_PROFESSIONAL_VM", "📭 Nenhum profissional encontrado")
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            isEmpty = true,
                            professionals = emptyList()
                        )
                    }
                    viewModelScope.launch {
                        _appSideEffectChannel.send(
                            AppSideEffect.ShowToast("Nenhum profissional encontrado")
                        )
                    }
                },
                onSuccess = { professionals ->
                    logInfo("SEARCH_PROFESSIONAL_VM", "✅ ${professionals.size} profissional(is) encontrado(s)")
                    professionals.forEach { prof ->
                        logInfo("SEARCH_PROFESSIONAL_VM", "  - ${prof.name} (${prof.skill.description})")
                    }
                    
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            isEmpty = false,
                            professionals = professionals,
                            errorMessage = null
                        )
                    }
                    viewModelScope.launch {
                        _appSideEffectChannel.send(
                            AppSideEffect.ShowToast("${professionals.size} profissional(is) encontrado(s)")
                        )
                    }
                },
                onFailure = { error ->
                    logInfo("SEARCH_PROFESSIONAL_VM", "❌ Erro: ${error.message}")
                    _uiState.update { 
                        it.copy(
                            isLoading = false,
                            isEmpty = false,
                            errorMessage = error.message ?: "Erro ao buscar profissionais"
                        )
                    }
                    viewModelScope.launch {
                        _appSideEffectChannel.send(
                            AppSideEffect.ShowToast(error.message ?: "Erro ao buscar profissionais")
                        )
                    }
                }
            )
        }
    }

    /**
     * Limpa a busca e reseta o estado
     */
    private fun clearSearch() {
        logInfo("SEARCH_PROFESSIONAL_VM", "🧹 Limpando busca")
        _uiState.update { 
            SearchProfessionalBySkillUIState()
        }
    }
}
