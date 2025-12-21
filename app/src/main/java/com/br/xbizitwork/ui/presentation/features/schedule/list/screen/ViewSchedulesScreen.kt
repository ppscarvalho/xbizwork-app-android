package com.br.xbizitwork.ui.presentation.features.schedule.list.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.xbizitwork.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.ui.presentation.components.schedule.ProfessionalScheduleCard
import com.br.xbizitwork.ui.presentation.components.schedule.TimeSlotItem
import com.br.xbizitwork.ui.presentation.components.state.EmptyState
import com.br.xbizitwork.ui.presentation.components.state.ErrorState
import com.br.xbizitwork.ui.presentation.components.state.LoadingIndicator
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.schedule.list.events.ViewSchedulesEvent
import com.br.xbizitwork.ui.presentation.features.schedule.list.viewmodel.ViewSchedulesViewModel
import java.time.format.DateTimeFormatter

@Composable
fun ViewSchedulesScreen(
    onNavigateBack: () -> Unit,
    onNavigateToCreate: () -> Unit,
    viewModel: ViewSchedulesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    
    LaunchedEffect(Unit) {
        viewModel.sideEffectChannel.collect { sideEffect ->
            when (sideEffect) {
                is SideEffect.ShowToast -> {
                    snackbarHostState.showSnackbar(sideEffect.message)
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
                enableNavigationUp = true,
                onNavigationIconButton = onNavigateBack
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
                    onRetry = { viewModel.onEvent(ViewSchedulesEvent.OnRefresh) }
                )
                
                uiState.isEmpty -> EmptyState(
                    image = painterResource(R.drawable.ic_logo_xbizwork_light),
                    title = "Nenhuma agenda criada"
                )
                
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(uiState.schedules) { schedule ->
                            val timeSlots = schedule.availability.workingHours.map { wh ->
                                TimeSlotItem(
                                    id = "",
                                    dayOfWeek = wh.dayOfWeek.displayName,
                                    startTime = wh.startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                                    endTime = wh.endTime.format(DateTimeFormatter.ofPattern("HH:mm"))
                                )
                            }
                            
                            ProfessionalScheduleCard(
                                category = schedule.category,
                                specialty = schedule.specialty,
                                timeSlots = timeSlots,
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
