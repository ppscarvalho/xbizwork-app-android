package com.br.xbizitwork.ui.presentation.features.schedule.list.screen

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.schedule.list.components.ListSchedulesContent
import com.br.xbizitwork.ui.presentation.features.schedule.list.events.ViewSchedulesEvent
import com.br.xbizitwork.ui.presentation.features.schedule.list.state.ViewSchedulesUIState
import kotlinx.coroutines.flow.Flow


@SuppressLint("NewApi")
@Composable
fun ListSchedulesScreen(
    uiState: ViewSchedulesUIState,
    appSideEffectFlow: Flow<AppSideEffect>,
    onEvent: (ViewSchedulesEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToCreateSchedule: () -> Unit,
    onNavigateToLogin: () -> Unit = {}
) {
    val context = LocalContext.current

    LifecycleEventEffect(appSideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)
            is AppSideEffect.NavigateToLogin -> onNavigateToLogin()
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Minhas Agendas",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreateSchedule
            ) {
                Icon(Icons.Default.Add, contentDescription = "Criar Agenda")
            }
        }
    ) { paddingValues ->
        ListSchedulesContent(
            uiState = uiState,
            onEvent = onEvent,
            paddingValues = paddingValues
        )
    }
}
