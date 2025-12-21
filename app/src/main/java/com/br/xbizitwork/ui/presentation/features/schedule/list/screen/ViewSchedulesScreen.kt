package com.br.xbizitwork.ui.presentation.features.schedule.list.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.ui.presentation.components.schedule.ProfessionalScheduleCard
import com.br.xbizitwork.ui.presentation.components.schedule.TimeSlotItem
import com.br.xbizitwork.ui.presentation.components.state.EmptyState
import com.br.xbizitwork.ui.presentation.components.state.ErrorState
import com.br.xbizitwork.ui.presentation.components.state.LoadingIndicator
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.schedule.list.events.ViewSchedulesEvent
import com.br.xbizitwork.ui.presentation.features.schedule.list.state.ViewSchedulesUIState
import com.example.xbizitwork.R
import kotlinx.coroutines.flow.Flow


@SuppressLint("NewApi")
@Composable
fun ViewSchedulesScreen(
    uiState: ViewSchedulesUIState,
    sideEffectFlow: Flow<SideEffect>,
    onEvent: (ViewSchedulesEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToCreate: () -> Unit,
    onNavigateToLogin: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    
    LaunchedEffect(Unit) {
        sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is SideEffect.ShowToast -> {
                    snackbarHostState.showSnackbar(sideEffect.message)
                }
                is SideEffect.NavigateToLogin -> {
                    onNavigateToLogin()
                }
                else -> {}
            }
        }
    }
    
    Scaffold(
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Minhas Agendas",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = { onNavigateBack() }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToCreate() }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Criar Agenda")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> LoadingIndicator()
                
                uiState.errorMessage != null -> ErrorState(
                    message = uiState.errorMessage,
                    onRetry = { onEvent(ViewSchedulesEvent.OnRefresh) }
                )
                
                uiState.isEmpty -> EmptyState(
                    image = painterResource(R.drawable.ic_empty_state_recipes),
                    title = "Nenhuma agenda criada"
                )
                
                else -> {
                    // Agrupar agendas por Categoria + Especialidade
                    val groupedSchedules = uiState.schedules.groupBy {
                        "${it.category}|${it.specialty}"
                    }

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        groupedSchedules.forEach { (key, schedulesList) ->
                            // Pegar categoria e especialidade do primeiro item (todos são iguais)
                            val firstSchedule = schedulesList.first()
                            val category = firstSchedule.category
                            val specialty = firstSchedule.specialty

                            // Juntar TODOS os timeSlots de todas as agendas desse grupo
                            val allTimeSlots = schedulesList.flatMap { schedule ->
                                schedule.availability.workingHours.map { wh ->
                                    val startStr = wh.startTime.toString()
                                    val endStr = wh.endTime.toString()

                                    TimeSlotItem(
                                        id = schedule.id,
                                        dayOfWeek = wh.dayOfWeek.displayName,
                                        startTime = if (startStr.length >= 5) startStr.substring(0, 5) else startStr,
                                        endTime = if (endStr.length >= 5) endStr.substring(0, 5) else endStr
                                    )
                                }
                            }
                            
                            // ✅ UM ÚNICO CARD com TODOS os horários
                            item(key = key) {
                                ProfessionalScheduleCard(
                                    category = category,
                                    specialty = specialty,
                                    timeSlots = allTimeSlots,
                                    onRemoveSlot = { slot ->
                                        // TODO: Implementar remoção de slot
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
