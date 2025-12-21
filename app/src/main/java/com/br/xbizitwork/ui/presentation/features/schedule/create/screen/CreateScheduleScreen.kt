package com.br.xbizitwork.ui.presentation.features.schedule.create.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.schedule.create.components.CreateScheduleContent
import com.br.xbizitwork.ui.presentation.features.schedule.create.events.CreateScheduleEvent
import com.br.xbizitwork.ui.presentation.features.schedule.create.state.CreateScheduleUIState
import kotlinx.coroutines.flow.Flow

@Composable
fun CreateScheduleScreen(
    uiState: CreateScheduleUIState,
    sideEffectFlow: Flow<SideEffect>,
    onEvent: (CreateScheduleEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToViewSchedules: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // Side Effects
    LaunchedEffect(Unit) {
        sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is SideEffect.ShowToast -> {
                    snackbarHostState.showSnackbar(sideEffect.message)
                }
                SideEffect.NavigateBack -> onNavigateToViewSchedules() // ← Vai para lista
                else -> Unit
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Criar Agenda",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = { onNavigateBack() }
            )
        },
        content = {paddingValues ->
            CreateScheduleContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onEvent = onEvent
            )
        }
    )

//    Scaffold(
//        topBar = {
//            AppTopBar(
//                isHomeMode = false,
//                title = "Criar Agenda",
//                enableNavigationUp = true,
//                onNavigationIconButton = onNavigateBack
//            )
//        },
//        snackbarHost = { SnackbarHost(snackbarHostState) }
//    ) { paddingValues ->
//
//        // 🔥 Conteúdo SEM scroll aqui
//        CreateScheduleContent(
//            paddingValues = paddingValues,
//            uiState = uiState,
//            onEvent = onEvent
//        )
//    }
}
