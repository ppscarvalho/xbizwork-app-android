package com.br.xbizitwork.ui.presentation.features.searchprofessionals.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.components.SearchProfessionalsContent
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.events.SearchProfessionalBySkillEvent
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.state.SearchProfessionalsUiState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Screen composable for professional search by skill
 * Following the same pattern as SearchScheduleScreen
 */
@Composable
fun SearchProfessionalsScreen(
    uiState: SearchProfessionalsUiState,
    appSideEffectFlow: Flow<AppSideEffect>,
    onEvent: (SearchProfessionalBySkillEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onProfessionalSelected: (ProfessionalSearchBySkill) -> Unit = {},
    onMapClick: (ProfessionalSearchBySkill) -> Unit = {}
) {
    val context = LocalContext.current
    
    // Tratar SideEffects (Toast)
    LifecycleEventEffect(appSideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)
            is AppSideEffect.NavigateToHomeGraph -> {
                // SearchProfessionalsScreen não trata NavigateToHomeGraph, ignora
            }
            is AppSideEffect.NavigateBack -> {
                // SearchProfessionalsScreen não trata NavigateBack, ignora
            }
        }
    }

    // Iniciar observação da busca
    LaunchedEffect(Unit) {
        onEvent(SearchProfessionalBySkillEvent.OnRefresh)
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
            SearchProfessionalsContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onEvent = onEvent,
                onProfessionalSelected = onProfessionalSelected,
                onMapClick = onMapClick
            )
        }
    )
}

@Preview(
    showBackground = true,
    device = Devices.PIXEL_4
)
@Composable
private fun SearchProfessionalsScreenPreview() {
    XBizWorkTheme {
        SearchProfessionalsScreen(
            uiState = SearchProfessionalsUiState(),
            appSideEffectFlow = flowOf(),
            onEvent = {},
            onNavigateBack = {}
        )
    }
}
