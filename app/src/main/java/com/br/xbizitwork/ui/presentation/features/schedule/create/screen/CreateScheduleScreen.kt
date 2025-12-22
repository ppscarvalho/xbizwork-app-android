package com.br.xbizitwork.ui.presentation.features.schedule.create.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.schedule.create.components.CreateScheduleContent
import com.br.xbizitwork.ui.presentation.features.schedule.create.events.CreateScheduleEvent
import com.br.xbizitwork.ui.presentation.features.schedule.create.state.CreateScheduleUIState
import kotlinx.coroutines.flow.Flow

@Composable
fun CreateScheduleScreen(
    uiState: CreateScheduleUIState,
    appSideEffectFlow: Flow<AppSideEffect>,
    onEvent: (CreateScheduleEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToViewSchedules: () -> Unit
) {
    val context = LocalContext.current
    LifecycleEventEffect(appSideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)

            is AppSideEffect.NavigateToLogin -> {
                onNavigateToViewSchedules()
            }
            else -> Unit
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
}
