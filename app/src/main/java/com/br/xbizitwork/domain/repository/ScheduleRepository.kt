package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.domain.common.DomainDefaultResult
import com.br.xbizitwork.domain.result.schedule.ScheduleItemResult

/**
 * Repository para agendas
 */
interface ScheduleRepository {
    suspend fun saveSchedule(scheduleItems: List<ScheduleItemResult>): DomainDefaultResult<Unit>
}
