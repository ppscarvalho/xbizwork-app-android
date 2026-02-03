package com.br.xbizitwork.ui.presentation.features.menu.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.background.AppGradientBackground
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun MenuContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onClickUpdateProfile: () -> Unit,
    onClickChangePassword: () -> Unit,
    onClickCreateSkills: () -> Unit,
    onClickSetupSchedule: () -> Unit,
    onClickYourPlan: () -> Unit,
    onClickMyAppointments: () -> Unit,
    onClickProfessionalAgenda: () -> Unit,
    onClickAppVersion: () -> Unit,
    onClickRateApp: () -> Unit,
    onClickLogout: () -> Unit
) {
    AppGradientBackground(
        modifier = modifier,
        paddingValues = paddingValues
    ) {
        // Conteúdo scrollável
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 10.dp)
                .padding(horizontal = 10.dp),

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Formulário
            MenuContainer(
                modifier = Modifier.weight(1f),
                onClickUpdateProfile = onClickUpdateProfile,
                onClickChangePassword = onClickChangePassword,
                onClickCreateSkills = onClickCreateSkills,
                onClickSetupSchedule = onClickSetupSchedule,
                onClickYourPlan = onClickYourPlan,
                onClickMyAppointments = onClickMyAppointments,
                onClickProfessionalAgenda = onClickProfessionalAgenda,
                onClickAppVersion = onClickAppVersion,
                onClickRateApp = onClickRateApp,
                onClickLogout = onClickLogout
            )
        }
    }
}

@Preview (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MenuContentPreview() {
    XBizWorkTheme {
        MenuContent(
            paddingValues = PaddingValues(),
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