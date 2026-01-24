package com.br.xbizitwork.ui.presentation.features.searchprofessionals.viewmodel

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.usecase.professional.SearchProfessionalsBySkillUseCase
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.events.SearchProfessionalBySkillEvent
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.state.SearchProfessionalsUiState
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for professional search by skill
 * Following the same pattern as SearchScheduleViewModel
 */
@HiltViewModel
class SearchProfessionalsViewModel @Inject constructor(
    private val searchProfessionalsBySkillUseCase: SearchProfessionalsBySkillUseCase
) : ViewModel() {


    private val _uiState: MutableStateFlow<SearchProfessionalsUiState> =
        MutableStateFlow(SearchProfessionalsUiState())
    val uiState: StateFlow<SearchProfessionalsUiState> = _uiState.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val searchState = snapshotFlow { _uiState.value.queryTextState.text }
        .debounce(500)
        .mapLatest {
            if (it.isBlank()) {
                SearchState.Empty
            } else {
                SearchState.Query(it.toString())
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SearchState.Empty
        )

    fun onEvent(event: SearchProfessionalBySkillEvent) {
        when (event) {
            is SearchProfessionalBySkillEvent.OnRefresh -> {
                observeSearch()
            }
            is SearchProfessionalBySkillEvent.OnProfessionalSelected -> {
                logProfessionalSelected(event.professional)
            }
        }
    }

    private fun logProfessionalSelected(professional: ProfessionalSearchBySkill) {
        logInfo("SEARCH_PROFESSIONALS_VM", "===========================================")
        logInfo("SEARCH_PROFESSIONALS_VM", "👤 PROFISSIONAL SELECIONADO NO MAPA")
        logInfo("SEARCH_PROFESSIONALS_VM", "===========================================")
        logInfo("SEARCH_PROFESSIONALS_VM", "📝 Nome: ${professional.name}")
        logInfo("SEARCH_PROFESSIONALS_VM", "🆔 ID: ${professional.id}")
        logInfo("SEARCH_PROFESSIONALS_VM", "📱 Telefone: ${professional.mobilePhone}")
        logInfo("SEARCH_PROFESSIONALS_VM", "📍 Cidade: ${professional.city} - ${professional.state}")
        logInfo("SEARCH_PROFESSIONALS_VM", "💼 Habilidade: ${professional.skill.description}")
        logInfo("SEARCH_PROFESSIONALS_VM", "🗺️ Localização: Lat ${professional.latitude}, Lng ${professional.longitude}")
        logInfo("SEARCH_PROFESSIONALS_VM", "===========================================")
    }

    private fun observeSearch() {
        viewModelScope.launch {
            searchState.collectLatest { searchState ->
                when (searchState) {
                    is SearchState.Empty -> {
                        _uiState.update {
                            it.copy(
                                searchIsEmpty = true,
                                professionals = emptyList(),
                                isEmpty = false,
                                isLoading = false,
                                errorMessage = null
                            )
                        }
                    }
                    is SearchState.Query -> {
                        fetchProfessionals(searchState.query)
                    }
                }
            }
        }
    }

    private fun fetchProfessionals(query: String) {
        viewModelScope.launch {
            searchProfessionalsBySkillUseCase.invoke(
                SearchProfessionalsBySkillUseCase.Parameters(skill = query)
            ).collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true, searchIsEmpty = false) }
                },
                onEmpty = {
                    _uiState.update { it.copy(isLoading = false, isEmpty = true, searchIsEmpty = false) }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isEmpty = false,
                            searchIsEmpty = false,
                            errorMessage = error.message
                        )
                    }
                },
                onSuccess = { result ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isEmpty = false,
                            searchIsEmpty = false,
                            professionals = result,
                            errorMessage = null
                        )
                    }
                }
            )
        }
    }
}
