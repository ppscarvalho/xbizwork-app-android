package com.br.xbizitwork.domain.result.schedule

/**
 * Domain result para item de agenda
 */
data class ScheduleItemResult(
    val categoryId: Int,
    val categoryName: String,
    val subcategoryId: Int,
    val subcategoryName: String,
    val dayOfWeekId: Int,
    val dayOfWeekName: String,
    val startTime: String,
    val endTime: String
)
