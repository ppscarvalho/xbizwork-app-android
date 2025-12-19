package com.br.xbizitwork.ui.presentation.components.buttons

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        verticalArrangement =Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MenuButton(
            leftIcon = Icons.Filled.Key,
            text = "Alterar Senha",
            onClick = onClickChangerPassword
        )

        MenuButton(
            leftIcon = Icons.Filled.DateRange,
            text = "Monte sua agenda",
            onClick = onClickDateRange
        )

        MenuButton(
            leftIcon = Icons.AutoMirrored.Filled.Assignment,
            text = "Seu plano",
            onClick = onClickAssignment
        )

        MenuButton(
            leftIcon = Icons.Filled.Event,
            text = "Meus compromissos",
            onClick = onClickEvent
        )

        MenuButton(
            leftIcon = Icons.Filled.ViewModule,
            text = "Agenda profissional",
            onClick = onClickViewModule
        )
    }
}

@Preview (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MenuContainerPreview() {
    XBizWorkTheme {
        MenuContainer(
            modifier = Modifier.fillMaxWidth(),
            onClickChangerPassword = {},
            onClickDateRange = {},
            onClickAssignment = {},
            onClickEvent = {},
            onClickViewModule = {}
        )
    }
}