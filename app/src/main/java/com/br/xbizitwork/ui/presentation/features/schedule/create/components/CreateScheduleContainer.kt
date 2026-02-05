package com.br.xbizitwork.ui.presentation.features.schedule.create.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.components.dropdown.GenericDropdown
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
        GenericDropdown(
            label = "Categoria",
            placeholder = "Selecione uma categoria",
            items = uiState.categories,
            selectedText = uiState.selectedCategoryName,
            isLoading = uiState.isLoadingCategories,
            itemId = { it.id },
            itemText = { it.description },
            onItemSelected = { id, name ->
                onEvent(CreateScheduleEvent.OnCategorySelected(id, name))
            }
        )
//        CategoryDropdown(
//            categories = uiState.categories,
//            selectedCategoryName = uiState.selectedCategoryName,
//            isLoading = uiState.isLoadingCategories,
//            onCategorySelected = { id, name ->
//                onEvent(CreateScheduleEvent.OnCategorySelected(id, name))
//            }
//        )

        if (uiState.selectedCategoryId != null) {
//            SpecialtyDropdown(
//                specialties = uiState.specialties,
//                selectedSpecialtyName = uiState.selectedSpecialtyName,
//                isLoading = uiState.isLoadingSpecialties,
//                onSpecialtySelected = { id, name ->
//                    onEvent(CreateScheduleEvent.OnSpecialtySelected(id, name))
//                }
//            )

            GenericDropdown(
                label = "Especialidade",
                placeholder = "Selecione uma especialidade",
                items = uiState.specialties,
                selectedText = uiState.selectedSpecialtyName,
                isLoading = uiState.isLoadingSpecialties,
                itemId = { it.id },
                itemText = { it.description },
                onItemSelected = { id, name ->
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
            contentColor = Color.White
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

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CreateScheduleContainerDarkPreview() {
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
