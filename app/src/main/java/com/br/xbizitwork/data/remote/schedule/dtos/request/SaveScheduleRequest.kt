package com.br.xbizitwork.data.remote.schedule.dtos.request

import com.google.gson.annotations.SerializedName

/**
 * DTO de requisição para salvar agenda
 */
data class SaveScheduleRequest(
    @SerializedName("schedule_items")
    val scheduleItems: List<ScheduleItemRequest>
)

data class ScheduleItemRequest(
    @SerializedName("category_id")
    val categoryId: Int,

    @SerializedName("subcategory_id")
    val subcategoryId: Int,

    @SerializedName("day_of_week_id")
    val dayOfWeekId: Int,

    @SerializedName("start_time")
    val startTime: String,

    @SerializedName("end_time")
    val endTime: String
)
