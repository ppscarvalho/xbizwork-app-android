package com.br.xbizitwork.domain.usecase.schedule

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.data.remote.schedule.dtos.requests.CreateScheduleRequest
import com.br.xbizitwork.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para criar agenda usando o novo formato da API
 * Retorna ApiResultModel (isSuccessful, message)
 */
interface CreateScheduleFromRequestUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<ApiResultModel>>

    data class Parameters(
        val request: CreateScheduleRequest
    )
}

/**
 * Implementação do CreateScheduleFromRequestUseCase
 */
class CreateScheduleFromRequestUseCaseImpl @Inject constructor(
    private val repository: ScheduleRepository
) : CreateScheduleFromRequestUseCase, FlowUseCase<CreateScheduleFromRequestUseCase.Parameters, ApiResultModel>() {

    override suspend fun executeTask(parameters: CreateScheduleFromRequestUseCase.Parameters): UiState<ApiResultModel> {
        return try {
            when (val result = repository.createScheduleFromRequest(parameters.request)) {
                is DefaultResult.Success -> UiState.Success(result.data)
                is DefaultResult.Error -> UiState.Error(Exception(result.message))
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
