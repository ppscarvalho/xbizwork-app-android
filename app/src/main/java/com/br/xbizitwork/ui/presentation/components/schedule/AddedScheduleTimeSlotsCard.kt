package com.br.xbizitwork.ui.presentation.components.schedule

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.features.schedule.create.state.ScheduleTimeSlot
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFOntFamily

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
            containerColor = MaterialTheme.colorScheme.surface
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
                color = MaterialTheme.colorScheme.primary,
                fontFamily = poppinsFOntFamily
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
                    TableHeader()

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

/**
 * Cabeçalho de Categoria
 */
@Composable
private fun CategoryHeader(categoryName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Categoria:",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontFamily = poppinsFOntFamily
        )

        Text(
            text = " $categoryName",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontFamily = poppinsFOntFamily
        )
    }
}

/**
 * Cabeçalho de Especialidade (Modalidade)
 */
@Composable
private fun SpecialtyHeader(specialtyName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .padding(vertical = 10.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Modalidade:",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            fontFamily = poppinsFOntFamily
        )

        Text(
            text = " $specialtyName",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            fontFamily = poppinsFOntFamily
        )
    }
}

/**
 * Linha de informação (Label: Valor) - DEPRECATED, mantido para compatibilidade
 */
@Composable
private fun InfoRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = poppinsFOntFamily
        )

        Text(
            text = " $value",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontFamily = poppinsFOntFamily
        )
    }
}

/**
 * Cabeçalho da tabela de horários
 */
@Composable
private fun TableHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Dia da Semana",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontFamily = poppinsFOntFamily,
            modifier = Modifier.weight(2f)
        )

        Text(
            text = "Hora Início",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontFamily = poppinsFOntFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Hora Fim",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontFamily = poppinsFOntFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Ação",
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontFamily = poppinsFOntFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(0.8f)
        )
    }
}

/**
 * Linha da tabela com os dados do horário
 */
@Composable
private fun TableRow(
    slot: ScheduleTimeSlot,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = slot.weekDayName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = poppinsFOntFamily,
            modifier = Modifier.weight(2f)
        )

        Text(
            text = slot.startTime,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = poppinsFOntFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = slot.endTime,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = poppinsFOntFamily,
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

