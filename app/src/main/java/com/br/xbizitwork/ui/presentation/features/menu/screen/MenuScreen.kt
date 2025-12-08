package com.br.xbizitwork.ui.presentation.features.menu.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.menu.components.MenuContent
import com.example.xbizitwork.R

@Composable
fun MenuScreen(
    onNavigateToHomeGraph: () -> Unit,
    onClickChangerPassword: () -> Unit,
    onClickDateRange: () -> Unit,
    onClickAssignment: () -> Unit,
    onClickEvent: () -> Unit,
    onClickViewModule: () -> Unit
) {
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
        content = {paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MenuContent(
                    onClickChangerPassword = onClickChangerPassword,
                    onClickDateRange = onClickDateRange,
                    onClickAssignment = onClickAssignment,
                    onClickEvent = onClickEvent,
                    onClickViewModule = onClickViewModule
                )
            }
        }
    )
}