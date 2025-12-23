package com.br.xbizitwork.domain.usecase.schedule

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.schedule.Availability
import com.br.xbizitwork.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para atualizar a disponibilidade de uma agenda
 * Retorna ApiResultModel (isSuccessful, message)
 */
interface UpdateAvailabilityUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<ApiResultModel>>

    data class Parameters(
        val scheduleId: String,
        val availability: Availability
    )
}

/**
 * Implementação do UpdateAvailabilityUseCase
 */
class UpdateAvailabilityUseCaseImpl @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : UpdateAvailabilityUseCase, FlowUseCase<UpdateAvailabilityUseCase.Parameters, ApiResultModel>() {

    override suspend fun executeTask(parameters: UpdateAvailabilityUseCase.Parameters): UiState<ApiResultModel> {
        return try {
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

            when (val result = scheduleRepository.updateAvailability(
                parameters.scheduleId,
                parameters.availability
            )) {
                is DefaultResult.Success -> UiState.Success(result.data)
                is DefaultResult.Error -> UiState.Error(Exception(result.message))
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
