package com.br.xbizitwork.ui.presentation.features.schedule.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.domain.usecase.schedule.DeleteScheduleUseCase
import com.br.xbizitwork.domain.usecase.schedule.GetProfessionalSchedulesUseCase
import com.br.xbizitwork.domain.usecase.session.GetAuthSessionUseCase
import com.br.xbizitwork.ui.presentation.features.schedule.list.events.ViewSchedulesEvent
import com.br.xbizitwork.ui.presentation.features.schedule.list.state.ViewSchedulesUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewSchedulesViewModel @Inject constructor(
    private val getProfessionalSchedulesUseCase: GetProfessionalSchedulesUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase,
    private val getAuthSessionUseCase: GetAuthSessionUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(ViewSchedulesUIState())
    val uiState: StateFlow<ViewSchedulesUIState> = _uiState.asStateFlow()
    
    private val _sideEffectChannel = Channel<SideEffect>()
    val sideEffectChannel = _sideEffectChannel.receiveAsFlow()
    
    init {
        loadSchedules()
    }
    
    fun onEvent(event: ViewSchedulesEvent) {
        when (event) {
            ViewSchedulesEvent.OnRefresh -> loadSchedules()
            is ViewSchedulesEvent.OnScheduleClick -> navigateToScheduleDetails(event.scheduleId)
            is ViewSchedulesEvent.OnDeleteSchedule -> deleteSchedule(event.scheduleId)
            ViewSchedulesEvent.OnCreateScheduleClick -> navigateToCreateSchedule()
        }
    }
    
    private fun loadSchedules() {
        viewModelScope.launch {
            // Buscar userId da sessão
            val session = getAuthSessionUseCase.invoke().first()
            val professionalId = session.id.toString()

            getProfessionalSchedulesUseCase(
                GetProfessionalSchedulesUseCase.Parameters(professionalId, onlyActive = false)
            ).collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true, errorMessage = null) }
                },
                onSuccess = { schedules ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            schedules = schedules,
                            isEmpty = schedules.isEmpty()
                        )
                    }
                },
                onFailure = { throwable ->
                    val errorMessage = throwable.message ?: "Erro ao carregar agendas"

                    // Verificar se é erro de autenticação (token inválido)
                    if (errorMessage.contains("401") ||
                        errorMessage.contains("Token inválido") ||
                        errorMessage.contains("Unauthorized")) {
                        viewModelScope.launch {
                            _sideEffectChannel.send(SideEffect.NavigateToLogin)
                        }
                    }

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = errorMessage
                        )
                    }
                }
            )
        }
    }
    
    private fun deleteSchedule(scheduleId: String) {
        viewModelScope.launch {
            deleteScheduleUseCase(DeleteScheduleUseCase.Parameters(scheduleId))
                .collectUiState(
                    onLoading = {},
                    onSuccess = {
                        _sideEffectChannel.send(SideEffect.ShowToast("Agenda excluída"))
                        loadSchedules() // Recarrega lista
                    },
                    onFailure = { throwable ->
                        _sideEffectChannel.send(
                            SideEffect.ShowToast(throwable.message ?: "Erro ao excluir")
                        )
                    }
                )
        }
    }
    
    private fun navigateToScheduleDetails(scheduleId: String) {
        viewModelScope.launch {
            // TODO: Implementar navegação para detalhes
            _sideEffectChannel.send(SideEffect.ShowToast("Detalhes: $scheduleId"))
        }
    }
    
    private fun navigateToCreateSchedule() {
        viewModelScope.launch {
            // TODO: Implementar navegação para criar agenda
            _sideEffectChannel.send(SideEffect.ShowToast("Navegar para criar agenda"))
        }
    }
}
