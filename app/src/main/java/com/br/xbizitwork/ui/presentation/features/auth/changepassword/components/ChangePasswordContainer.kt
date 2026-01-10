package com.br.xbizitwork.ui.presentation.features.auth.changepassword.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.components.inputs.AppPasswordField
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.R

/**
 * Container com os campos de alteração de senha
 * Seguindo o mesmo padrão do SignUpContainer
 */
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
            label = stringResource(R.string.description_current_password_text),
            placeholder = stringResource(R.string.hint_current_password_text),
            value = currentPasswordValue,
            onValueChange = onCurrentPasswordChanged,
            leadingIcon = Icons.Outlined.Lock,
            textColor = colorScheme.onPrimary,
            cursorColor = colorScheme.onPrimary
        )

        // Campo Nova Senha
        AppPasswordField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.description_new_password_text),
            placeholder = stringResource(R.string.hint_new_password_text),
            value = newPasswordValue,
            onValueChange = onNewPasswordChanged,
            leadingIcon = Icons.Outlined.Lock,
            textColor = colorScheme.onPrimary,
            cursorColor = colorScheme.onPrimary
        )

        // Campo Confirmar Nova Senha
        AppPasswordField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.description_confirm_new_password_text),
            placeholder = stringResource(R.string.hint_confirm_new_password_text),
            value = confirmPasswordValue,
            onValueChange = onConfirmPasswordChanged,
            leadingIcon = Icons.Outlined.Lock,
            textColor = colorScheme.onPrimary,
            cursorColor = colorScheme.onPrimary
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .shadow(5.dp, RoundedCornerShape(25.dp)),
                text = stringResource(R.string.change_password_button_text),
                contentColor = Color.White,
                enabled = buttonEnabled,
                onClick = onChangePasswordClick,
                isLoading = isLoading,
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0f344e)
@Composable
private fun ChangePasswordContainerPreview() {
    var currentPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    XBizWorkTheme {
        ChangePasswordContainer(
            modifier = Modifier.fillMaxWidth(),
            isLoading = false,
            buttonEnabled = true,
            currentPasswordValue = currentPassword,
            newPasswordValue = newPassword,
            confirmPasswordValue = confirmPassword,
            onCurrentPasswordChanged = { currentPassword = it },
            onNewPasswordChanged = { newPassword = it },
            onConfirmPasswordChanged = { confirmPassword = it },
            onChangePasswordClick = { /* ação fake no preview */ }
        )
    }
}
