package com.br.xbizitwork.ui.presentation.features.menu.screen

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.menu.components.MenuContent
import com.br.xbizitwork.R
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun MenuScreen(
    onNavigateToHomeGraph: () -> Unit,
    appSideEffectFlow: Flow<AppSideEffect>,
    onClickUpdateProfile: () -> Unit,
    onClickChangePassword: () -> Unit,
    onClickCreateSkills: () -> Unit,
    onClickSetupSchedule: () -> Unit,
    onClickYourPlan: () -> Unit,
    onClickMyAppointments: () -> Unit,
    onClickProfessionalAgenda: () -> Unit,
    //onClickFAQ: () -> Unit,
    onClickAppVersion: () -> Unit,
    onClickRateApp: () -> Unit,
    onClickLogout: () -> Unit
) {
    val context = LocalContext.current

    // Tratar SideEffects (Toast)
    LifecycleEventEffect(appSideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)
            is AppSideEffect.NavigateToHomeGraph -> {
                // MenuScreen não trata NavigateToLogin, ignora
            }

            is AppSideEffect.NavigateBack -> {
                // MenuScreen não trata NavigateBack, ignora
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = stringResource(id = R.string.menu_text),
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = { onNavigateToHomeGraph() }
            )
        },
        content = { paddingValues ->
            MenuContent(
                paddingValues = paddingValues,
                onClickUpdateProfile = onClickUpdateProfile,
                onClickChangePassword = onClickChangePassword,
                onClickCreateSkills = onClickCreateSkills,
                onClickSetupSchedule = onClickSetupSchedule,
                onClickYourPlan = onClickYourPlan,
                onClickMyAppointments = onClickMyAppointments,
                onClickProfessionalAgenda = onClickProfessionalAgenda,
                //onClickFAQ = onClickFAQ,
                onClickAppVersion = onClickAppVersion,
                onClickRateApp = onClickRateApp,
                onClickLogout = onClickLogout
            )
        }
    )
}


@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun MenuScreenPreview() {
    XBizWorkTheme {
        MenuScreen(
            onNavigateToHomeGraph = {},
            appSideEffectFlow = emptyFlow(),
            onClickUpdateProfile = {},
            onClickChangePassword = {},
            onClickCreateSkills = {},
            onClickSetupSchedule = {},
            onClickYourPlan = {},
            onClickMyAppointments = {},
            onClickProfessionalAgenda = {},
            onClickAppVersion = {},
            onClickRateApp = {},
            onClickLogout = {}
        )
    }
}
