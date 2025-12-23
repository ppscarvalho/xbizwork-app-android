package com.br.xbizitwork.ui.presentation.features.auth.changepassword.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.components.inputs.AppPasswordField

@Composable
fun ChangePasswordContainer(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    buttonEnabled: Boolean,
    currentPasswordValue: String,
    newPasswordValue: String,
    confirmPasswordValue: String,
    onCurrentPasswordChanged: (String) -> Unit,
    onNewPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onChangePasswordClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {

        // Campo Senha Atual
        AppPasswordField(
            modifier = Modifier.fillMaxWidth(),
            label = "Senha Atual",
            placeholder = "Digite sua senha atual",
            value = currentPasswordValue,
            onValueChange = onCurrentPasswordChanged,
            leadingIcon = Icons.Outlined.Lock,
            textColor = colorScheme.onSurfaceVariant,
            cursorColor = colorScheme.onSurfaceVariant
        )

        // Campo Nova Senha
        AppPasswordField(
            modifier = Modifier.fillMaxWidth(),
            label = "Nova Senha",
            placeholder = "Digite sua nova senha",
            value = newPasswordValue,
            onValueChange = onNewPasswordChanged,
            leadingIcon = Icons.Outlined.Lock,
            textColor = colorScheme.onSurfaceVariant,
            cursorColor = colorScheme.onSurfaceVariant
        )

        // Campo Confirmar Nova Senha
        AppPasswordField(
            modifier = Modifier.fillMaxWidth(),
            label = "Confirmar Nova Senha",
            placeholder = "Confirme sua nova senha",
            value = confirmPasswordValue,
            onValueChange = onConfirmPasswordChanged,
            leadingIcon = Icons.Outlined.Lock,
            textColor = colorScheme.onSurfaceVariant,
            cursorColor = colorScheme.onSurfaceVariant
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .shadow(5.dp, RoundedCornerShape(25.dp)),
                text = "Alterar Senha",
                contentColor = Color.White,
                enabled = buttonEnabled,
                isLoading = isLoading,
                onClick = onChangePasswordClick
            )
        }
    }
}
