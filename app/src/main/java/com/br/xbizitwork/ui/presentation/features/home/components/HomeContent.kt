package com.br.xbizitwork.ui.presentation.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onNavigationToSignInScreen: () -> Unit,
    onNavigateToProfileScreen: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HomeContainer(
            modifier = Modifier.weight(1f),
            onNavigationToSignInScreen = onNavigationToSignInScreen,
            onNavigateToProfileScreen = onNavigateToProfileScreen
        )
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    XBizWorkTheme {
        HomeContent(
            onNavigationToSignInScreen = {},
            onNavigateToProfileScreen = {}
        )
    }
}