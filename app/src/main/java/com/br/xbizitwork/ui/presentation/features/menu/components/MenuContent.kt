package com.br.xbizitwork.ui.presentation.features.menu.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.br.xbizitwork.ui.presentation.features.home.components.HomeContainer

@Composable
fun MenuContent(
    modifier: Modifier = Modifier,
    onClickChangerPassword: () -> Unit,
    onClickDateRange: () -> Unit,
    onClickAssignment: () -> Unit,
    onClickEvent: () -> Unit,
    onClickViewModule: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MenuContainer(
            modifier = Modifier.weight(1f),
            onClickChangerPassword = onClickChangerPassword,
            onClickDateRange = onClickDateRange,
            onClickAssignment = onClickAssignment,
            onClickEvent = onClickEvent,
            onClickViewModule = onClickViewModule
        )
    }
}