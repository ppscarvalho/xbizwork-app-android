package com.br.xbizitwork.ui.presentation.features.plans.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

@Composable
fun PlanInfoRow(
    modifier: Modifier = Modifier,
    planLabel: String = "Plano",
    planDescription: String = "Descrição do plano"
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = planLabel,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(8.dp)
            )
        }

        Text(
            text = planDescription,
            fontWeight = FontWeight.Normal,
            fontFamily = poppinsFontFamily,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlanInfoRowBasicPreview() {
    XBizWorkTheme{
        PlanInfoRow(
            planLabel = "Plano Básico",
            planDescription = "Acesso às funcionalidades essenciais do aplicativo, ideal para quem quer começar de forma simples e prática."
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlanInfoRowMediumPreview() {
    XBizWorkTheme{
        PlanInfoRow(
            planLabel = "Plano Médio",
            planDescription = "Inclui todos os recursos do Plano Básico, com funcionalidades adicionais para quem busca mais flexibilidade e produtividade."
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PlanInfoRowPremiunPreview() {
    XBizWorkTheme{
        PlanInfoRow(
            planLabel = "Plano Médio",
            planDescription = "Acesso completo a todos os recursos do aplicativo, oferecendo a melhor experiência e máximo aproveitamento da plataforma."
        )
    }
}
