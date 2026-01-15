package com.br.xbizitwork.ui.presentation.components.schedule

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.schedule.state.TimeSlotItem
import com.br.xbizitwork.ui.theme.XBizWorkTheme

/**
 * Lista de horários da agenda
 *
 * @param timeSlots Lista de slots de horário
 * @param onRemoveSlot Callback quando um slot é removido
 * @param modifier Modificador opcional
 */
@Composable
fun ScheduleTimeSlotList(
    timeSlots: List<TimeSlotItem>,
    onRemoveSlot: (TimeSlotItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        timeSlots.forEachIndexed { index, slot ->
            ScheduleTimeSlotRow(
                dayOfWeek = slot.dayOfWeek,
                startTime = slot.startTime,
                endTime = slot.endTime,
                onRemoveClick = { onRemoveSlot(slot) }
            )
            
            // Adiciona divider entre as linhas (exceto na última)
            if (index < timeSlots.size - 1) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScheduleTimeSlotListPreview() {
    XBizWorkTheme {
        val sampleSlots = listOf(
            TimeSlotItem(
                id = "1",
                dayOfWeek = "Segunda-feira",
                startTime = "08:00",
                endTime = "10:00"
            ),
            TimeSlotItem(
                id = "2",
                dayOfWeek = "Quarta-feira",
                startTime = "10:00",
                endTime = "12:00"
            ),
            TimeSlotItem(
                id = "3",
                dayOfWeek = "Sexta-feira",
                startTime = "12:00",
                endTime = "14:00"
            )
        )
        
        ScheduleTimeSlotList(
            timeSlots = sampleSlots,
            onRemoveSlot = {}
        )
    }
}
