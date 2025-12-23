package com.br.xbizitwork.ui.presentation.components.schedule

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.theme.BeigeBackground
import com.br.xbizitwork.ui.theme.XBizWorkTheme

/**
 * Componente que exibe informações da categoria selecionada pelo profissional.
 *
 * Este componente apresenta um rótulo e o valor da categoria em um layout horizontal,
 * seguindo o padrão visual estabelecido nos componentes de formulário da aplicação.
 *
 * @param categoryLabel Texto do rótulo que identifica o campo (padrão: "Categoria:")
 * @param categoryValue Nome da categoria selecionada pelo profissional
 * @param modifier Modificador Compose para customização adicional do layout
 */
@Composable
fun CategoryInfoRow(
    categoryLabel: String = "Categoria:",
    categoryValue: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp).
                background(BeigeBackground)
        ,
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = categoryLabel,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(end = 8.dp)
        )
        
        Text(
            text = categoryValue,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(
    name = "Category Info Row - Light",
    showBackground = true
)
@Composable
private fun CategoryInfoRowPreview() {
    XBizWorkTheme(darkTheme = false) {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {
                CategoryInfoRow(
                    categoryLabel = "Especialidade:",
                    categoryValue = "Personal Trainer"
                )
                
                CategoryInfoRow(
                    categoryValue = "Nutricionista Esportivo"
                )
            }
        }
    }
}

@Preview(
    name = "Category Info Row - Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun CategoryInfoRowDarkPreview() {
    XBizWorkTheme(darkTheme = true) {
        Surface {
            Column(modifier = Modifier.padding(16.dp)) {
                CategoryInfoRow(
                    categoryValue = "Educador Físico"
                )

                CategoryInfoRow(
                    categoryLabel = "Especialidade:",
                    categoryValue = "Personal Trainer"
                )
            }
        }
    }
}
