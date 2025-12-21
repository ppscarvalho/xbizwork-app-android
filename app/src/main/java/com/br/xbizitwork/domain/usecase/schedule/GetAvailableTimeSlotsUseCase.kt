package com.br.xbizitwork.domain.usecase.schedule

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.schedule.DayOfWeek
import com.br.xbizitwork.domain.model.schedule.TimeSlot
import com.br.xbizitwork.domain.repository.ScheduleRepository
import java.time.LocalDate
import javax.inject.Inject

/**
 * Use Case para obter slots de horário disponíveis
 */
class GetAvailableTimeSlotsUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : FlowUseCase<GetAvailableTimeSlotsUseCase.Parameters, List<TimeSlot>>() {
    
    data class Parameters(
        val scheduleId: String,
        val date: LocalDate,
        val dayOfWeek: DayOfWeek
    )
    
    override suspend fun executeTask(parameters: Parameters): UiState<List<TimeSlot>> {
        if (parameters.scheduleId.isBlank()) {
            return UiState.Error(IllegalArgumentException("ID da agenda inválido"))
        }
        
        return when (val result = scheduleRepository.getAvailableTimeSlots(
            parameters.scheduleId,
            parameters.date,
            parameters.dayOfWeek
        )) {
            is DefaultResult.Success -> UiState.Success(result.data)
            is DefaultResult.Error -> UiState.Error(Exception(result.message))
        }
    }
}
