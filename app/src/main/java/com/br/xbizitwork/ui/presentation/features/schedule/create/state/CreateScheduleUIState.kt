package com.br.xbizitwork.ui.presentation.features.schedule.create.state

import com.br.xbizitwork.domain.model.schedule.DayOfWeek
import com.br.xbizitwork.domain.result.category.CategoryResult
import com.br.xbizitwork.domain.result.specialty.SpecialtyResult
import java.time.LocalTime

/**
 * Representa um horário temporário antes de ser salvo
 */
data class ScheduleTimeSlot(
    val id: String,
    val categoryId: Int,
    val categoryName: String,
    val specialtyId: Int,
    val specialtyName: String,
    val weekDay: Int,
    val weekDayName: String,
    val startTime: String,
    val endTime: String
)

/**
 * Estado da tela de criar agenda - Novo fluxo UX
 */
data class CreateScheduleUIState(
    // Dropdown de Categorias
    val categories: List<CategoryResult> = emptyList(),
    val selectedCategoryId: Int? = null,
    val selectedCategoryName: String = "",
    val isLoadingCategories: Boolean = false,
    
    // Dropdown de Especialidades
    val specialties: List<SpecialtyResult> = emptyList(),
    val selectedSpecialtyId: Int? = null,
    val selectedSpecialtyName: String = "",
    val isLoadingSpecialties: Boolean = false,
    
    // Seleção de dia da semana
    val selectedWeekDay: Int? = null,
    val selectedWeekDayName: String = "",
    
    // Horários
    val startTime: String = "08:00",
    val endTime: String = "18:00",
    
    // Lista de horários adicionados (antes de salvar)
    val scheduleTimeSlots: List<ScheduleTimeSlot> = emptyList(),
    
    // Estados da UI
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    
    // Validação
    val canAddTimeSlot: Boolean = false,
    val canSaveSchedule: Boolean = false
) {
    /**
     * Valida se pode adicionar um horário na lista
     */
    fun validateCanAdd(): CreateScheduleUIState {
        val hasCategory = selectedCategoryId != null
        val hasSpecialty = selectedSpecialtyId != null
        val hasWeekDay = selectedWeekDay != null
        val hasValidTimes = startTime.isNotBlank() && endTime.isNotBlank()
        
        return copy(
            canAddTimeSlot = hasCategory && hasSpecialty && hasWeekDay && hasValidTimes
        )
    }
    
    /**
     * Valida se pode salvar a agenda
     */
    fun validateCanSave(): CreateScheduleUIState {
        return copy(
            canSaveSchedule = scheduleTimeSlots.isNotEmpty()
        )
    }
}

