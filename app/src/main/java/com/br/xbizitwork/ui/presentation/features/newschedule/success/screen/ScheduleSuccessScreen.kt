package com.br.xbizitwork.ui.presentation.features.newschedule.success.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.newschedule.success.components.ScheduleSuccessContent

@Composable
fun ScheduleSuccessScreen(
    onNavigateBack:() -> Unit,
    onNavigateToListSchedulesScreen: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Criar Agenda",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = { onNavigateBack() }
            )
        },
        content = {paddingValues ->
            ScheduleSuccessContent(
                paddingValues = paddingValues,
                onConfirm = {},
                onEditSchedule = { }
            )
        }
    )
}
