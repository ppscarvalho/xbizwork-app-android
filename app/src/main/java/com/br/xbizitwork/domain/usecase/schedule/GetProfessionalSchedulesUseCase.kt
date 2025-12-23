package com.br.xbizitwork.domain.usecase.schedule

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.schedule.Schedule
import com.br.xbizitwork.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para obter todas as agendas de um profissional
 */
interface GetProfessionalSchedulesUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<List<Schedule>>>

    data class Parameters(
        val professionalId: String,
        val onlyActive: Boolean = false
    )
}

/**
 * Implementação do GetProfessionalSchedulesUseCase
 */
class GetProfessionalSchedulesUseCaseImpl @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : GetProfessionalSchedulesUseCase, FlowUseCase<GetProfessionalSchedulesUseCase.Parameters, List<Schedule>>() {

    override suspend fun executeTask(parameters: GetProfessionalSchedulesUseCase.Parameters): UiState<List<Schedule>> {
        return try {
            if (parameters.professionalId.isBlank()) {
                return UiState.Error(IllegalArgumentException("ID do profissional inválido"))
            }

            val result = if (parameters.onlyActive) {
                scheduleRepository.getActiveSchedules(parameters.professionalId)
            } else {
                scheduleRepository.getProfessionalSchedules(parameters.professionalId)
            }

            when (result) {
                is DefaultResult.Success -> UiState.Success(result.data)
                is DefaultResult.Error -> UiState.Error(Exception(result.message))
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
