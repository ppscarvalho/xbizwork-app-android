package com.br.xbizitwork.ui.presentation.features.schedule.create.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.data.local.auth.datastore.AuthSessionLocalDataSource
import com.br.xbizitwork.data.remote.schedule.dtos.requests.CreateScheduleRequest
import com.br.xbizitwork.domain.usecase.category.GetCategoriesUseCase
import com.br.xbizitwork.domain.usecase.schedule.CreateScheduleFromRequestUseCase
import com.br.xbizitwork.domain.usecase.schedule.ValidateScheduleOnBackendUseCase
import com.br.xbizitwork.domain.usecase.schedule.ValidateScheduleTimeSlotUseCase
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
    private val createScheduleFromRequestUseCase: CreateScheduleFromRequestUseCase,
    private val validateScheduleOnBackendUseCase: ValidateScheduleOnBackendUseCase,
    private val validateScheduleTimeSlotUseCase: ValidateScheduleTimeSlotUseCase,
    private val authSessionLocalDataSource: AuthSessionLocalDataSource
) : ViewModel() {

    private val _uiState: MutableStateFlow<CreateScheduleUIState> = MutableStateFlow(CreateScheduleUIState())
    val uiState: StateFlow<CreateScheduleUIState> = _uiState.asStateFlow()

    private val _appSideEffectChannel = Channel<AppSideEffect>()
    val sideEffectChannel = _appSideEffectChannel.receiveAsFlow()

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
            getCategoriesUseCase().collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoadingCategories = true) }
                },
                onSuccess = { categories ->
                    _uiState.update {
                        it.copy(
                            categories = categories,
                            isLoadingCategories = false
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoadingCategories = false,
                            errorMessage = error.message ?: "Erro ao carregar categorias"
                        )
                    }
                }
            )
        }
    }

    private fun loadSpecialties(categoryId: Int) {
        viewModelScope.launch {
            getSpecialtiesByCategoryUseCase(categoryId).collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoadingSpecialties = true) }
                },
                onSuccess = { specialties ->
                    _uiState.update {
                        it.copy(
                            specialties = specialties,
                            isLoadingSpecialties = false
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoadingSpecialties = false,
                            errorMessage = error.message ?: "Erro ao carregar especialidades"
                        )
                    }
                }
            )
        }
    }

    private fun onCategorySelected(categoryId: Int, categoryName: String) {
        _uiState.update {
            it.copy(
                selectedCategoryId = categoryId,
                selectedCategoryName = categoryName,
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

        // Validar horário usando o UseCase
        val validationResult = validateScheduleTimeSlotUseCase(
            ValidateScheduleTimeSlotUseCase.Parameters(
                startTime = state.startTime,
                endTime = state.endTime,
                categoryId = state.selectedCategoryId!!,
                specialtyId = state.selectedSpecialtyId!!,
                weekDay = state.selectedWeekDay!!,
                existingSlots = state.scheduleTimeSlots
            )
        )

        // Se a validação local falhou, mostrar erro e retornar
        if (validationResult is ValidateScheduleTimeSlotUseCase.ValidationResult.Invalid) {
            viewModelScope.launch {
                _appSideEffectChannel.send(
                    AppSideEffect.ShowToast(validationResult.message)
                )
            }
            return
        }

        // Validação local passou, agora validar no backend
        viewModelScope.launch {
            val session = authSessionLocalDataSource.getSession()
            val userId = session?.id ?: run {
                _appSideEffectChannel.send(AppSideEffect.ShowToast("❌ Usuário não autenticado"))
                return@launch
            }

            val request = CreateScheduleRequest(
                userId = userId,
                categoryId = state.selectedCategoryId!!,
                specialtyId = state.selectedSpecialtyId!!,
                weekDays = listOf(state.selectedWeekDay!!),
                startTime = state.startTime,
                endTime = state.endTime,
                status = true
            )

            validateScheduleOnBackendUseCase(
                ValidateScheduleOnBackendUseCase.Parameters(request)
            ).collectUiState(
                onLoading = {
                    // Validando no backend
                },
                onSuccess = { result ->
                    // Verificar se a validação passou (isSuccessful = true)
                    if (result.isSuccessful) {
                        // ✅ Validação OK - pode adicionar
                        val newSlot = ScheduleTimeSlot(
                            id = UUID.randomUUID().toString(),
                            categoryId = state.selectedCategoryId,
                            categoryName = state.selectedCategoryName,
                            specialtyId = state.selectedSpecialtyId,
                            specialtyName = state.selectedSpecialtyName,
                            weekDay = state.selectedWeekDay,
                            weekDayName = state.selectedWeekDayName,
                            startTime = state.startTime,
                            endTime = state.endTime
                        )

                        _uiState.update {
                            it.copy(
                                scheduleTimeSlots = it.scheduleTimeSlots + newSlot
                            ).validateCanSave()
                        }

                        _appSideEffectChannel.send(AppSideEffect.ShowToast("✅ Horário adicionado!"))
                    } else {
                        // ❌ Validação falhou - já existe agenda
                        _appSideEffectChannel.send(
                            AppSideEffect.ShowToast("❌ ${result.message}")
                        )
                    }
                },
                onFailure = { error ->
                    _appSideEffectChannel.send(
                        AppSideEffect.ShowToast("❌ ${error.message}")
                    )
                }
            )
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
            val session = authSessionLocalDataSource.getSession()
            val userId = session?.id ?: run {
                _appSideEffectChannel.send(AppSideEffect.ShowToast("❌ Usuário não autenticado"))
                return@launch
            }

            _uiState.update { it.copy(isLoading = true) }

            var hasError = false
            var errorMessage = ""

            // Processar slots SEQUENCIALMENTE para evitar condição de corrida
            for (slot in state.scheduleTimeSlots) {
                if (hasError) break

                val request = CreateScheduleRequest(
                    userId = userId,
                    categoryId = slot.categoryId,
                    specialtyId = slot.specialtyId,
                    weekDays = listOf(slot.weekDay),
                    startTime = slot.startTime,
                    endTime = slot.endTime,
                    status = true
                )

                createScheduleFromRequestUseCase(
                    CreateScheduleFromRequestUseCase.Parameters(request)
                ).collectUiState(
                    onLoading = {
                        // Já está em loading
                    },
                    onSuccess = { result ->
                        // Sucesso - continua para próximo slot
                    },
                    onFailure = { error ->
                        hasError = true
                        errorMessage = error.message ?: "Erro ao criar agenda"
                    }
                )
            }

            if (hasError) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = errorMessage
                    )
                }
            } else {
                // Todas as agendas foram criadas com sucesso
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true,
                        scheduleTimeSlots = emptyList()
                    )
                }
                _appSideEffectChannel.send(AppSideEffect.ShowToast("✅ Agendas criadas com sucesso!"))
                _appSideEffectChannel.send(AppSideEffect.NavigateBack)
            }
        }
    }

    private fun dismissError() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}

