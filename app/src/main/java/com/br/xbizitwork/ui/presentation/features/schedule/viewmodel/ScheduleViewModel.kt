package com.br.xbizitwork.ui.presentation.features.schedule.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.domain.model.DayOfWeek
import com.br.xbizitwork.domain.result.schedule.ScheduleItemResult
import com.br.xbizitwork.domain.usecase.category.GetAllCategoryUseCase
import com.br.xbizitwork.domain.usecase.schedule.SaveScheduleUseCase
import com.br.xbizitwork.domain.usecase.subcategory.GetSubcategoriesByCategoryUseCase
import com.br.xbizitwork.ui.presentation.features.schedule.events.ScheduleEvent
import com.br.xbizitwork.ui.presentation.features.schedule.state.ScheduleState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para gerenciar o estado da tela de agendamento
 */
@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val getAllCategoryUseCase: GetAllCategoryUseCase,
    private val getSubcategoriesByCategoryUseCase: GetSubcategoriesByCategoryUseCase,
    private val saveScheduleUseCase: SaveScheduleUseCase
) : ViewModel() {

    // ============= STATE MANAGEMENT =============
    private val _uiState: MutableStateFlow<ScheduleState> = MutableStateFlow(ScheduleState())
    val uiState: StateFlow<ScheduleState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ScheduleState()
    )

    // ============= SIDE EFFECTS =============
    private val _sideEffectChannel = Channel<SideEffect>(capacity = Channel.Factory.BUFFERED)
    val sideEffectChannel = _sideEffectChannel.receiveAsFlow()

    // ============= INIT =============
    init {
        loadCategories()
    }

    // ============= PUBLIC FUNCTIONS =============
    fun onEvent(event: ScheduleEvent) {
        when (event) {
            is ScheduleEvent.OnCategorySelected -> {
                _uiState.update {
                    it.copy(
                        selectedCategoryId = event.categoryId,
                        selectedSubcategoryId = null, // Reset subcategory
                        subcategories = emptyList()
                    )
                }
                loadSubcategories(event.categoryId)
            }

            is ScheduleEvent.OnSubcategorySelected -> {
                _uiState.update { it.copy(selectedSubcategoryId = event.subcategoryId) }
            }

            is ScheduleEvent.OnDayOfWeekSelected -> {
                _uiState.update { it.copy(selectedDayOfWeekId = event.dayOfWeekId) }
            }

            is ScheduleEvent.OnStartTimeSelected -> {
                _uiState.update { it.copy(selectedStartTime = event.startTime) }
            }

            is ScheduleEvent.OnEndTimeSelected -> {
                _uiState.update { it.copy(selectedEndTime = event.endTime) }
            }

            is ScheduleEvent.OnAddScheduleItem -> {
                addScheduleItem()
            }

            is ScheduleEvent.OnRemoveScheduleItem -> {
                removeScheduleItem(
                    event.categoryId,
                    event.subcategoryId,
                    event.dayOfWeekId,
                    event.startTime
                )
            }

            is ScheduleEvent.OnSaveSchedule -> {
                saveSchedule()
            }

            is ScheduleEvent.OnScreenLoad -> {
                loadCategories()
            }
        }
    }

    // ============= PRIVATE FUNCTIONS =============
    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingCategories = true, errorMessage = null) }
            
            getAllCategoryUseCase().collectUiState(
                onSuccess = { categories ->
                    _uiState.update {
                        it.copy(
                            categories = categories,
                            isLoadingCategories = false
                        )
                    }
                },
                onError = { error ->
                    _uiState.update {
                        it.copy(
                            isLoadingCategories = false,
                            errorMessage = error.message ?: "Erro ao carregar categorias"
                        )
                    }
                    _sideEffectChannel.send(
                        SideEffect.ShowToast(error.message ?: "Erro ao carregar categorias")
                    )
                },
                onLoading = {
                    _uiState.update { it.copy(isLoadingCategories = true) }
                }
            )
        }
    }

    private fun loadSubcategories(categoryId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingSubcategories = true, errorMessage = null) }
            
            getSubcategoriesByCategoryUseCase(categoryId).collectUiState(
                onSuccess = { subcategories ->
                    _uiState.update {
                        it.copy(
                            subcategories = subcategories,
                            isLoadingSubcategories = false
                        )
                    }
                },
                onError = { error ->
                    _uiState.update {
                        it.copy(
                            isLoadingSubcategories = false,
                            errorMessage = error.message ?: "Erro ao carregar subcategorias"
                        )
                    }
                    _sideEffectChannel.send(
                        SideEffect.ShowToast(error.message ?: "Erro ao carregar subcategorias")
                    )
                },
                onLoading = {
                    _uiState.update { it.copy(isLoadingSubcategories = true) }
                }
            )
        }
    }

    private fun addScheduleItem() {
        val state = _uiState.value
        
        // Validação de campos
        if (!state.isAddButtonEnabled) {
            viewModelScope.launch {
                _sideEffectChannel.send(SideEffect.ShowToast("Preencha todos os campos"))
            }
            return
        }

        val categoryId = state.selectedCategoryId!!
        val subcategoryId = state.selectedSubcategoryId!!
        val dayOfWeekId = state.selectedDayOfWeekId!!
        val startTime = state.selectedStartTime!!
        val endTime = state.selectedEndTime!!

        // Validação de duplicação
        val isDuplicate = state.scheduleItems.any {
            it.categoryId == categoryId &&
            it.subcategoryId == subcategoryId &&
            it.dayOfWeekId == dayOfWeekId &&
            it.startTime == startTime
        }

        if (isDuplicate) {
            viewModelScope.launch {
                _sideEffectChannel.send(
                    SideEffect.ShowToast("Este horário já foi adicionado")
                )
            }
            return
        }

        // Busca nomes para exibição
        val category = state.categories.find { it.id == categoryId }
        val subcategory = state.subcategories.find { it.id == subcategoryId }
        val dayOfWeek = DayOfWeek.fromId(dayOfWeekId)

        if (category == null || subcategory == null || dayOfWeek == null) {
            viewModelScope.launch {
                _sideEffectChannel.send(SideEffect.ShowToast("Erro ao adicionar horário"))
            }
            return
        }

        val newItem = ScheduleItemResult(
            categoryId = categoryId,
            categoryName = category.description,
            subcategoryId = subcategoryId,
            subcategoryName = subcategory.description,
            dayOfWeekId = dayOfWeekId,
            dayOfWeekName = dayOfWeek.displayName,
            startTime = startTime,
            endTime = endTime
        )

        _uiState.update {
            it.copy(
                scheduleItems = it.scheduleItems + newItem,
                // Reset form fields
                selectedDayOfWeekId = null,
                selectedStartTime = null,
                selectedEndTime = null
            )
        }

        viewModelScope.launch {
            _sideEffectChannel.send(SideEffect.ShowToast("Horário adicionado com sucesso"))
        }
    }

    private fun removeScheduleItem(
        categoryId: Int,
        subcategoryId: Int,
        dayOfWeekId: Int,
        startTime: String
    ) {
        _uiState.update {
            it.copy(
                scheduleItems = it.scheduleItems.filter { item ->
                    !(item.categoryId == categoryId &&
                      item.subcategoryId == subcategoryId &&
                      item.dayOfWeekId == dayOfWeekId &&
                      item.startTime == startTime)
                }
            )
        }
    }

    private fun saveSchedule() {
        val state = _uiState.value
        
        if (state.scheduleItems.isEmpty()) {
            viewModelScope.launch {
                _sideEffectChannel.send(
                    SideEffect.ShowToast("Adicione pelo menos um horário")
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, errorMessage = null, successMessage = null) }
            
            saveScheduleUseCase(state.scheduleItems).collectUiState(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            successMessage = "Agenda salva com sucesso!",
                            scheduleItems = emptyList() // Limpa a lista após salvar
                        )
                    }
                    _sideEffectChannel.send(SideEffect.ShowToast("Agenda salva com sucesso!"))
                    _sideEffectChannel.send(SideEffect.NavigateBack)
                },
                onError = { error ->
                    _uiState.update {
                        it.copy(
                            isSaving = false,
                            errorMessage = error.message ?: "Erro ao salvar agenda"
                        )
                    }
                    _sideEffectChannel.send(
                        SideEffect.ShowToast(error.message ?: "Erro ao salvar agenda")
                    )
                },
                onLoading = {
                    _uiState.update { it.copy(isSaving = true) }
                }
            )
        }
    }
}
