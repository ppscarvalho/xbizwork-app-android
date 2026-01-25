package com.br.xbizitwork.ui.presentation.features.faq.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.faq.components.FaqContent
import com.br.xbizitwork.ui.presentation.features.faq.events.FaqEvent
import com.br.xbizitwork.ui.presentation.features.faq.state.FaqUiState
import kotlinx.coroutines.flow.Flow

/**
 * Tela principal do FAQ
 * Seguindo o mesmo padrão do SkillsScreen
 */
@Composable
fun FaqScreen(
    uiState: FaqUiState,
    appSideEffectFlow: Flow<AppSideEffect>,
    onEvent: (FaqEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    
    LifecycleEventEffect(appSideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)
            is AppSideEffect.NavigateBack -> onNavigateBack()
            else -> {} // Outros side effects não são relevantes para FAQ
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Dúvidas Frequentes",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = onNavigateBack
            )
        },
        content = { paddingValues ->
            FaqContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onEvent = onEvent
            )
        }
    )
}
