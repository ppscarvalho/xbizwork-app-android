package com.br.xbizitwork.ui.presentation.features.schedule.search.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.domain.model.schedule.Availability
import com.br.xbizitwork.domain.model.schedule.DayOfWeek
import com.br.xbizitwork.domain.model.schedule.Schedule
import com.br.xbizitwork.domain.model.schedule.WorkingHours
import com.br.xbizitwork.ui.presentation.components.state.ErrorState
import com.br.xbizitwork.ui.presentation.components.state.LoadingIndicator
import com.br.xbizitwork.ui.presentation.features.schedule.search.events.SearchSchedulesEvent
import com.br.xbizitwork.ui.presentation.features.schedule.search.state.SearchSchedulesUIState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun SearchScheduleContent(
    modifier: Modifier = Modifier,
    uiState: SearchSchedulesUIState,
    onEvent: (SearchSchedulesEvent) -> Unit,
    onNavigateToScheduleDetail: (String) -> Unit
) {
    when {
        uiState.isLoading -> {
            LoadingIndicator()
        }

        uiState.errorMessage != null -> {
            ErrorState(
                message = uiState.errorMessage,
                onRetry = { onEvent(SearchSchedulesEvent.OnRefresh) }
            )
        }

        uiState.isEmpty -> {
            SearchScheduleEmptyState(modifier = modifier.padding(12.dp))
        }

        uiState.schedules.isNotEmpty() -> {
            SearchScheduleContainer(
                uiState = uiState,
                onNavigateToScheduleDetail = { scheduleId ->
                    onEvent(SearchSchedulesEvent.OnViewSchedule(scheduleId))
                }
            )
        }

        else -> {
            Text(
                text = "Pequisa por categoria",
                maxLines = 2,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold,
                fontFamily = poppinsFontFamily,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}


@Preview (showBackground = true)
@Composable
private fun SearchScheduleContentPreview() {
    XBizWorkTheme{
        SearchScheduleContent(
            uiState = SearchSchedulesUIState(
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
            ),
            onEvent = {},
            onNavigateToScheduleDetail = {}
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