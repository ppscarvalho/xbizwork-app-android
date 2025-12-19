package com.br.xbizitwork.ui.presentation.components.inputs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.theme.poppinsFOntFamily

/**
 * Dropdown Field customizado para seleção de opções
 *
 * @param label Texto do label
 * @param placeholder Texto quando nada está selecionado
 * @param selectedValue Valor atualmente selecionado
 * @param options Lista de opções disponíveis
 * @param onValueChange Callback quando valor muda
 * @param leadingIcon Ícone opcional no início
 */
@Composable
fun AppDropdownField(
    modifier: Modifier = Modifier,
    label: String,
    placeholder: String,
    selectedValue: String,
    options: List<DropdownOption>,
    onValueChange: (String) -> Unit,
    leadingIcon: ImageVector? = null,
    textColor: Color = Color(0xFF1A5D5D),
    backgroundColor: Color = Color.White,
) {
    var expanded by remember { mutableStateOf(false) }

    // Busca o display text do valor selecionado
    val displayText = options.find { it.value == selectedValue }?.displayText ?: placeholder

    Column(modifier = modifier) {
        // Label
        Text(
            text = label,
            fontFamily = poppinsFOntFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = textColor,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        // Dropdown Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(backgroundColor)
                .border(
                    width = 1.dp,
                    color = Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Ícone (se houver)
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = textColor,
                        modifier = Modifier.padding(end = 12.dp)
                    )
                }

                // Texto selecionado
                Text(
                    text = displayText,
                    fontFamily = poppinsFOntFamily,
                    fontSize = 14.sp,
                    color = if (selectedValue.isEmpty()) Color(0xFF999999) else textColor,
                    modifier = Modifier.weight(1f)
                )

                // Ícone de dropdown
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expandir",
                    tint = textColor
                )
            }

            // Menu dropdown
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .background(backgroundColor)
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = option.displayText,
                                fontFamily = poppinsFOntFamily,
                                fontSize = 14.sp,
                                color = textColor
                            )
                        },
                        onClick = {
                            onValueChange(option.value)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

/**
 * Modelo para opções do dropdown
 */
data class DropdownOption(
    val value: String,
    val displayText: String
)

