package com.br.xbizitwork.domain.usecase.schedule

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.schedule.dtos.requests.CreateScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.responses.ScheduleResponse
import com.br.xbizitwork.domain.repository.ScheduleRepository
import javax.inject.Inject

/**
 * Use Case para criar agenda usando o novo formato da API
 */
class CreateScheduleFromRequestUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(request: CreateScheduleRequest): DefaultResult<ScheduleResponse> {
        return repository.createScheduleFromRequest(request)
    }
}
