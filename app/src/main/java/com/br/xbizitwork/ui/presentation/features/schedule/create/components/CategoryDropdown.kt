package com.br.xbizitwork.ui.presentation.features.schedule.create.components

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.domain.result.category.CategoryResult
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDropdown(
    categories: List<CategoryResult>,
    selectedCategoryName: String,
    isLoading: Boolean,
    onCategorySelected: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox (
        expanded = expanded,
        onExpandedChange = {
            if (!isLoading && categories.isNotEmpty()) {
                expanded = !expanded
            }
        },
        modifier = modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            value = selectedCategoryName,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(), // 🔥 ESSENCIAL
            readOnly = true,
            enabled = !isLoading && categories.isNotEmpty(),
            label = {
                Text(
                    text = "Categoria",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = poppinsFontFamily
                )
            },
            placeholder = { Text("Selecione uma categoria") },
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
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledTextColor = Color.Black,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = { Text(category.description) },
                    onClick = {
                        onCategorySelected(category.id, category.description)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
private fun CategoryDropdownPreview() {
    XBizWorkTheme {
        CategoryDropdown(
            categories = listOf(
                CategoryResult(1, "Reunião"),
                CategoryResult(2, "Consulta"),
                CategoryResult(3, "Treinamento"),
                CategoryResult(4, "Workshop"),
                CategoryResult(5, "Palestra"),
                CategoryResult(6, "Evento Social"),
                CategoryResult(7, "Entrevista"),
                CategoryResult(8, "Manutenção"),
                CategoryResult(9, "Inspeção"),
                CategoryResult(10, "Outro")
            ),
            selectedCategoryName = "Reunião",
            isLoading = false,
            onCategorySelected = { _, _ -> }
        )
    }
}