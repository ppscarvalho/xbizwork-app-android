package com.br.xbizitwork.ui.presentation.features.menu.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.buttons.MenuButton
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun MenuContainer(
    modifier: Modifier = Modifier,
    onClickChangerPassword: () -> Unit,
    onClickDateRange: () -> Unit,
    onClickAssignment: () -> Unit,
    onClickEvent: () -> Unit,
    onClickViewModule: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
        ) {
            item {
                MenuButton(
                    leftIcon = Icons.Filled.Key,
                    text = "Alterar Senha",
                    onClick = onClickChangerPassword
                )
            }

            item {
                MenuButton(
                    leftIcon = Icons.Filled.DateRange,
                    text = "Monte sua agenda",
                    onClick = onClickDateRange
                )
            }

            item {
                MenuButton(
                    leftIcon = Icons.AutoMirrored.Filled.Assignment,
                    text = "Seu plano",
                    onClick = onClickAssignment
                )
            }

            item {
                MenuButton(
                    leftIcon = Icons.Filled.Event,
                    text = "Meus compromissos",
                    onClick = onClickEvent
                )
            }

            item {
                MenuButton(
                    leftIcon = Icons.Filled.ViewModule,
                    text = "Agenda profissional",
                    onClick = onClickViewModule
                )
            }
        }
    }
}

@Preview (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MenuContainerPreView() {
    XBizWorkTheme {
        MenuContainer(
            modifier = Modifier.fillMaxSize(),
            onClickChangerPassword = {},
            onClickDateRange = {},
            onClickAssignment = {},
            onClickEvent = {},
            onClickViewModule = {}
        )
    }
}