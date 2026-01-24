package com.br.xbizitwork.ui.presentation.features.searchprofessionals.screen

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.components.SearchProfessionalsContent
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.events.SearchProfessionalBySkillEvent
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.state.SearchProfessionalsUiState
import com.br.xbizitwork.ui.theme.XBizWorkTheme

/**
 * Screen composable for professional search by skill
 * Following the same pattern as SearchScheduleScreen
 */
@Composable
fun SearchProfessionalsScreen(
    uiState: SearchProfessionalsUiState,
    onEvent: (SearchProfessionalBySkillEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onProfessionalSelected: (ProfessionalSearchBySkill) -> Unit = {}
) {
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
                onProfessionalSelected = onProfessionalSelected
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
private fun SearchProfessionalsScreenPreview() {
    XBizWorkTheme {
        SearchProfessionalsScreen(
            uiState = SearchProfessionalsUiState(),
            onEvent = {},
            onNavigateBack = {}
        )
    }
}
