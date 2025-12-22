package com.br.xbizitwork.ui.presentation.features.schedule.create.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.data.remote.schedule.dtos.requests.CreateScheduleRequest
import com.br.xbizitwork.domain.usecase.category.GetCategoriesUseCase
import com.br.xbizitwork.domain.usecase.schedule.CreateScheduleFromRequestUseCase
import com.br.xbizitwork.domain.usecase.specialty.GetSpecialtiesByCategoryUseCase
import com.br.xbizitwork.ui.presentation.features.schedule.create.events.CreateScheduleEvent
import com.br.xbizitwork.ui.presentation.features.schedule.create.state.CreateScheduleUIState
import com.br.xbizitwork.ui.presentation.features.schedule.create.state.ScheduleTimeSlot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateScheduleViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getSpecialtiesByCategoryUseCase: GetSpecialtiesByCategoryUseCase,
    private val createScheduleFromRequestUseCase: CreateScheduleFromRequestUseCase
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(CreateScheduleUIState())
    val uiState: StateFlow<CreateScheduleUIState> = _uiState.asStateFlow()
    
    private val _App_sideEffectChannel = Channel<AppSideEffect>()
    val sideEffectChannel = _App_sideEffectChannel.receiveAsFlow()
    
    init {
        loadCategories()
    }
    
    fun onEvent(event: CreateScheduleEvent) {
        when (event) {
            CreateScheduleEvent.OnLoadCategories -> loadCategories()
            is CreateScheduleEvent.OnLoadSpecialties -> loadSpecialties(event.categoryId)
            
            is CreateScheduleEvent.OnCategorySelected -> onCategorySelected(event.categoryId, event.categoryName)
            is CreateScheduleEvent.OnSpecialtySelected -> onSpecialtySelected(event.specialtyId, event.specialtyName)
            is CreateScheduleEvent.OnWeekDaySelected -> onWeekDaySelected(event.weekDay, event.weekDayName)
            is CreateScheduleEvent.OnStartTimeChanged -> onStartTimeChanged(event.time)
            is CreateScheduleEvent.OnEndTimeChanged -> onEndTimeChanged(event.time)
            
            CreateScheduleEvent.OnAddTimeSlot -> addTimeSlot()
            is CreateScheduleEvent.OnRemoveTimeSlot -> removeTimeSlot(event.slotId)
            CreateScheduleEvent.OnSaveSchedule -> saveSchedule()
            
            CreateScheduleEvent.OnDismissError -> dismissError()
        }
    }
    
    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingCategories = true) }
            
            when (val result = getCategoriesUseCase()) {
                is DefaultResult.Success -> {
                    _uiState.update {
                        it.copy(
                            categories = result.data,
                            isLoadingCategories = false
                        )
                    }
                }
                is DefaultResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoadingCategories = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }
    
    private fun loadSpecialties(categoryId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingSpecialties = true) }
            
            when (val result = getSpecialtiesByCategoryUseCase(categoryId)) {
                is DefaultResult.Success -> {
                    _uiState.update {
                        it.copy(
                            specialties = result.data,
                            isLoadingSpecialties = false
                        )
                    }
                }
                is DefaultResult.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoadingSpecialties = false,
                            errorMessage = result.message
                        )
                    }
                }
            }
        }
    }
    
    private fun onCategorySelected(categoryId: Int, categoryName: String) {
        _uiState.update {
            it.copy(
                selectedCategoryId = categoryId,
                selectedCategoryName = categoryName,
                // Limpa especialidade ao trocar categoria
                selectedSpecialtyId = null,
                selectedSpecialtyName = "",
                specialties = emptyList()
            ).validateCanAdd()
        }
        loadSpecialties(categoryId)
    }
    
    private fun onSpecialtySelected(specialtyId: Int, specialtyName: String) {
        _uiState.update {
            it.copy(
                selectedSpecialtyId = specialtyId,
                selectedSpecialtyName = specialtyName
            ).validateCanAdd()
        }
    }
    
    private fun onWeekDaySelected(weekDay: Int, weekDayName: String) {
        _uiState.update {
            it.copy(
                selectedWeekDay = weekDay,
                selectedWeekDayName = weekDayName
            ).validateCanAdd()
        }
    }
    
    private fun onStartTimeChanged(time: String) {
        _uiState.update {
            it.copy(startTime = time).validateCanAdd()
        }
    }
    
    private fun onEndTimeChanged(time: String) {
        _uiState.update {
            it.copy(endTime = time).validateCanAdd()
        }
    }
    
    private fun addTimeSlot() {
        val state = _uiState.value
        
        if (!state.canAddTimeSlot) return
        
        // ========================================
        // VALIDAÇÃO 1: Hora final > Hora inicial
        // ========================================
        val startHour = state.startTime.substringBefore(":").toIntOrNull() ?: 0
        val startMinute = state.startTime.substringAfter(":").toIntOrNull() ?: 0
        val endHour = state.endTime.substringBefore(":").toIntOrNull() ?: 0
        val endMinute = state.endTime.substringAfter(":").toIntOrNull() ?: 0

        val startTimeInMinutes = startHour * 60 + startMinute
        val endTimeInMinutes = endHour * 60 + endMinute

        if (endTimeInMinutes <= startTimeInMinutes) {
            viewModelScope.launch {
                _App_sideEffectChannel.send(
                    AppSideEffect.ShowToast("❌ Hora final deve ser maior que hora inicial!")
                )
            }
            return
        }

        // ========================================
        // VALIDAÇÃO 2: Horário duplicado
        // ========================================
        val isDuplicate = state.scheduleTimeSlots.any { slot ->
            slot.categoryId == state.selectedCategoryId &&
            slot.specialtyId == state.selectedSpecialtyId &&
            slot.weekDay == state.selectedWeekDay &&
            slot.startTime == state.startTime &&
            slot.endTime == state.endTime
        }

        if (isDuplicate) {
            viewModelScope.launch {
                _App_sideEffectChannel.send(
                    AppSideEffect.ShowToast("❌ Este horário já foi adicionado!")
                )
            }
            return
        }

        // ========================================
        // VALIDAÇÃO 3: Sobreposição de horários E horários sequenciais
        // ========================================
        val hasOverlapOrSequential = state.scheduleTimeSlots.any { slot ->
            // Mesma categoria, especialidade e dia da semana
            if (slot.categoryId == state.selectedCategoryId &&
                slot.specialtyId == state.selectedSpecialtyId &&
                slot.weekDay == state.selectedWeekDay) {

                val slotStartMinutes = slot.startTime.substringBefore(":").toInt() * 60 +
                                     slot.startTime.substringAfter(":").toInt()
                val slotEndMinutes = slot.endTime.substringBefore(":").toInt() * 60 +
                                   slot.endTime.substringAfter(":").toInt()

                // Verifica sobreposição
                val startsInside = startTimeInMinutes >= slotStartMinutes &&
                                 startTimeInMinutes < slotEndMinutes
                val endsInside = endTimeInMinutes > slotStartMinutes &&
                               endTimeInMinutes <= slotEndMinutes
                val encompasses = startTimeInMinutes <= slotStartMinutes &&
                                endTimeInMinutes >= slotEndMinutes

                // ✅ NOVA REGRA: Bloquear horários sequenciais (sem intervalo)
                val isSequentialStart = startTimeInMinutes == slotEndMinutes // Novo começa quando antigo termina
                val isSequentialEnd = endTimeInMinutes == slotStartMinutes   // Novo termina quando antigo começa

                startsInside || endsInside || encompasses || isSequentialStart || isSequentialEnd
            } else {
                false
            }
        }

        if (hasOverlapOrSequential) {
            viewModelScope.launch {
                _App_sideEffectChannel.send(
                    AppSideEffect.ShowToast("❌ Horários devem ter intervalo entre eles!")
                )
            }
            return
        }

        // ========================================
        // ✅ VALIDAÇÕES OK - Adicionar horário
        // ========================================
        val newSlot = ScheduleTimeSlot(
            id = UUID.randomUUID().toString(),
            categoryId = state.selectedCategoryId!!,
            categoryName = state.selectedCategoryName,
            specialtyId = state.selectedSpecialtyId!!,
            specialtyName = state.selectedSpecialtyName,
            weekDay = state.selectedWeekDay!!,
            weekDayName = state.selectedWeekDayName,
            startTime = state.startTime,
            endTime = state.endTime
        )
        
        _uiState.update {
            it.copy(
                scheduleTimeSlots = it.scheduleTimeSlots + newSlot
            ).validateCanSave()
        }
        
        viewModelScope.launch {
            _App_sideEffectChannel.send(AppSideEffect.ShowToast("✅ Horário adicionado!"))
        }
    }
    
    private fun removeTimeSlot(slotId: String) {
        _uiState.update {
            it.copy(
                scheduleTimeSlots = it.scheduleTimeSlots.filter { slot -> slot.id != slotId }
            ).validateCanSave()
        }
    }
    
    private fun saveSchedule() {
        val state = _uiState.value
        
        if (!state.canSaveSchedule) return
        
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            
            try {
                // TODO: Obter userId do usuário logado
                val userId = 14 // Temporário
                
                // Fazer uma chamada para cada time slot
                var hasErrors = false
                
                state.scheduleTimeSlots.forEach { slot ->
                    val request = CreateScheduleRequest(
                        userId = userId,
                        categoryId = slot.categoryId,
                        specialtyId = slot.specialtyId,
                        weekDays = listOf(slot.weekDay),
                        startTime = slot.startTime,
                        endTime = slot.endTime,
                        status = true
                    )
                    
                    when (val result = createScheduleFromRequestUseCase(request)) {
                        is DefaultResult.Success -> {
                            // Sucesso
                        }
                        is DefaultResult.Error -> {
                            hasErrors = true
                        }
                    }
                }
                
                if (hasErrors) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Erro ao salvar alguns horários"
                        )
                    }
                } else {
                    // ✅ Limpar lista temporária após salvar com sucesso
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = true,
                            scheduleTimeSlots = emptyList() // ← Limpa a lista
                        )
                    }
                    _App_sideEffectChannel.send(AppSideEffect.ShowToast("✅ Agenda criada com sucesso!"))
                    _App_sideEffectChannel.send(AppSideEffect.NavigateBack)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Erro ao criar agenda"
                    )
                }
            }
        }
    }
    
    private fun dismissError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
