package com.br.xbizitwork.ui.presentation.features.schedule.state

import com.br.xbizitwork.domain.result.category.CategoryResult
import com.br.xbizitwork.domain.result.schedule.ScheduleItemResult
import com.br.xbizitwork.domain.result.subcategory.SubcategoryResult

/**
 * Estado da ScheduleScreen
 */
data class ScheduleState(
    // Dados carregados
    val categories: List<CategoryResult> = emptyList(),
    val subcategories: List<SubcategoryResult> = emptyList(),
    
    // Seleções do formulário
    val selectedCategoryId: Int? = null,
    val selectedSubcategoryId: Int? = null,
    val selectedDayOfWeekId: Int? = null,
    val selectedStartTime: String? = null,
    val selectedEndTime: String? = null,
    
    // Lista de horários adicionados
    val scheduleItems: List<ScheduleItemResult> = emptyList(),
    
    // Estados de UI
    val isLoadingCategories: Boolean = false,
    val isLoadingSubcategories: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null
) {
    /**
     * Verifica se o botão "Adicionar" deve estar habilitado
     */
    val isAddButtonEnabled: Boolean
        get() = selectedCategoryId != null &&
                selectedSubcategoryId != null &&
                selectedDayOfWeekId != null &&
                selectedStartTime != null &&
                selectedEndTime != null

    /**
     * Verifica se o botão "Salvar Agenda" deve estar habilitado
     */
    val isSaveButtonEnabled: Boolean
        get() = scheduleItems.isNotEmpty() && !isSaving
}
