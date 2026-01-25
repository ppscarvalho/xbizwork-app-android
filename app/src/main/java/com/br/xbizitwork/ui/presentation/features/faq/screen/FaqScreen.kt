package com.br.xbizitwork.ui.presentation.features.faq.screen

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.faq.components.FaqContent
import com.br.xbizitwork.ui.presentation.features.faq.events.FaqEvent
import com.br.xbizitwork.ui.presentation.features.faq.state.FaqUiState
import com.br.xbizitwork.ui.theme.XBizWorkTheme

/**
 * Main screen for FAQ feature
 * Following the same pattern as SkillsScreen
 */
@Composable
fun FaqScreen(
    uiState: FaqUiState,
    onEvent: (FaqEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
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

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4
)
@Composable
private fun FaqScreenPreview() {
    XBizWorkTheme {
        FaqScreen(
            uiState = FaqUiState(),
            onEvent = {},
            onNavigateBack = {}
        )
    }
}
