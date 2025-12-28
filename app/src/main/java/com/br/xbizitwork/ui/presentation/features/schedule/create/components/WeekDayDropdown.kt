package com.br.xbizitwork.ui.presentation.features.schedule.create.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekDayDropdown(
    selectedWeekDayName: String,
    onWeekDaySelected: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val weekDays = remember {
        listOf(
            0 to "Domingo",
            1 to "Segunda-feira",
            2 to "Terça-feira",
            3 to "Quarta-feira",
            4 to "Quinta-feira",
            5 to "Sexta-feira",
            6 to "Sábado"
        )
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            value = selectedWeekDayName,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(), // 🔥 ESSENCIAL
            readOnly = true,
            enabled = true,
            label = {
                Text(
                    text = "Dia da Semana",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontFamily = poppinsFontFamily
                )
            },
            placeholder = { Text("Selecione um dia") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
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
            weekDays.forEach { (dayNum, dayName) ->
                DropdownMenuItem(
                    text = { Text(dayName) },
                    onClick = {
                        onWeekDaySelected(dayNum, dayName)
                        expanded = false
                    }
                )
            }
        }
    }
}

// ============================================
// PREVIEW
// ============================================

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun WeekDayDropdownPreview() {
    XBizWorkTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            WeekDayDropdown(
                selectedWeekDayName = "Segunda-feira",
                onWeekDaySelected = { _, _ -> }
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WeekDayDropdownDarkPreview() {
    XBizWorkTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            WeekDayDropdown(
                selectedWeekDayName = "",
                onWeekDaySelected = { _, _ -> }
            )
        }
    }
}

