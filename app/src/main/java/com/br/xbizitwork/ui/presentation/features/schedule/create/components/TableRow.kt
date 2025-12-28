package com.br.xbizitwork.ui.presentation.features.schedule.create.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.features.schedule.create.state.ScheduleTimeSlot
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

/**
 * Linha da tabela com os dados do horário
 */
@Composable
fun TableRow(
    slot: ScheduleTimeSlot,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = slot.weekDayName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            fontFamily = poppinsFontFamily,
            modifier = Modifier.weight(2f)
        )

        Text(
            text = slot.startTime,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            fontFamily = poppinsFontFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = slot.endTime,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            fontFamily = poppinsFontFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = onRemove,
            modifier = Modifier.weight(0.8f)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Remover",
                tint = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Preview (showBackground = true)
@Composable
private fun TableRowPreview() {
    XBizWorkTheme() {
        TableRow(
            slot = ScheduleTimeSlot(
                id = "1",
                categoryId = 1,
                categoryName = "Educador Físico",
                specialtyId = 1,
                specialtyName = "Treino para Emagrecimento",
                weekDay = 1,
                weekDayName = "Segunda-feira",
                startTime = "08:00",
                endTime = "10:00"
            ),
            onRemove = {}
        )
    }
}