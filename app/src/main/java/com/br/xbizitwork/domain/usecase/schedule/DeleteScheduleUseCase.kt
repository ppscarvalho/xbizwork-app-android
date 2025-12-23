package com.br.xbizitwork.domain.usecase.schedule

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para deletar uma agenda
 */
interface DeleteScheduleUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<Unit>>

    data class Parameters(
        val scheduleId: String
    )
}

/**
 * Implementação do DeleteScheduleUseCase
 */
class DeleteScheduleUseCaseImpl @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : DeleteScheduleUseCase, FlowUseCase<DeleteScheduleUseCase.Parameters, Unit>() {

    override suspend fun executeTask(parameters: DeleteScheduleUseCase.Parameters): UiState<Unit> {
        return try {
            if (parameters.scheduleId.isBlank()) {
                return UiState.Error(IllegalArgumentException("ID da agenda inválido"))
            }

            when (val result = scheduleRepository.deleteSchedule(parameters.scheduleId)) {
                is DefaultResult.Success -> UiState.Success(Unit)
                is DefaultResult.Error -> UiState.Error(Exception(result.message))
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
