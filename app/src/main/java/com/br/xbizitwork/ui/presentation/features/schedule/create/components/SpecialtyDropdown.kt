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
import com.br.xbizitwork.domain.result.specialty.SpecialtyResult
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpecialtyDropdown(
    specialties: List<SpecialtyResult>,
    selectedSpecialtyName: String,
    isLoading: Boolean,
    onSpecialtySelected: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            if (!isLoading && specialties.isNotEmpty()) {
                expanded = !expanded
            }
        },
        modifier = modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            value = selectedSpecialtyName,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(), // 🔥 essencial
            readOnly = true,
            enabled = !isLoading && specialties.isNotEmpty(),
            label = {
                Text(
                    text = "Especialidade",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = poppinsFontFamily
                )
            },
            placeholder = { Text("Selecione uma especialidade") },
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
            specialties.forEach { specialty ->
                DropdownMenuItem(
                    text = { Text(specialty.description) },
                    onClick = {
                        onSpecialtySelected(specialty.id, specialty.description)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview (showBackground = true)
@Composable
private fun SpecialtyDropdownPreview() {
    XBizWorkTheme {
        SpecialtyDropdown(
            specialties = listOf(
                SpecialtyResult(1, "Cardiologia", 1),
                SpecialtyResult(2, "Dermatologia", 1),
                SpecialtyResult(3, "Neurologia", 1),
                SpecialtyResult(4, "Pediatria", 2),
                SpecialtyResult(5, "Psiquiatria", 2),
            ),
            selectedSpecialtyName = "Cardiologia",
            isLoading = false,
            onSpecialtySelected = { _, _ -> }
        )
    }
}