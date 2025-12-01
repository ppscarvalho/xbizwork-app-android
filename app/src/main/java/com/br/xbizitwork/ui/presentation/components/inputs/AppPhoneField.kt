package com.br.xbizitwork.ui.presentation.components.inputs

import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
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
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun AppPhoneField(
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
    AppTextField(
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        value = value,
        onValueChange = onValueChange,
        textColor = textColor,
        cursorColor = cursorColor,

        leadingIcon = leadingIcon,

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done
        ),

        isError = isError
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppPhoneFieldPreview() {
    var phone by remember { mutableStateOf("") }

    XBizWorkTheme {
        AppPhoneField(
            label = "Telefone",
            placeholder = "(99) 99999-9999",
            value = phone,
            onValueChange = { phone = it },
            leadingIcon = Icons.Outlined.Phone,
            textColor = Color.Black,
            cursorColor = Color.Black
        )
    }
}