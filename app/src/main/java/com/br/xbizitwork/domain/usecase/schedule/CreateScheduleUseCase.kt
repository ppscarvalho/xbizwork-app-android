package com.br.xbizitwork.domain.usecase.schedule

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.schedule.Schedule
import com.br.xbizitwork.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso responsável pela criação de uma nova agenda.
 * Retorna ApiResultModel (isSuccessful, message)
 */
interface CreateScheduleUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<ApiResultModel>>

    data class Parameters(
        val schedule: Schedule
    )
}

/**
 * Implementação do CreateScheduleUseCase
 */
class CreateScheduleUseCaseImpl @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : CreateScheduleUseCase, FlowUseCase<CreateScheduleUseCase.Parameters, ApiResultModel>() {

    override suspend fun executeTask(parameters: CreateScheduleUseCase.Parameters): UiState<ApiResultModel> {
        // Validação básica
        if (parameters.schedule.category.isBlank()) {
            return UiState.Error(IllegalArgumentException("Categoria não pode ser vazia"))
        }
        
        if (parameters.schedule.specialty.isBlank()) {
            return UiState.Error(IllegalArgumentException("Especialidade não pode ser vazia"))
        }
        
        if (parameters.schedule.availability.workingHours.isEmpty()) {
            return UiState.Error(IllegalArgumentException("É necessário definir pelo menos um dia de trabalho"))
        }
        
        // Valida horários de trabalho
        val invalidHours = parameters.schedule.availability.workingHours
            .filter { it.isActive && !it.isValid() }
        
        if (invalidHours.isNotEmpty()) {
            return UiState.Error(IllegalArgumentException("Horários de trabalho inválidos"))
        }
        
        return when (val result = scheduleRepository.createSchedule(parameters.schedule)) {
            is DefaultResult.Success -> UiState.Success(result.data)
            is DefaultResult.Error -> UiState.Error(
                Exception(result.message)
            )
        }
    }
}
