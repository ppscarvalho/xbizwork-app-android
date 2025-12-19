package com.br.xbizitwork.ui.presentation.components.inputs

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Campo de data com digitação manual + DatePicker
 * Permite digitar DD/MM/AAAA OU clicar no ícone para abrir o calendário
 *
 * NOTA: Requer API 26+ (Android 8.0) para java.time
 */
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDateField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String = "DD/MM/AAAA",
    value: LocalDate?,
    onValueChange: (LocalDate?) -> Unit,
    textColor: Color = Color.Black,
    cursorColor: Color = Color.Black
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var textValue by remember(value) {
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        mutableStateOf(value?.format(dateFormatter) ?: "")
    }

    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    // Campo de texto EDITÁVEL
    AppTextField(
        modifier = modifier,
        label = label,
        placeholder = placeholder,
        value = textValue,
        onValueChange = { newText ->
            // Limita a 10 caracteres (DD/MM/AAAA)
            if (newText.length <= 10) {
                textValue = newText

                // Tenta parsear a data quando completa
                if (newText.length == 10) {
                    try {
                        val parsedDate = LocalDate.parse(newText, dateFormatter)
                        onValueChange(parsedDate)
                    } catch (e: Exception) {
                        // Data inválida, não faz nada
                    }
                }
            }
        },
        leadingIcon = Icons.Outlined.DateRange,
        textColor = textColor,
        cursorColor = cursorColor,
        trailingIcon = Icons.Outlined.DateRange,
        onTrailingIconClick = { showDatePicker = true }
    )

    // DatePicker Dialog
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = value?.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val selectedDate = Instant.ofEpochMilli(millis)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
                        onValueChange(selectedDate)
                        textValue = selectedDate.format(dateFormatter)
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

