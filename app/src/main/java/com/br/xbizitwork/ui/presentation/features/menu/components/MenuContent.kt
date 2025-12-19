package com.br.xbizitwork.ui.presentation.features.menu.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun MenuContent(
    modifier: Modifier = Modifier,
    onClickUpdateProfile: () -> Unit,
    onClickChangerPassword: () -> Unit,
    onClickDateRange: () -> Unit,
    onClickAssignment: () -> Unit,
    onClickEvent: () -> Unit,
    onClickViewModule: () -> Unit,
    onClickFAQ: () -> Unit,
    onClickAppVersion: () -> Unit,
    onClickRateApp: () -> Unit,
    onClickLogout: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MenuContainer(
            modifier = Modifier.weight(1f),
            onClickUpdateProfile = onClickUpdateProfile,
            onClickChangerPassword = onClickChangerPassword,
            onClickDateRange = onClickDateRange,
            onClickAssignment = onClickAssignment,
            onClickEvent = onClickEvent,
            onClickViewModule = onClickViewModule,
            onClickFAQ = onClickFAQ,
            onClickAppVersion = onClickAppVersion,
            onClickRateApp = onClickRateApp,
            onClickLogout = onClickLogout
        )
    }
}

@Preview (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MenuContentPreview() {
    XBizWorkTheme {
        MenuContent(
            onClickUpdateProfile = {},
            onClickChangerPassword = {},
            onClickDateRange = {},
            onClickAssignment = {},
            onClickEvent = {},
            onClickViewModule = {},
            onClickFAQ = {},
            onClickAppVersion = {},
            onClickRateApp = {},
            onClickLogout = {}
        )
    }
}