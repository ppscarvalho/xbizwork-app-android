package com.br.xbizitwork.ui.presentation.features.newschedule.create.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.newschedule.create.components.CreateDefaultScheduleContent
import com.br.xbizitwork.ui.presentation.features.newschedule.create.events.CreateDefaultScheduleEvent
import com.br.xbizitwork.ui.presentation.features.newschedule.create.state.CreateDefaultScheduleUIState
import kotlinx.coroutines.flow.Flow

@Composable
fun CreateDefaultScheduleScreen(
    uiState: CreateDefaultScheduleUIState,
    appSideEffectFlow: Flow<AppSideEffect>,
    onEvent: (CreateDefaultScheduleEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToSuccessScreen: () -> Unit
) {

    val context = LocalContext.current

    LifecycleEventEffect(appSideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.ShowToast ->
                context.toast(sideEffect.message)

            is AppSideEffect.NavigateBack ->
                onNavigateBack()

            else -> Unit
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Agenda padrão",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = { onNavigateBack() }
            )
        },
        content = { paddingValues ->
            CreateDefaultScheduleContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onEvent = onEvent
            )
        }
    )
}
