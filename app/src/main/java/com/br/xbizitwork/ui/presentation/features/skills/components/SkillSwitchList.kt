package com.br.xbizitwork.ui.presentation.features.skills.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.br.xbizitwork.ui.presentation.features.skills.state.SkillItemUiState

@Composable
fun SkillSwitchList(
    skills: List<SkillItemUiState>,
    onSkillCheckedChange: (skillId: Int, checked: Boolean) -> Unit
) {
    Column {
        skills.forEach { skill ->
            SkillSwitchItem(
                skill = skill,
                onCheckedChange = { checked ->
                    onSkillCheckedChange(skill.id, checked)
                }
            )
        }
    }
}
