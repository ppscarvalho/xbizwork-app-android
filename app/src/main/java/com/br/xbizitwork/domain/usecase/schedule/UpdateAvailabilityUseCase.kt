package com.br.xbizitwork.domain.usecase.schedule

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.schedule.Availability
import com.br.xbizitwork.domain.model.schedule.Schedule
import com.br.xbizitwork.domain.repository.ScheduleRepository
import javax.inject.Inject

/**
 * Use Case para atualizar a disponibilidade de uma agenda
 */
class UpdateAvailabilityUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : FlowUseCase<UpdateAvailabilityUseCase.Parameters, Schedule>() {
    
    data class Parameters(
        val scheduleId: String,
        val availability: Availability
    )
    
    override suspend fun executeTask(parameters: Parameters): UiState<Schedule> {
        if (parameters.scheduleId.isBlank()) {
            return UiState.Error(IllegalArgumentException("ID da agenda inválido"))
        }
        
        if (parameters.availability.workingHours.isEmpty()) {
            return UiState.Error(IllegalArgumentException("É necessário definir pelo menos um dia de trabalho"))
        }
        
        // Valida horários de trabalho
        val invalidHours = parameters.availability.workingHours
            .filter { it.isActive && !it.isValid() }
        
        if (invalidHours.isNotEmpty()) {
            return UiState.Error(IllegalArgumentException("Horários de trabalho inválidos"))
        }
        
        return when (val result = scheduleRepository.updateAvailability(
            parameters.scheduleId,
            parameters.availability
        )) {
            is DefaultResult.Success -> UiState.Success(result.data)
            is DefaultResult.Error -> UiState.Error(Exception(result.message))
        }
    }
}
