package com.br.xbizitwork.domain.usecase.schedule

import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.common.DomainDefaultResult
import com.br.xbizitwork.domain.repository.ScheduleRepository
import com.br.xbizitwork.domain.result.schedule.ScheduleItemResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase para salvar agenda do profissional
 */
interface SaveScheduleUseCase {
    operator fun invoke(scheduleItems: List<ScheduleItemResult>): Flow<UiState<Unit>>
}

class SaveScheduleUseCaseImpl @Inject constructor(
    private val repository: ScheduleRepository
) : SaveScheduleUseCase, FlowUseCase<List<ScheduleItemResult>, Unit>() {
    
    override suspend fun executeTask(parameters: List<ScheduleItemResult>): UiState<Unit> {
        return try {
            when (val response = repository.saveSchedule(parameters)) {
                is DomainDefaultResult.Success -> {
                    UiState.Success(Unit)
                }

                is DomainDefaultResult.Error -> {
                    UiState.Error(Throwable(response.message))
                }
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
