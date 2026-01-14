package com.br.xbizitwork.ui.presentation.features.skills.events

import com.br.xbizitwork.ui.presentation.features.skills.state.SkillItemUiState

sealed class SkillsEvent {
    data object OnLoadCategories : SkillsEvent()
    data class OnSaveSkills(val selectedSkills: List<SkillItemUiState>) : SkillsEvent()
}