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
 * Use Case para validar agenda no backend antes de adicionar
 * Retorna ApiResultModel (isSuccessful, message)
 */
interface ValidateScheduleOnBackendUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<ApiResultModel>>

    data class Parameters(
        val request: CreateScheduleRequest
    )
}

/**
 * Implementação do ValidateScheduleOnBackendUseCase
 */
class ValidateScheduleOnBackendUseCaseImpl @Inject constructor(
    private val repository: ScheduleRepository
) : ValidateScheduleOnBackendUseCase, FlowUseCase<ValidateScheduleOnBackendUseCase.Parameters, ApiResultModel>() {

    override suspend fun executeTask(parameters: ValidateScheduleOnBackendUseCase.Parameters): UiState<ApiResultModel> {
        return try {
            when (val result = repository.validateSchedule(parameters.request)) {
                is DefaultResult.Success -> UiState.Success(result.data)
                is DefaultResult.Error -> UiState.Error(Exception(result.message))
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}

