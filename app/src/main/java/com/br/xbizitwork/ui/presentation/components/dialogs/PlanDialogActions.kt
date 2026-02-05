package com.br.xbizitwork.ui.presentation.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Container de ações do diálogo (Login, Cadastrar-se, Cancelar)
 */
@Composable
fun PlanDialogActions(
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LoginButton(onClick = onLoginClick)
        SignUpButton(onClick = onSignUpClick)
        CancelButton(onClick = onDismiss)
    }
}
