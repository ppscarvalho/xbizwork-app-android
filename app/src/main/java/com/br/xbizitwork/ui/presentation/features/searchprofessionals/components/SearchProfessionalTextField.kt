package com.br.xbizitwork.ui.presentation.features.searchprofessionals.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

@Composable
fun SearchProfessionalTextField(
    modifier: Modifier = Modifier,
    queryTextState: TextFieldState
) {
    Row(
        modifier = modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.Search,
            contentDescription = "Ícone de busca",
            tint = Color(0xFF616161) // Cinza escuro fixo
        )

        BasicTextField(
            state = queryTextState,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 8.dp),
            textStyle = TextStyle(
                color = Color(0xFF212121), // Preto/cinza muito escuro fixo
                fontFamily = poppinsFontFamily
            ),
            cursorBrush = SolidColor(Color(0xFF1976D2)), // Azul fixo
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            decorator = { innerTextField ->
                Box {
                    if (queryTextState.text.isEmpty()) {
                        Text(
                            text = "Ex: educador, professor, pedreiro...",
                            style = TextStyle(
                                color = Color(0xFF9E9E9E), // Cinza claro para placeholder
                                fontFamily = poppinsFontFamily
                            )
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SearchProfessionalTextFieldPreview() {
    XBizWorkTheme {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
                .border(
                    width = 0.5.dp,
                    color = Color(0x59000000),
                    shape = RoundedCornerShape(12.dp)
                )
        ) {
            SearchProfessionalTextField(
                queryTextState = TextFieldState("educa")
            )
        }
    }
}