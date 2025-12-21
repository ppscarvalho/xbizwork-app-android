package com.br.xbizitwork.ui.presentation.features.schedule.create.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.components.schedule.AddedScheduleTimeSlotsCard
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

        // 🔹 Título
        item {
            Text(
                text = "Adicionar Horário",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        // 🔹 Categoria
        item {
            CategoryDropdown(
                categories = uiState.categories,
                selectedCategoryName = uiState.selectedCategoryName,
                isLoading = uiState.isLoadingCategories,
                onCategorySelected = { id, name ->
                    onEvent(CreateScheduleEvent.OnCategorySelected(id, name))
                }
            )
        }

        // 🔹 Especialidade
        if (uiState.selectedCategoryId != null) {
            item {
                SpecialtyDropdown(
                    specialties = uiState.specialties,
                    selectedSpecialtyName = uiState.selectedSpecialtyName,
                    isLoading = uiState.isLoadingSpecialties,
                    onSpecialtySelected = { id, name ->
                        onEvent(CreateScheduleEvent.OnSpecialtySelected(id, name))
                    }
                )
            }
        }

        // 🔹 Dia da semana
        item {
            WeekDayDropdown(
                selectedWeekDayName = uiState.selectedWeekDayName,
                onWeekDaySelected = { weekDay, name ->
                    onEvent(CreateScheduleEvent.OnWeekDaySelected(weekDay, name))
                }
            )
        }

        // 🔹 Horários
        item {
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
        }

        // 🔹 Botão adicionar
        item {
//            OutlinedButton(
//                onClick = { onEvent(CreateScheduleEvent.OnAddTimeSlot) },
//                enabled = uiState.canAddTimeSlot,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Text("Adicionar à Lista")
//            }

            AppButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Adicionar à Lista",
                isLoading = uiState.isLoading,
                contentColor = Color.Black,
                enabled = uiState.canAddTimeSlot,
                onClick = { onEvent(CreateScheduleEvent.OnAddTimeSlot) }
            )
        }

        // 🔹 Horários adicionados
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
                    isLoading = uiState.isLoading,
                    contentColor = Color.White,
                    enabled = uiState.canSaveSchedule && !uiState.isLoading,
                    onClick = { onEvent(CreateScheduleEvent.OnSaveSchedule) }
                )
            }
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun CreateScheduleContentPreview() {
    XBizWorkTheme {
        val fakeUiState = CreateScheduleUIState(
            categories = emptyList(),
            specialties = emptyList(),
            selectedCategoryId = 1,
            selectedCategoryName = "Professor",
            selectedSpecialtyName = "Matemática",
            selectedWeekDayName = "Segunda-feira",

            // Horários selecionados (só para visualizar)
            startTime = "08:00",
            endTime = "12:00",

            // 🔥 Removido: não usar ScheduleTimeSlot no preview
            scheduleTimeSlots = emptyList(),

            isLoading = false,
            isLoadingCategories = false,
            isLoadingSpecialties = false,
            canAddTimeSlot = true,
            canSaveSchedule = false // false porque não há slots adicionados
        )

        MaterialTheme {
            Surface {
                CreateScheduleContent(
                    paddingValues = PaddingValues(0.dp),
                    uiState = fakeUiState,
                    onEvent = {}
                )
            }
        }
    }
}
