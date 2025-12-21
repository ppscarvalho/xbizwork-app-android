package com.br.xbizitwork.domain.usecase.schedule

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.repository.ScheduleRepository
import javax.inject.Inject

/**
 * Use Case para deletar uma agenda
 */
class DeleteScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : FlowUseCase<DeleteScheduleUseCase.Parameters, Unit>() {
    
    data class Parameters(
        val scheduleId: String
    )
    
    override suspend fun executeTask(parameters: Parameters): UiState<Unit> {
        if (parameters.scheduleId.isBlank()) {
            return UiState.Error(IllegalArgumentException("ID da agenda inválido"))
        }
        
        return when (val result = scheduleRepository.deleteSchedule(parameters.scheduleId)) {
            is DefaultResult.Success -> UiState.Success(Unit)
            is DefaultResult.Error -> UiState.Error(Exception(result.message))
        }
    }
}
