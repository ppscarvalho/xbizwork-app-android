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
 * Botão de fazer login
 * Utiliza o AppButton genérico com configuração específica para login
 */
@Composable
fun LoginButton(onClick: () -> Unit) {
    AppButton(
        text = stringResource(id = R.string.fazer_login_text),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = Color.White
    )
}
