package com.br.xbizitwork.ui.presentation.features.skills.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.br.xbizitwork.ui.presentation.features.skills.state.SkillUiState

@Composable
fun SkillsCheckboxList(
    skills: List<SkillUiState>,
    onSkillCheckedChange: (skillId: Int, checked: Boolean) -> Unit
) {
    Column {
        skills.forEach { skill ->
            SkillCheckboxItem(
                skill = skill,
                onCheckedChange = { checked ->
                    onSkillCheckedChange(skill.id, checked)
                }
            )
        }
    }
}
