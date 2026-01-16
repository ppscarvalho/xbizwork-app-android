package com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.screen

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.components.SearchProfessionalBySkillContent
import com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.events.SearchProfessionalBySkillEvent
import com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.state.SearchProfessionalBySkillUIState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Screen composable for professional search by skill
 * Following the same pattern as SkillsScreen
 */
@Composable
fun SearchProfessionalSkillScreen(
    uiState: SearchProfessionalBySkillUIState,
    appSideEffectFlow: Flow<AppSideEffect>,
    onEvent: (SearchProfessionalBySkillEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    
    LifecycleEventEffect(appSideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)
            is AppSideEffect.NavigateBack -> onNavigateBack()
            else -> { /* Other side effects not handled here */ }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Buscar Profissionais",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = onNavigateBack
            )
        },
        content = { paddingValues ->
            SearchProfessionalBySkillContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onEvent = onEvent
            )
        }
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4
)
@Composable
private fun SearchProfessionalSkillScreenPreview() {
    XBizWorkTheme {
        SearchProfessionalSkillScreen(
            uiState = SearchProfessionalBySkillUIState(),
            appSideEffectFlow = flowOf(),
            onEvent = {},
            onNavigateBack = {}
        )
    }
}
