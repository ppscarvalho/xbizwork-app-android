package com.br.xbizitwork.ui.presentation.features.schedule.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.domain.result.schedule.ScheduleItemResult
import com.br.xbizitwork.ui.theme.poppinsFOntFamily

/**
 * Card para exibir um grupo de horários por modalidade (categoria + subcategoria)
 */
@Composable
fun ScheduleGroupCard(
    categoryName: String,
    subcategoryName: String,
    items: List<ScheduleItemResult>,
    onRemoveItem: (ScheduleItemResult) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Título da modalidade
            Text(
                text = "Modalidade: $categoryName – $subcategoryName",
                fontFamily = poppinsFOntFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = Color(0xFF1A5D5D),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Cabeçalho da tabela
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Dia da Semana",
                    fontFamily = poppinsFOntFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Color(0xFF666666),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Início",
                    fontFamily = poppinsFOntFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Color(0xFF666666),
                    modifier = Modifier.weight(0.6f)
                )
                Text(
                    text = "Fim",
                    fontFamily = poppinsFOntFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Color(0xFF666666),
                    modifier = Modifier.weight(0.6f)
                )
                Text(
                    text = "Ação",
                    fontFamily = poppinsFOntFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Color(0xFF666666),
                    modifier = Modifier.weight(0.4f)
                )
            }

            // Linhas de horários
            items.forEach { item ->
                ScheduleItemRow(
                    item = item,
                    onRemove = { onRemoveItem(item) }
                )
            }
        }
    }
}

/**
 * Linha de horário individual
 */
@Composable
private fun ScheduleItemRow(
    item: ScheduleItemResult,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.dayOfWeekName,
            fontFamily = poppinsFOntFamily,
            fontSize = 14.sp,
            color = Color(0xFF333333),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = item.startTime,
            fontFamily = poppinsFOntFamily,
            fontSize = 14.sp,
            color = Color(0xFF333333),
            modifier = Modifier.weight(0.6f)
        )
        Text(
            text = item.endTime,
            fontFamily = poppinsFOntFamily,
            fontSize = 14.sp,
            color = Color(0xFF333333),
            modifier = Modifier.weight(0.6f)
        )
        IconButton(
            onClick = onRemove,
            modifier = Modifier.weight(0.4f)
        ) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Remover",
                tint = Color(0xFFE74C3C)
            )
        }
    }
}
