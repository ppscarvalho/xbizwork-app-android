package com.br.xbizitwork.ui.presentation.features.menu.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.menu.components.MenuContent
import com.example.xbizitwork.R
import kotlinx.coroutines.flow.Flow

@Composable
fun MenuScreen(
    onNavigateToHomeGraph: () -> Unit,
    sideEffectFlow: Flow<SideEffect>,
    onClickUpdateProfile: () -> Unit,
    onClickChangePassword: () -> Unit,
    onClickSetupSchedule: () -> Unit,
    onClickYourPlan: () -> Unit,
    onClickMyAppointments: () -> Unit,
    onClickProfessionalAgenda: () -> Unit,
    onClickFAQ: () -> Unit,
    onClickAppVersion: () -> Unit,
    onClickRateApp: () -> Unit,
    onClickLogout: () -> Unit
) {
    val context = LocalContext.current

    // Tratar SideEffects (Toast)
    LifecycleEventEffect(sideEffectFlow) { sideEffect ->
        when(sideEffect) {
            is SideEffect.ShowToast -> context.toast(sideEffect.message)
            is SideEffect.NavigateToLogin -> {
                // MenuScreen não trata NavigateToLogin, ignora
            }
            is SideEffect.NavigateBack -> {
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MenuContent(
                    onClickUpdateProfile = onClickUpdateProfile,
                    onClickChangePassword = onClickChangePassword,
                    onClickSetupSchedule = onClickSetupSchedule,
                    onClickYourPlan = onClickYourPlan,
                    onClickMyAppointments = onClickMyAppointments,
                    onClickProfessionalAgenda = onClickProfessionalAgenda,
                    onClickFAQ = onClickFAQ,
                    onClickAppVersion = onClickAppVersion,
                    onClickRateApp = onClickRateApp,
                    onClickLogout = onClickLogout
                )
            }
        }
    )
}
