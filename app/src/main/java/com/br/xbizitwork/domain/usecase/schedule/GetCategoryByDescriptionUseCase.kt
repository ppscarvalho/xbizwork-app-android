package com.br.xbizitwork.domain.usecase.schedule

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.schedule.Schedule
import com.br.xbizitwork.domain.repository.ScheduleRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import java.lang.IllegalArgumentException

interface GetCategoryByDescriptionUseCase{
    operator fun invoke(parameters: Parameters): Flow<UiState<List<Schedule>>>

    data class Parameters(
        val description: String
    )
}

class GetCategoryByDescriptionUseCaseImpl @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : GetCategoryByDescriptionUseCase, FlowUseCase<GetCategoryByDescriptionUseCase.Parameters, List<Schedule>>() {
    override suspend fun executeTask(parameters: GetCategoryByDescriptionUseCase.Parameters): UiState<List<Schedule>> {
        return try {
            if (parameters.description.isBlank()) {
                return UiState.Error(IllegalArgumentException("ID do profissional inválido"))
            }

            val result = scheduleRepository.getCategoryByDescription(parameters.description)

            when (result) {
                is DefaultResult.Success -> UiState.Success(result.data)
                is DefaultResult.Error -> UiState.Error(Exception(result.message))
            }

        }catch (e: Exception){
            UiState.Error(e)
        }
    }
}