package com.br.xbizitwork.ui.presentation.components.buttons

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFOntFamily

/**
 * Componente de botão para menu com layout:
 * [Ícone Esquerda] | [Texto] | [Ícone Direita]
 *
 * @param modifier Modificador Compose
 * @param leftIcon Ícone exibido no lado esquerdo
 * @param text Texto do botão
 * @param rightIcon Ícone exibido no lado direito (padrão: arrow)
 * @param hasDividerAfter Se deve mostrar divisor após o botão
 * @param onClick Callback ao clicar no botão
 */
@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    leftIcon: ImageVector,
    text: String,
    rightIcon: ImageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
    hasDividerAfter: Boolean = true,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(
                    horizontal = 16.dp,
                    vertical = 16.dp
                ),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone esquerdo
            Icon(
                imageVector = leftIcon,
                contentDescription = text,
                tint = MaterialTheme.colorScheme.primary
            )

            // Texto
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = poppinsFOntFamily,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )

            // Ícone direito
            Icon(
                imageVector = rightIcon,
                contentDescription = "Navegar",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        // Divisor inferior (condicional)
        if (hasDividerAfter) {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFF3F3F3
)
@Composable
private fun MenuButtonPreview() {
    XBizWorkTheme {
        MenuButton(
            leftIcon = Icons.Filled.Key,
            text = "Alterar Senha",
            onClick = { }
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MenuButtonVariantsPreview() {
    XBizWorkTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MenuButton(
                leftIcon = Icons.Filled.Key,
                text = "Alterar Senha",
                onClick = { }
            )

            MenuButton(
                leftIcon = Icons.Filled.DateRange,
                text = "Monte sua agenda",
                onClick = { }
            )

            MenuButton(
                leftIcon = Icons.AutoMirrored.Filled.Assignment,
                text = "Seu plano",
                onClick = { }
            )

            MenuButton(
                leftIcon = Icons.Filled.Event,
                text = "Meus compromissos",
                onClick = { }
            )

            MenuButton(
                leftIcon = Icons.Filled.ViewModule,
                text = "Agenda profissional",
                onClick = { }
            )
        }
    }
}
