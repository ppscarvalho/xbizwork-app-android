package com.br.xbizitwork.ui.presentation.features.schedule.create.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.components.inputs.AppTextField
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.schedule.create.events.CreateScheduleEvent
import com.br.xbizitwork.ui.presentation.features.schedule.create.state.ScheduleTimeSlot
import com.br.xbizitwork.ui.presentation.features.schedule.create.viewmodel.CreateScheduleViewModel

@Composable
fun CreateScheduleScreen(
    onNavigateBack: () -> Unit,
    viewModel: CreateScheduleViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Side Effects
    LaunchedEffect(Unit) {
        viewModel.sideEffectChannel.collect { sideEffect ->
            when (sideEffect) {
                is SideEffect.ShowToast -> {
                    snackbarHostState.showSnackbar(sideEffect.message)
                }
                SideEffect.NavigateBack -> onNavigateBack()
                else -> {}
            }
        }
    }
    
    Scaffold(
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Criar Agenda",
                enableNavigationUp = true,
                onNavigationIconButton = onNavigateBack
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Seção: Adicionar Horário
            Text(
                text = "Adicionar Horário",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            // Dropdown Categoria
            CategoryDropdown(
                categories = uiState.categories,
                selectedCategoryName = uiState.selectedCategoryName,
                isLoading = uiState.isLoadingCategories,
                onCategorySelected = { id, name ->
                    viewModel.onEvent(CreateScheduleEvent.OnCategorySelected(id, name))
                }
            )
            
            // Dropdown Especialidade
            if (uiState.selectedCategoryId != null) {
                SpecialtyDropdown(
                    specialties = uiState.specialties,
                    selectedSpecialtyName = uiState.selectedSpecialtyName,
                    isLoading = uiState.isLoadingSpecialties,
                    onSpecialtySelected = { id, name ->
                        viewModel.onEvent(CreateScheduleEvent.OnSpecialtySelected(id, name))
                    }
                )
            }
            
            // Dropdown Dia da Semana
            WeekDayDropdown(
                selectedWeekDayName = uiState.selectedWeekDayName,
                onWeekDaySelected = { weekDay, name ->
                    viewModel.onEvent(CreateScheduleEvent.OnWeekDaySelected(weekDay, name))
                }
            )
            
            // Horários
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TimeDropdown(
                    modifier = Modifier.weight(1f),
                    label = "Início",
                    selectedTime = uiState.startTime,
                    onTimeSelected = { viewModel.onEvent(CreateScheduleEvent.OnStartTimeChanged(it)) }
                )
                
                TimeDropdown(
                    modifier = Modifier.weight(1f),
                    label = "Fim",
                    selectedTime = uiState.endTime,
                    onTimeSelected = { viewModel.onEvent(CreateScheduleEvent.OnEndTimeChanged(it)) }
                )
            }
            
            // Botão Adicionar
            OutlinedButton(
                onClick = { viewModel.onEvent(CreateScheduleEvent.OnAddTimeSlot) },
                enabled = uiState.canAddTimeSlot,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Adicionar à Lista")
            }
            
            // Seção: Horários Adicionados
            if (uiState.scheduleTimeSlots.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Horários Adicionados (${uiState.scheduleTimeSlots.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                uiState.scheduleTimeSlots.forEach { slot ->
                    ScheduleTimeSlotCard(
                        slot = slot,
                        onRemove = { viewModel.onEvent(CreateScheduleEvent.OnRemoveTimeSlot(slot.id)) }
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Botão Salvar Agenda
                AppButton(
                    text = "Salvar Agenda",
                    isLoading = uiState.isLoading,
                    contentColor = Color.White,
                    enabled = uiState.canSaveSchedule && !uiState.isLoading,
                    onClick = { viewModel.onEvent(CreateScheduleEvent.OnSaveSchedule) }
                )
            }
        }
    }
}

@Composable
private fun CategoryDropdown(
    categories: List<com.br.xbizitwork.domain.result.category.CategoryResult>,
    selectedCategoryName: String,
    isLoading: Boolean,
    onCategorySelected: (Int, String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    Column {
        Text(
            text = "Categoria",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        
        Box {
            OutlinedButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && categories.isNotEmpty()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedCategoryName.ifBlank { "Selecione uma categoria" },
                        modifier = Modifier.weight(1f)
                    )
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                        )
                    } else {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            }
            
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.description) },
                        onClick = {
                            onCategorySelected(category.id, category.description)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SpecialtyDropdown(
    specialties: List<com.br.xbizitwork.domain.result.specialty.SpecialtyResult>,
    selectedSpecialtyName: String,
    isLoading: Boolean,
    onSpecialtySelected: (Int, String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    Column {
        Text(
            text = "Especialidade",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        
        Box {
            OutlinedButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading && specialties.isNotEmpty()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedSpecialtyName.ifBlank { "Selecione uma especialidade" },
                        modifier = Modifier.weight(1f)
                    )
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .width(20.dp)
                                .height(20.dp)
                        )
                    } else {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
            }
            
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                specialties.forEach { specialty ->
                    DropdownMenuItem(
                        text = { Text(specialty.description) },
                        onClick = {
                            onSpecialtySelected(specialty.id, specialty.description)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun WeekDayDropdown(
    selectedWeekDayName: String,
    onWeekDaySelected: (Int, String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    val weekDays = listOf(
        0 to "Domingo",
        1 to "Segunda-feira",
        2 to "Terça-feira",
        3 to "Quarta-feira",
        4 to "Quinta-feira",
        5 to "Sexta-feira",
        6 to "Sábado"
    )
    
    Column {
        Text(
            text = "Dia da Semana",
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        
        Box {
            OutlinedButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedWeekDayName.ifBlank { "Selecione um dia" },
                        modifier = Modifier.weight(1f)
                    )
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }
            
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                weekDays.forEach { (dayNum, dayName) ->
                    DropdownMenuItem(
                        text = { Text(dayName) },
                        onClick = {
                            onWeekDaySelected(dayNum, dayName)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ScheduleTimeSlotCard(
    slot: ScheduleTimeSlot,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = slot.categoryName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    text = slot.specialtyName,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = slot.weekDayName,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "•",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "${slot.startTime} - ${slot.endTime}",
                        fontSize = 12.sp,
                        color = Color(0xFF1976D2)
                    )
                }
            }
            
            IconButton(onClick = onRemove) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remover",
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
private fun TimeDropdown(
    modifier: Modifier = Modifier,
    label: String,
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    // Gerar lista de horários de 01:00 até 00:00
    val timeList = remember {
        (1..24).map { hour ->
            "%02d:00".format(if (hour == 24) 0 else hour)
        }
    }

    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Box {
            OutlinedButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedTime.ifBlank { "Selecione" },
                        modifier = Modifier.weight(1f)
                    )
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                LazyColumn(
                    modifier = Modifier.height(300.dp)
                ) {
                    items(timeList) { time ->
                        DropdownMenuItem(
                            text = { Text(time) },
                            onClick = {
                                onTimeSelected(time)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}
