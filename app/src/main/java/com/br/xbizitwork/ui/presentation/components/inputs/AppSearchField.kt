package com.br.xbizitwork.ui.presentation.components.inputs


import android.content.res.Configuration
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun AppSearchField(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    textColor: Color,
    cursorColor: Color,
    onSearch: () -> Unit
) {
    AppTextField(
        modifier = modifier,
        label = "",
        placeholder = placeholder,
        value = value,
        onValueChange = onValueChange,
        textColor = textColor,
        cursorColor = cursorColor,

        leadingIcon = Icons.Outlined.Search,
        trailingIcon = Icons.AutoMirrored.Outlined.ArrowForward,

        onTrailingIconClick = { onSearch() },

        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        )
    )
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppSearchFieldPreview() {
    var search by remember { mutableStateOf("") }

    XBizWorkTheme {
        AppSearchField(
            placeholder = "Buscar...",
            value = search,
            onValueChange = { search = it },
            textColor = Color.Black,
            cursorColor = Color.Black,
            onSearch = { /* ação de busca no preview */ }
        )
    }
}