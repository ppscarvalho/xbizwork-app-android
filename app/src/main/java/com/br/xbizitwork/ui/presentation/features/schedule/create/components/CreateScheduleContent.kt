package com.br.xbizitwork.ui.presentation.features.schedule.create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.schedule.create.events.CreateScheduleEvent
import com.br.xbizitwork.ui.presentation.features.schedule.create.state.CreateScheduleUIState
import com.br.xbizitwork.ui.presentation.features.schedule.create.state.ScheduleTimeSlot
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun CreateScheduleContent(
    paddingValues: PaddingValues,
    uiState: CreateScheduleUIState,
    onEvent: (CreateScheduleEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Text(
                text = "Adicionar Horário",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        item {
            CreateScheduleContainer(
                uiState = uiState,
                onEvent = onEvent
            )
        }

        if (uiState.scheduleTimeSlots.isNotEmpty()) {
            item {
                AddedScheduleTimeSlotsCard(
                    timeSlots = uiState.scheduleTimeSlots,
                    onRemoveSlot = { slot ->
                        onEvent(CreateScheduleEvent.OnRemoveTimeSlot(slot.id))
                    }
                )
            }

            item {
                AppButton(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Salvar Agenda",
                    enabled = uiState.canSaveSchedule && !uiState.isLoading,
                    isLoading = uiState.isLoading,
                    onClick = { onEvent(CreateScheduleEvent.OnSaveSchedule) },
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun CreateScheduleScreenPreview() {
    XBizWorkTheme {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                AppTopBar(
                    isHomeMode = false,
                    title = "Criar Agenda",
                    navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    enableNavigationUp = true,
                    onNavigationIconButton = {  }
                )
            }
        ) { paddingValues ->

            CreateScheduleContent(
                paddingValues = paddingValues,
                uiState = CreateScheduleUIState(
                    selectedCategoryId = 1,
                    selectedCategoryName = "Professor",
                    selectedSpecialtyId = 10,
                    selectedSpecialtyName = "Matemática",
                    selectedWeekDay = 1,
                    selectedWeekDayName = "Segunda-feira",
                    startTime = "08:00",
                    endTime = "12:00",
                    canAddTimeSlot = true,
                    canSaveSchedule = true,
                    scheduleTimeSlots = listOf(
                        ScheduleTimeSlot(
                            id = "1",
                            categoryId = 1,
                            categoryName = "Professor",
                            specialtyId = 10,
                            specialtyName = "Matemática",
                            weekDay = 1,
                            weekDayName = "Segunda-feira",
                            startTime = "08:00",
                            endTime = "12:00"
                        ),
                        ScheduleTimeSlot(
                            id = "2",
                            categoryId = 1,
                            categoryName = "Professor",
                            specialtyId = 11,
                            specialtyName = "Física",
                            weekDay = 3,
                            weekDayName = "Quarta-feira",
                            startTime = "14:00",
                            endTime = "18:00"
                        )
                    )
                ),
                onEvent = {}
            )
        }
    }
}
