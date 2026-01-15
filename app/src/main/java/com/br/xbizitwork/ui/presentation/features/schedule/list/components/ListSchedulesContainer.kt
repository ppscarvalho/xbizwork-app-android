package com.br.xbizitwork.ui.presentation.features.schedule.list.components

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.domain.model.schedule.Availability
import com.br.xbizitwork.domain.model.schedule.DayOfWeek
import com.br.xbizitwork.domain.model.schedule.Schedule
import com.br.xbizitwork.domain.model.schedule.WorkingHours
import com.br.xbizitwork.ui.presentation.components.schedule.state.TimeSlotItem
import com.br.xbizitwork.ui.presentation.features.schedule.list.state.ListSchedulesUIState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun ListSchedulesContainer(
    uiState: ListSchedulesUIState,
    onRemoveSlot: (String) -> Unit = {}
) {
    val groupedSchedules = uiState.schedules.groupBy {
        "${it.category}|${it.specialty}"
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        groupedSchedules.forEach { (key, schedulesList) ->
            val firstSchedule = schedulesList.first()
            val category = firstSchedule.category
            val specialty = firstSchedule.specialty

            val allTimeSlots = schedulesList.flatMap { schedule ->
                schedule.availability.workingHours.map { wh ->
                    val startStr = wh.startTime.toString()
                    val endStr = wh.endTime.toString()

                    TimeSlotItem(
                        id = schedule.id,
                        dayOfWeek = wh.dayOfWeek.displayName,
                        startTime = startStr.take(5),
                        endTime = endStr.take(5)
                    )
                }
            }

            item(key = key) {
                ProfessionalScheduleCard(
                    category = category,
                    specialty = specialty,
                    timeSlots = allTimeSlots,
                    onRemoveSlot = { slotId ->
                        onRemoveSlot(slotId.id)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListSchedulesContainerPreview() {
    XBizWorkTheme {
        ListSchedulesContainer(
            uiState = ListSchedulesUIState(
                isLoading = false,
                schedules = listOf(
                    fakeSchedule(
                        scheduleId = "1",
                        professionalId = "14",
                        category = "Saúde",
                        specialty = "Fisioterapia",
                        dayOfWeek = DayOfWeek.MONDAY,
                        start = "08:00",
                        end = "10:00"
                    ),
                    fakeSchedule(
                        scheduleId = "2",
                        professionalId = "14",
                        category = "Saúde",
                        specialty = "Fisioterapia",
                        dayOfWeek = DayOfWeek.WEDNESDAY,
                        start = "14:00",
                        end = "16:00"
                    )
                )
            )
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ListSchedulesContainerDarkPreview() {
    XBizWorkTheme {
        ListSchedulesContainer(
            uiState = ListSchedulesUIState(
                isLoading = false,
                schedules = listOf(
                    fakeSchedule(
                        scheduleId = "1",
                        professionalId = "14",
                        category = "Saúde",
                        specialty = "Fisioterapia",
                        dayOfWeek = DayOfWeek.MONDAY,
                        start = "08:00",
                        end = "10:00"
                    ),
                    fakeSchedule(
                        scheduleId = "2",
                        professionalId = "14",
                        category = "Saúde",
                        specialty = "Fisioterapia",
                        dayOfWeek = DayOfWeek.WEDNESDAY,
                        start = "14:00",
                        end = "16:00"
                    )
                )
            )
        )
    }
}

@SuppressLint("NewApi")
private fun fakeSchedule(
    scheduleId: String,
    professionalId: String,
    category: String,
    specialty: String,
    dayOfWeek: DayOfWeek,
    start: String,
    end: String
): Schedule {
    val now = LocalDateTime.now()

    return Schedule(
        id = scheduleId,
        professionalId = professionalId,
        category = category,
        specialty = specialty,
        availability = Availability(
            id = "availability-$scheduleId",
            professionalId = professionalId,
            workingHours = listOf(
                WorkingHours(
                    dayOfWeek = dayOfWeek,
                    startTime = LocalTime.parse(start),
                    endTime = LocalTime.parse(end)
                )
            ),
            effectiveFrom = now,
            effectiveUntil = null,
            isActive = true,
            createdAt = now,
            updatedAt = now
        ),
        timeSlots = emptyList(),
        serviceDurationMinutes = 60,
        isActive = true,
        createdAt = now,
        updatedAt = now
    )
}