package com.br.xbizitwork.ui.presentation.components.dialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.br.xbizitwork.ui.theme.poppinsFontFamily

/**
 * Botão de cancelar
 */
@Composable
fun CancelButton(onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Cancelar",
            fontFamily = poppinsFontFamily,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
