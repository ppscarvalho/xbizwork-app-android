package com.br.xbizitwork.ui.presentation.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.ui.theme.XBizWorkTheme

/**
 * Dialog exibido quando usuário não logado tenta assinar um plano
 * Oferece opções para fazer login ou cadastrar-se
 *
 * Componente orquestrador que compõe:
 * - PlanDialogTitle: Título do diálogo
 * - PlanDialogDescription: Descrição informativa
 * - PlanDialogActions: Container com botões de ação (Login, Cadastrar-se, Cancelar)
 */
@Composable
fun PlanSelectionDialog(
    onDismiss: () -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { PlanDialogTitle() },
        text = { PlanDialogDescription() },
        confirmButton = {
            PlanDialogActions(
                onLoginClick = onLoginClick,
                onSignUpClick = onSignUpClick,
                onDismiss = onDismiss
            )
        }
    )
}


@Preview
@Composable
private fun PlanSelectionDialogPreview() {
    XBizWorkTheme {
        PlanSelectionDialog(
            onDismiss = {},
            onLoginClick = {},
            onSignUpClick = {}
        )
    }
}
