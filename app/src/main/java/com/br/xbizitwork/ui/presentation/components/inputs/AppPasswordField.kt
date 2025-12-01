package com.br.xbizitwork.ui.presentation.components.inputs

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun AppPasswordField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    textColor: Color,
    cursorColor: Color,
    leadingIcon: ImageVector,
    isError: Boolean = false
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    AppTextField(
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        value = value,
        onValueChange = onValueChange,
        textColor = textColor,
        cursorColor = cursorColor,

        leadingIcon = leadingIcon,
        trailingIcon = if (isPasswordVisible)
            Icons.Default.Visibility
        else
            Icons.Default.VisibilityOff,

        onTrailingIconClick = {
            isPasswordVisible = !isPasswordVisible
        },

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),

        visualTransformation = if (isPasswordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),

        isError = isError
    )
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppPasswordFieldPreview() {
    var password by remember { mutableStateOf("") }

    XBizWorkTheme {
        AppPasswordField(
            label = "Senha",
            placeholder = "Digite sua senha",
            value = password,
            onValueChange = { password = it },
            leadingIcon = Icons.Outlined.Lock,
            textColor = Color.Black,
            cursorColor = Color.Black
        )
    }
}