package com.br.xbizitwork.ui.presentation.components.inputs


import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,

    // Configurações genéricas
    textColor: Color,
    cursorColor: Color,

    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,

    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    enabled: Boolean = true,

    // ✅ NOVO: Callback quando perde o foco
    onFocusLost: (() -> Unit)? = null,
    maxLength: Int? = null,
) {
    Column(modifier = modifier) {

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    // ✅ Chama callback quando PERDE o foco
                    if (!focusState.isFocused && onFocusLost != null) {
                        onFocusLost()
                    }
                },
//            value = value,
//            onValueChange = onValueChange,
            value = value,
            onValueChange = { newValue ->
                if (maxLength == null || newValue.length <= maxLength) {
                    onValueChange(newValue)
                }
            },

            textStyle = TextStyle(
                fontFamily = poppinsFontFamily,
                textAlign = TextAlign.Start,
                color = textColor
            ),

            label = {
                Text(
                    text = label,
                    color = textColor,
                    fontFamily = poppinsFontFamily
                )
            },

            placeholder = {
                Text(
                    text = placeholder,
                    fontFamily = poppinsFontFamily,
                    color = colorScheme.onPrimary,
                )
            },

            leadingIcon = {
                leadingIcon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = "Leading Icon",
                        tint = colorScheme.onPrimary
                    )
                }
            },

            trailingIcon = {
                trailingIcon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = "Trailing Icon",
                        tint = colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clickable { onTrailingIconClick?.invoke() }
                    )
                }
            },

            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation,
            isError = isError,
            enabled = enabled,

            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = cursorColor,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                errorBorderColor = Color.Red,
                // ✅ Cores para estado desabilitado - mantém a borda visível
                disabledBorderColor = Color.White,
                disabledTextColor = Color.White,
                disabledLabelColor = Color.White,
                disabledPlaceholderColor = Color.LightGray,
                disabledLeadingIconColor = Color.White
            )
        )
    }
}

@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0f344e)
@Composable
private fun AppTextFieldPreview() {
    var name by remember { mutableStateOf("") }

    XBizWorkTheme {
        AppTextField(
            label = "Nome",
            placeholder = "Digite seu nome",
            value = name,
            onValueChange = { name = it },
            leadingIcon = Icons.Outlined.Person,
            textColor = Color.White,
            cursorColor = Color.Black,
            trailingIcon = Icons.Outlined.Person,
            maxLength = 10
        )
    }
}
