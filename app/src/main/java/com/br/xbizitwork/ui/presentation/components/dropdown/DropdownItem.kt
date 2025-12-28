package com.br.xbizitwork.ui.presentation.components.dropdown

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> GenericDropdown(
    label: String,
    placeholder: String,
    items: List<T>,
    selectedText: String,
    isLoading: Boolean,
    itemId: (T) -> Int,
    itemText: (T) -> String,
    onItemSelected: (id: Int, description: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (!isLoading && items.isNotEmpty()) expanded = !expanded
        },
        modifier = modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            readOnly = true,
            enabled = !isLoading && items.isNotEmpty(),
            label = { Text(text = label, fontFamily = poppinsFontFamily) },
            placeholder = { Text(placeholder) },
            trailingIcon = {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            containerColor = MaterialTheme.colorScheme.surface // ✅ ESSA LINHA é a chave
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = itemText(item),
                            fontFamily = poppinsFontFamily,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    onClick = {
                        onItemSelected(itemId(item), itemText(item))
                        expanded = false
                    }
                )
            }
        }
    }
}


@Preview(
    showBackground = true,
    name = "GenericDropdown - Light"
)
@Composable
private fun GenericDropdownLightPreview() {
    XBizWorkTheme {
        GenericDropdown(
            label = "Categoria",
            placeholder = "Selecione uma categoria",
            items = listOf(
                1 to "Reunião",
                2 to "Consulta",
                3 to "Treinamento"
            ),
            selectedText = "Reunião",
            isLoading = false,
            itemId = { it.first },
            itemText = { it.second },
            onItemSelected = { _, _ -> }
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "GenericDropdown - Dark"
)
@Composable
private fun GenericDropdownDarkPreview() {
    XBizWorkTheme {
        GenericDropdown(
            label = "Categoria",
            placeholder = "Selecione uma categoria",
            items = listOf(
                1 to "Reunião",
                2 to "Consulta",
                3 to "Treinamento"
            ),
            selectedText = "Reunião",
            isLoading = false,
            itemId = { it.first },
            itemText = { it.second },
            onItemSelected = { _, _ -> }
        )
    }
}
