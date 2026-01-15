package com.br.xbizitwork.ui.presentation.features.auth.signin.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
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
import com.br.xbizitwork.ui.presentation.components.inputs.AppTextField
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.R

@Composable
fun SignInContainer(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    buttonEnabled: Boolean,
    emailValue: String,
    passwordValue: String,

    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSignInClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {

        // Campo E-mail (NOVO)
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.description_email_text),
            placeholder = stringResource(R.string.hint_email_text),
            value = emailValue,
            onValueChange = onEmailChanged,
            leadingIcon = Icons.Outlined.Email,
            textColor = colorScheme.onPrimary,
            cursorColor = colorScheme.onPrimary
        )

        // ✅ Campo Senha (NOVO)
        AppPasswordField(
            modifier = Modifier.fillMaxWidth(),
            label = stringResource(R.string.description_password_text),
            placeholder = stringResource(R.string.hint_password_text),
            value = passwordValue,
            onValueChange = onPasswordChanged,
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
                    .height(45.dp)
                    .shadow(5.dp, RoundedCornerShape(25.dp)),
                text = stringResource(R.string.signIn_button_text),
                contentColor = Color.White,
                enabled = buttonEnabled,
                onClick = onSignInClick,
                isLoading = isLoading,
            )
        }
    }
}

@Preview (showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0f344e)
@Composable
private fun SignInContainerPreview() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    XBizWorkTheme {
        SignInContainer(
            modifier = Modifier.fillMaxWidth(),
            isLoading = false,
            buttonEnabled = true,
            emailValue = email,
            passwordValue = password,
            onEmailChanged = { email = it },
            onPasswordChanged = { password = it },
            onSignInClick = { /* ação fake no preview */ }
        )
    }
}