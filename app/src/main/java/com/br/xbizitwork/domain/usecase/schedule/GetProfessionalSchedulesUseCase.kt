package com.br.xbizitwork.domain.usecase.schedule

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.schedule.Schedule
import com.br.xbizitwork.domain.repository.ScheduleRepository
import javax.inject.Inject

/**
 * Use Case para obter todas as agendas de um profissional
 */
class GetProfessionalSchedulesUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : FlowUseCase<GetProfessionalSchedulesUseCase.Parameters, List<Schedule>>() {
    
    data class Parameters(
        val professionalId: String,
        val onlyActive: Boolean = false
    )
    
    override suspend fun executeTask(parameters: Parameters): UiState<List<Schedule>> {
        if (parameters.professionalId.isBlank()) {
            return UiState.Error(IllegalArgumentException("ID do profissional inválido"))
        }
        
        val result = if (parameters.onlyActive) {
            scheduleRepository.getActiveSchedules(parameters.professionalId)
        } else {
            scheduleRepository.getProfessionalSchedules(parameters.professionalId)
        }
        
        return when (result) {
            is DefaultResult.Success -> UiState.Success(result.data)
            is DefaultResult.Error -> UiState.Error(Exception(result.message))
        }
    }
}
