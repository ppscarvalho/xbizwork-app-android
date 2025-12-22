package com.br.xbizitwork.ui.presentation.features.schedule.create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.components.schedule.CategoryDropdown
import com.br.xbizitwork.ui.presentation.components.schedule.ScheduleTimeDropdown
import com.br.xbizitwork.ui.presentation.components.schedule.SpecialtyDropdown
import com.br.xbizitwork.ui.presentation.components.schedule.WeekDayDropdown
import com.br.xbizitwork.ui.presentation.features.schedule.create.events.CreateScheduleEvent
import com.br.xbizitwork.ui.presentation.features.schedule.create.state.CreateScheduleUIState
import com.br.xbizitwork.ui.theme.XBizWorkTheme

private val scheduleTimes = listOf(
    "07:00", "08:00", "09:00", "10:00", "11:00",
    "12:00", "13:00", "14:00", "15:00", "16:00",
    "17:00", "18:00", "19:00", "20:00"
)

@Composable
fun CreateScheduleContainer(
    uiState: CreateScheduleUIState,
    onEvent: (CreateScheduleEvent) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {

        CategoryDropdown(
            categories = uiState.categories,
            selectedCategoryName = uiState.selectedCategoryName,
            isLoading = uiState.isLoadingCategories,
            onCategorySelected = { id, name ->
                onEvent(CreateScheduleEvent.OnCategorySelected(id, name))
            }
        )

        if (uiState.selectedCategoryId != null) {
            SpecialtyDropdown(
                specialties = uiState.specialties,
                selectedSpecialtyName = uiState.selectedSpecialtyName,
                isLoading = uiState.isLoadingSpecialties,
                onSpecialtySelected = { id, name ->
                    onEvent(CreateScheduleEvent.OnSpecialtySelected(id, name))
                }
            )
        }

        WeekDayDropdown(
            selectedWeekDayName = uiState.selectedWeekDayName,
            onWeekDaySelected = { weekDay, name ->
                onEvent(CreateScheduleEvent.OnWeekDaySelected(weekDay, name))
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ScheduleTimeDropdown(
                modifier = Modifier.weight(1f),
                label = "Hora Início",
                selectedTime = uiState.startTime,
                times = scheduleTimes,
                onTimeSelected = {
                    onEvent(CreateScheduleEvent.OnStartTimeChanged(it))
                }
            )

            ScheduleTimeDropdown(
                modifier = Modifier.weight(1f),
                label = "Hora Fim",
                selectedTime = uiState.endTime,
                times = scheduleTimes,
                onTimeSelected = {
                    onEvent(CreateScheduleEvent.OnEndTimeChanged(it))
                }
            )
        }

        AppButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Adicionar à Lista",
            enabled = uiState.canAddTimeSlot,
            isLoading = uiState.isLoading,
            onClick = { onEvent(CreateScheduleEvent.OnAddTimeSlot) },
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CreateScheduleContainerPreview() {
    XBizWorkTheme {
        CreateScheduleContainer(
            uiState = CreateScheduleUIState(
                selectedCategoryId = 1,
                selectedCategoryName = "Professor",
                selectedSpecialtyName = "Matemática",
                selectedWeekDayName = "Segunda-feira",
                startTime = "08:00",
                endTime = "12:00",
                canAddTimeSlot = true
            ),
            onEvent = {}
        )
    }
}
