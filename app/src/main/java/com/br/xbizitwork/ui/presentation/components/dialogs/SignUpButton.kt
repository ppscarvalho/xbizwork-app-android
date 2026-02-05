package com.br.xbizitwork.ui.presentation.components.dialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.br.xbizitwork.R
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton

/**
 * Botão de cadastrar-se
 * Utiliza o AppButton genérico com configuração específica para cadastro
 */
@Composable
fun SignUpButton(onClick: () -> Unit) {
    AppButton(
        text = stringResource(id = R.string.cadastrar_se_text),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.secondary,
        contentColor = Color.White
    )
}
