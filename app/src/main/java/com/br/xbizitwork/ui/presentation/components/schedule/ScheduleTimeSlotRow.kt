package com.br.xbizitwork.ui.presentation.components.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.theme.XBizWorkTheme

/**
 * Linha individual de horário da agenda
 *
 * @param dayOfWeek Dia da semana (ex: "Segunda-feira")
 * @param startTime Horário de início (ex: "08:00")
 * @param endTime Horário de término (ex: "10:00")
 * @param onRemoveClick Callback quando o botão de remoção é clicado
 * @param modifier Modificador opcional
 */
@Composable
fun ScheduleTimeSlotRow(
    dayOfWeek: String,
    startTime: String,
    endTime: String,
    onRemoveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Dia da Semana (40%)
        Text(
            text = dayOfWeek,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(0.4f),
            textAlign = TextAlign.Start
        )
        
        // Hora Início (20%)
        Text(
            text = startTime,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(0.2f),
            textAlign = TextAlign.Center
        )
        
        // Hora Fim (20%)
        Text(
            text = endTime,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(0.2f),
            textAlign = TextAlign.Center
        )
        
        // Ação - Botão de Remover (20%)
        IconButton(
            onClick = onRemoveClick,
            modifier = Modifier.weight(0.2f)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Remover horário",
                tint = Color(0xFFD32F2F) // ErrorRed
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ScheduleTimeSlotRowPreview() {
    XBizWorkTheme {
        ScheduleTimeSlotRow(
            dayOfWeek = "Segunda-feira",
            startTime = "08:00",
            endTime = "10:00",
            onRemoveClick = {}
        )
    }
}
