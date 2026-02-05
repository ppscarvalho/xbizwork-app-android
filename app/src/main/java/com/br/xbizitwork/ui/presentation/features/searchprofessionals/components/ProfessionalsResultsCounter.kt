package com.br.xbizitwork.ui.presentation.features.searchprofessionals.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

/**
 * Componente que exibe a contagem de profissionais encontrados
 * Exemplo: "Foram encontrados 15 profissionais Educador Físico"
 */
@Composable
fun ProfessionalsResultsCounter(
    count: Int,
    searchQuery: String,
    modifier: Modifier = Modifier
) {
    if (count > 0 && searchQuery.isNotBlank()) {
        val text = if (count == 1) {
            "Foi encontrado $count profissional $searchQuery"
        } else {
            "Foram encontrados $count profissionais $searchQuery"
        }

        Text(
            text = text,
            fontFamily = poppinsFontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onPrimary, // Branco para contrastar com fundo escuro
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun ProfessionalsResultsCounterPreview() {
    XBizWorkTheme {
        ProfessionalsResultsCounter(
            count = 15,
            searchQuery = "Educador Físico"
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun ProfessionalsResultsCounterSinglePreview() {
    XBizWorkTheme {
        ProfessionalsResultsCounter(
            count = 1,
            searchQuery = "Educador Físico"
        )
    }
}
