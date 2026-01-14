package com.br.xbizitwork.ui.presentation.features.skills.screen

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
import com.br.xbizitwork.ui.presentation.features.skills.components.SkillsContent
import com.br.xbizitwork.ui.presentation.features.skills.events.SkillsEvent
import com.br.xbizitwork.ui.presentation.features.skills.state.SkillUiState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SkillsScreen(
    uiState: SkillUiState,
    appSideEffectFlow: Flow<AppSideEffect>,
    onEvent: (SkillsEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
) {
    val context = LocalContext.current
    LifecycleEventEffect(appSideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)

            is AppSideEffect.NavigateToLogin -> {
                onNavigateToLogin()
            }

            is AppSideEffect.NavigateBack -> {
                onNavigateBack()
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Minhas habilidades",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = onNavigateBack
            )
        },
        content = { paddingValues ->
            SkillsContent(
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
private fun SkillsScreenPreview() {
    XBizWorkTheme {
        SkillsScreen(
            uiState = SkillUiState(),
            appSideEffectFlow = flowOf(),
            onEvent = {},
            onNavigateBack = {},
            onNavigateToLogin = {}
        )
    }
}
