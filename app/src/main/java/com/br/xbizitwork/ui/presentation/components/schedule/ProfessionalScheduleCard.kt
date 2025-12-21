package com.br.xbizitwork.ui.presentation.components.schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.theme.XBizWorkTheme

/**
 * Card completo exibindo informações da agenda do profissional
 *
 * Composto por:
 * 1. CategoryInfoRow - Categoria selecionada
 * 2. SpecialtyInfoRow - Especialidade selecionada
 * 3. ScheduleTableHeader - Cabeçalho da tabela
 * 4. ScheduleTimeSlotList - Lista de horários
 *
 * @param category Categoria do profissional (ex: "Educador Físico")
 * @param specialty Especialidade do profissional (ex: "Treino para Emagrecimento")
 * @param timeSlots Lista de slots de horário
 * @param onRemoveSlot Callback quando um slot é removido
 * @param modifier Modificador opcional
 */
@Composable
fun ProfessionalScheduleCard(
    category: String,
    specialty: String,
    timeSlots: List<TimeSlotItem>,
    onRemoveSlot: (TimeSlotItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 1. Categoria
            CategoryInfoRow(categoryValue = category)
            
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            // 2. Especialidade
            SpecialtyInfoRow(value = specialty)
            
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            
            // 3. Header da Tabela
            ScheduleTableHeader()
            
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
            
            // 4. Lista de Horários
            ScheduleTimeSlotList(
                timeSlots = timeSlots,
                onRemoveSlot = onRemoveSlot
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfessionalScheduleCardPreview() {
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
        
        ProfessionalScheduleCard(
            category = "Educador Físico",
            specialty = "Treino para Emagrecimento",
            timeSlots = sampleSlots,
            onRemoveSlot = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}
