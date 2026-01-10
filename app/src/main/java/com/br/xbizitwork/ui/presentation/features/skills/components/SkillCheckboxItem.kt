package com.br.xbizitwork.ui.presentation.features.skills.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.presentation.features.skills.state.SkillUiState
import com.br.xbizitwork.ui.theme.poppinsFontFamily

@Composable
fun SkillCheckboxItem(
    skill: SkillUiState,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onCheckedChange(!skill.checked) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = skill.checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.onPrimary
            )
        )

        Text(
            text = skill.description,
            fontFamily = poppinsFontFamily,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 8.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}
