package com.br.xbizitwork.ui.presentation.features.plans.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.plans.components.PlanContent
import com.br.xbizitwork.ui.presentation.features.plans.events.PlanEvent
import com.br.xbizitwork.ui.presentation.features.plans.state.PlanUiState
import kotlinx.coroutines.flow.Flow

@Composable
fun PlanScreen(
    uiState: PlanUiState,
    appSideEffectFlow: Flow<AppSideEffect>,
    onEvent: (PlanEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current

    // Tratar SideEffects
    LifecycleEventEffect(appSideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)
            else -> {}
        }
    }

    // Refresh ao iniciar
    LaunchedEffect(Unit) {
        onEvent(PlanEvent.OnRefresh)
    }

    Scaffold(
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Escolha Seu Plano",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = onNavigateBack
            )
        },
        content = { paddingValues ->
            PlanContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onEvent = onEvent
            )
        }
    )
}
