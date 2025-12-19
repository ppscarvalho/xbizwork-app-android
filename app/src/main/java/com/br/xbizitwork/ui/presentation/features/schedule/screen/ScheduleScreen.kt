package com.br.xbizitwork.ui.presentation.features.schedule.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.domain.model.DayOfWeek
import com.br.xbizitwork.domain.model.TimeSlot
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.components.inputs.AppDropdownField
import com.br.xbizitwork.ui.presentation.components.inputs.DropdownOption
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.schedule.components.ScheduleGroupCard
import com.br.xbizitwork.ui.presentation.features.schedule.events.ScheduleEvent
import com.br.xbizitwork.ui.presentation.features.schedule.viewmodel.ScheduleViewModel

@Composable
fun ScheduleScreen(
    onNavigateUp: () -> Unit,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.uiState.collectAsState()

    // Tratar SideEffects
    LifecycleEventEffect(viewModel.sideEffectChannel) { sideEffect ->
        when (sideEffect) {
            is SideEffect.ShowToast -> context.toast(sideEffect.message)
            is SideEffect.NavigateBack -> onNavigateUp()
            else -> {}
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Criar Agenda",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = onNavigateUp
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (state.isLoadingCategories) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    ScheduleContent(
                        state = state,
                        onEvent = viewModel::onEvent
                    )
                }
            }
        }
    )
}

@Composable
private fun ScheduleContent(
    state: com.br.xbizitwork.ui.presentation.features.schedule.state.ScheduleState,
    onEvent: (ScheduleEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }

        // Formulário de adição
        item {
            ScheduleForm(
                state = state,
                onEvent = onEvent
            )
        }

        // Lista de horários agrupados
        item {
            if (state.scheduleItems.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Agrupar por categoria + subcategoria
        val groupedItems = state.scheduleItems.groupBy { 
            Pair(it.categoryId, it.subcategoryId) 
        }

        items(groupedItems.toList()) { (key, items) ->
            val firstItem = items.first()
            ScheduleGroupCard(
                categoryName = firstItem.categoryName,
                subcategoryName = firstItem.subcategoryName,
                items = items,
                onRemoveItem = { item ->
                    onEvent(
                        ScheduleEvent.OnRemoveScheduleItem(
                            categoryId = item.categoryId,
                            subcategoryId = item.subcategoryId,
                            dayOfWeekId = item.dayOfWeekId,
                            startTime = item.startTime
                        )
                    )
                }
            )
        }

        // Botão Salvar Agenda
        item {
            if (state.scheduleItems.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                
                AppButton(
                    text = if (state.isSaving) "Salvando..." else "Salvar Agenda",
                    onClick = { onEvent(ScheduleEvent.OnSaveSchedule) },
                    enabled = state.isSaveButtonEnabled,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }
    }
}

@Composable
private fun ScheduleForm(
    state: com.br.xbizitwork.ui.presentation.features.schedule.state.ScheduleState,
    onEvent: (ScheduleEvent) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Categoria
        AppDropdownField(
            label = "Categoria",
            placeholder = "Selecione uma categoria",
            selectedValue = state.selectedCategoryId?.toString() ?: "",
            options = state.categories.map { 
                DropdownOption(
                    value = it.id.toString(),
                    displayText = it.description
                )
            },
            onValueChange = { value ->
                value.toIntOrNull()?.let { categoryId ->
                    onEvent(ScheduleEvent.OnCategorySelected(categoryId))
                }
            }
        )

        // Subcategoria
        AppDropdownField(
            label = "Subcategoria",
            placeholder = if (state.selectedCategoryId == null) {
                "Selecione uma categoria primeiro"
            } else if (state.isLoadingSubcategories) {
                "Carregando..."
            } else if (state.subcategories.isEmpty()) {
                "Nenhuma subcategoria disponível"
            } else {
                "Selecione uma subcategoria"
            },
            selectedValue = state.selectedSubcategoryId?.toString() ?: "",
            options = state.subcategories.map {
                DropdownOption(
                    value = it.id.toString(),
                    displayText = it.description
                )
            },
            onValueChange = { value ->
                value.toIntOrNull()?.let { subcategoryId ->
                    onEvent(ScheduleEvent.OnSubcategorySelected(subcategoryId))
                }
            }
        )

        // Dia da semana
        AppDropdownField(
            label = "Dia da Semana",
            placeholder = "Selecione um dia",
            selectedValue = state.selectedDayOfWeekId?.toString() ?: "",
            options = DayOfWeek.entries.map {
                DropdownOption(
                    value = it.id.toString(),
                    displayText = it.displayName
                )
            },
            onValueChange = { value ->
                value.toIntOrNull()?.let { dayId ->
                    onEvent(ScheduleEvent.OnDayOfWeekSelected(dayId))
                }
            }
        )

        // Horários
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Hora Início
            AppDropdownField(
                modifier = Modifier.weight(1f),
                label = "Hora Início",
                placeholder = "Selecione",
                selectedValue = state.selectedStartTime ?: "",
                options = TimeSlot.entries.map {
                    DropdownOption(
                        value = it.hour,
                        displayText = it.hour
                    )
                },
                onValueChange = { value ->
                    onEvent(ScheduleEvent.OnStartTimeSelected(value))
                }
            )

            // Hora Fim
            AppDropdownField(
                modifier = Modifier.weight(1f),
                label = "Hora Fim",
                placeholder = "Selecione",
                selectedValue = state.selectedEndTime ?: "",
                options = TimeSlot.entries.map {
                    DropdownOption(
                        value = it.hour,
                        displayText = it.hour
                    )
                },
                onValueChange = { value ->
                    onEvent(ScheduleEvent.OnEndTimeSelected(value))
                }
            )
        }

        // Botão Adicionar
        AppButton(
            text = "Adicionar",
            onClick = { onEvent(ScheduleEvent.OnAddScheduleItem) },
            enabled = state.isAddButtonEnabled,
            modifier = Modifier.fillMaxWidth()
        )
    }
}
