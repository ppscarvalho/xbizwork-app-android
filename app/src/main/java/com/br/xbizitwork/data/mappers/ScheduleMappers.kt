package com.br.xbizitwork.data.mappers

import com.br.xbizitwork.data.remote.schedule.dtos.request.SaveScheduleRequest
import com.br.xbizitwork.data.remote.schedule.dtos.request.ScheduleItemRequest
import com.br.xbizitwork.domain.result.schedule.ScheduleItemResult

/**
 * Mapeia ScheduleItemResult (Domain Layer) 
 * para ScheduleItemRequest (Data Layer)
 */
fun ScheduleItemResult.toScheduleItemRequest(): ScheduleItemRequest {
    return ScheduleItemRequest(
        categoryId = this.categoryId,
        subcategoryId = this.subcategoryId,
        dayOfWeekId = this.dayOfWeekId,
        startTime = this.startTime,
        endTime = this.endTime
    )
}

/**
 * Mapeia List<ScheduleItemResult> para SaveScheduleRequest
 */
fun List<ScheduleItemResult>.toSaveScheduleRequest(): SaveScheduleRequest {
    return SaveScheduleRequest(
        scheduleItems = this.map { it.toScheduleItemRequest() }
    )
}
