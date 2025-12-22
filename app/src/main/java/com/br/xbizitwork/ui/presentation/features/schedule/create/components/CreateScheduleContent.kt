package com.br.xbizitwork.ui.presentation.features.schedule.create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.components.schedule.AddedScheduleTimeSlotsCard
import com.br.xbizitwork.ui.presentation.features.schedule.create.events.CreateScheduleEvent
import com.br.xbizitwork.ui.presentation.features.schedule.create.state.CreateScheduleUIState
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
                fontWeight = FontWeight.Bold
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

@Preview (showBackground = true)
@Composable
private fun CreateScheduleContentPreview() {
    XBizWorkTheme {
        CreateScheduleContent(
            paddingValues = PaddingValues(0.dp),
            uiState = CreateScheduleUIState(),
            onEvent = {}
        )
    }

}
