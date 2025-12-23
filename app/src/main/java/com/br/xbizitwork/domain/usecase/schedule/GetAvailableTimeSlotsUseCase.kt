package com.br.xbizitwork.domain.usecase.schedule

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.schedule.DayOfWeek
import com.br.xbizitwork.domain.model.schedule.TimeSlot
import com.br.xbizitwork.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

/**
 * Caso de uso para obter slots de horário disponíveis
 */
interface GetAvailableTimeSlotsUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<List<TimeSlot>>>

    data class Parameters(
        val scheduleId: String,
        val date: LocalDate,
        val dayOfWeek: DayOfWeek
    )
}

/**
 * Implementação do GetAvailableTimeSlotsUseCase
 */
class GetAvailableTimeSlotsUseCaseImpl @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : GetAvailableTimeSlotsUseCase, FlowUseCase<GetAvailableTimeSlotsUseCase.Parameters, List<TimeSlot>>() {

    override suspend fun executeTask(parameters: GetAvailableTimeSlotsUseCase.Parameters): UiState<List<TimeSlot>> {
        return try {
            if (parameters.scheduleId.isBlank()) {
                return UiState.Error(IllegalArgumentException("ID da agenda inválido"))
            }

            when (val result = scheduleRepository.getAvailableTimeSlots(
                parameters.scheduleId,
                parameters.date,
                parameters.dayOfWeek
            )) {
                is DefaultResult.Success -> UiState.Success(result.data)
                is DefaultResult.Error -> UiState.Error(Exception(result.message))
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
