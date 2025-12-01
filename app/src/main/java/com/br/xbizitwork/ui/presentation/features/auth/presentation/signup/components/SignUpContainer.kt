package com.br.xbizitwork.ui.presentation.features.auth.presentation.signup.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.components.inputs.AppPasswordField
import com.br.xbizitwork.ui.presentation.components.inputs.AppTextField
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import androidx.compose.ui.graphics.Color


@Composable
fun SignUpContainer(
    modifier: Modifier = Modifier,

    isLoading: Boolean,
    buttonEnabled: Boolean,

    nameValue: String,
    emailValue: String,
    passwordValue: String,
    confirmPasswordValue: String,

    onNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onSignUpClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        // Campo Nome
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Nome",
            placeholder = "Digite seu nome",
            value = nameValue,
            onValueChange = onNameChanged,
            leadingIcon = Icons.Outlined.Person,
            textColor = colorScheme.onSurface,
            cursorColor = colorScheme.onSurface
        )

        // Campo E-mail (NOVO)
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "E-mail",
            placeholder = "Digite seu e-mail",
            value = emailValue,
            onValueChange = onEmailChanged,
            leadingIcon = Icons.Outlined.Email,
            textColor = colorScheme.onSurface,
            cursorColor = colorScheme.onSurface
        )

        // ✅ Campo Senha (NOVO)
        AppPasswordField(
            modifier = Modifier.fillMaxWidth(),
            label = "Senha",
            placeholder = "Digite sua senha",
            value = passwordValue,
            onValueChange = onPasswordChanged,
            leadingIcon = Icons.Outlined.Lock,
            textColor = colorScheme.onSurface,
            cursorColor = colorScheme.onSurface
        )

        // ✅ Confirmar Senha (NOVO)
        AppPasswordField(
            modifier = Modifier.fillMaxWidth(),
            label = "Confirmar senha",
            placeholder = "Repita sua senha",
            value = confirmPasswordValue,
            onValueChange = onConfirmPasswordChanged,
            leadingIcon = Icons.Outlined.Lock,
            textColor = colorScheme.onSurface,
            cursorColor = colorScheme.onSurface
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
                text = "Criar conta",
                contentColor = Color.White,
                enabled = buttonEnabled,
                onClick = onSignUpClick,
                isLoading = isLoading,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpContainerPreview_Step1() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    XBizWorkTheme {
        SignUpContainer(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            isLoading = false,
            buttonEnabled = true,
            nameValue = name,
            emailValue = email,
            passwordValue = password,
            confirmPasswordValue = confirmPassword,
            onNameChanged = { name = it },
            onEmailChanged = { email = it },
            onPasswordChanged = { password = it },
            onConfirmPasswordChanged = { confirmPassword = it },
            onSignUpClick = { /* ação fake no preview */ }
        )
    }
}