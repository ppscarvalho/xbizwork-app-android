package com.br.xbizitwork.ui.presentation.features.schedule.agenda.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.domain.usecase.schedule.GetProfessionalSchedulesUseCase
import com.br.xbizitwork.domain.usecase.session.GetAuthSessionUseCase
import com.br.xbizitwork.ui.presentation.features.schedule.agenda.state.ProfessionalAgendaUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfessionalAgendaViewModel @Inject constructor(
    private val getProfessionalSchedulesUseCase: GetProfessionalSchedulesUseCase,
    private val getAuthSessionUseCase: GetAuthSessionUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(
        ProfessionalAgendaUIState(
            selectedDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
                .format(java.util.Date())
        )
    )
    val uiState: StateFlow<ProfessionalAgendaUIState> = _uiState.asStateFlow()
    
    init {
        loadSchedules()
    }
    
    private fun loadSchedules() {
        viewModelScope.launch {
            // Buscar userId da sessão
            val session = getAuthSessionUseCase.invoke().first()
            val professionalId = session.id.toString()

            getProfessionalSchedulesUseCase(
                GetProfessionalSchedulesUseCase.Parameters(professionalId, onlyActive = true)
            ).collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = { schedules ->
                    _uiState.update {
                        it.copy(isLoading = false, schedules = schedules)
                    }
                },
                onFailure = { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Erro"
                        )
                    }
                }
            )
        }
    }
}
