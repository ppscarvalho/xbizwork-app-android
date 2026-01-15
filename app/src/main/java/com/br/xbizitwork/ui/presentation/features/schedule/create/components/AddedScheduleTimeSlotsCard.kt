package com.br.xbizitwork.ui.presentation.features.schedule.create.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.schedule.ScheduleTableHeader
import com.br.xbizitwork.ui.presentation.features.schedule.create.state.ScheduleTimeSlot
import com.br.xbizitwork.ui.theme.BeigeBackground
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

/**
 * Card que exibe os horários adicionados antes de salvar
 *
 * Agrupa por:
 * - Categoria (ex: Educador Físico)
 *   - Especialidade (ex: Musculação)
 *     - Tabela com horários
 *   - Especialidade (ex: Treino para Emagrecimento)
 *     - Tabela com horários
 */
@Composable
fun AddedScheduleTimeSlotsCard(
    timeSlots: List<ScheduleTimeSlot>,
    onRemoveSlot: (ScheduleTimeSlot) -> Unit,
    modifier: Modifier = Modifier
) {
    // Agrupar por Categoria → Especialidade
    val groupedByCategory = timeSlots.groupBy { it.categoryName }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = BeigeBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 📌 Título
            Text(
                text = "Visualização",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontFamily = poppinsFontFamily
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.primary
            )

            // Para cada Categoria
            groupedByCategory.forEach { (categoryName, slotsInCategory) ->
                // 📌 Categoria
                CategoryHeader(categoryName = categoryName)

                // Agrupar por Especialidade dentro da Categoria
                val groupedBySpecialty = slotsInCategory.groupBy { it.specialtyName }

                // Para cada Especialidade
                groupedBySpecialty.forEach { (specialtyName, slotsInSpecialty) ->
                    // 📌 Especialidade (Modalidade)
                    SpecialtyHeader(specialtyName = specialtyName)
                    // 📌 Cabeçalho da Tabela
                    //TableHeader()
                    ScheduleTableHeader()

                    // 📌 Linhas da Tabela
                    slotsInSpecialty.forEachIndexed { index, slot ->
                        TableRow(
                            slot = slot,
                            onRemove = { onRemoveSlot(slot) }
                        )
                        // Adicionar divisor apenas entre linhas (não no final)
                        if (index < slotsInSpecialty.size - 1) {
                            HorizontalDivider(
                                color = MaterialTheme.colorScheme.outlineVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

// ============================================
// PREVIEW
// ============================================

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun AddedScheduleTimeSlotsCardPreview() {
    XBizWorkTheme {
        val sampleSlots = listOf(
            // Educador Físico → Treino para Emagrecimento
            ScheduleTimeSlot(
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
            ScheduleTimeSlot(
                id = "2",
                categoryId = 1,
                categoryName = "Educador Físico",
                specialtyId = 1,
                specialtyName = "Treino para Emagrecimento",
                weekDay = 3,
                weekDayName = "Quarta-feira",
                startTime = "10:00",
                endTime = "12:00"
            ),
            ScheduleTimeSlot(
                id = "3",
                categoryId = 1,
                categoryName = "Educador Físico",
                specialtyId = 1,
                specialtyName = "Treino para Emagrecimento",
                weekDay = 5,
                weekDayName = "Sexta-feira",
                startTime = "12:00",
                endTime = "14:00"
            ),
            // Educador Físico → Musculação
            ScheduleTimeSlot(
                id = "4",
                categoryId = 1,
                categoryName = "Educador Físico",
                specialtyId = 2,
                specialtyName = "Musculação",
                weekDay = 1,
                weekDayName = "Segunda-feira",
                startTime = "14:00",
                endTime = "16:00"
            ),
            ScheduleTimeSlot(
                id = "5",
                categoryId = 1,
                categoryName = "Educador Físico",
                specialtyId = 2,
                specialtyName = "Musculação",
                weekDay = 3,
                weekDayName = "Quarta-feira",
                startTime = "16:00",
                endTime = "18:00"
            ),
            ScheduleTimeSlot(
                id = "6",
                categoryId = 1,
                categoryName = "Educador Físico",
                specialtyId = 2,
                specialtyName = "Musculação",
                weekDay = 5,
                weekDayName = "Sexta-feira",
                startTime = "18:00",
                endTime = "20:00"
            )
        )

        AddedScheduleTimeSlotsCard(
            timeSlots = sampleSlots,
            onRemoveSlot = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddedScheduleTimeSlotsCardDarkPreview() {
    XBizWorkTheme {
        val sampleSlots = listOf(
            // Educador Físico → Musculação
            ScheduleTimeSlot(
                id = "1",
                categoryId = 1,
                categoryName = "Educador Físico",
                specialtyId = 1,
                specialtyName = "Musculação",
                weekDay = 1,
                weekDayName = "Segunda-feira",
                startTime = "08:00",
                endTime = "10:00"
            ),
            // Educador Físico → Treino Funcional
            ScheduleTimeSlot(
                id = "2",
                categoryId = 1,
                categoryName = "Educador Físico",
                specialtyId = 2,
                specialtyName = "Treino Funcional",
                weekDay = 3,
                weekDayName = "Quarta-feira",
                startTime = "14:00",
                endTime = "16:00"
            )
        )

        AddedScheduleTimeSlotsCard(
            timeSlots = sampleSlots,
            onRemoveSlot = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

