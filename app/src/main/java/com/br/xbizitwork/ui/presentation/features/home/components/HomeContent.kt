package com.br.xbizitwork.ui.presentation.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.background.AppGradientBackground
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onNavigateToPlansScreen: () -> Unit,
    onNavigateToProfileScreen: () -> Unit
) {
    AppGradientBackground(
        modifier = modifier.fillMaxSize(),
        paddingValues = paddingValues
    ) {
        HomeContainer(
            modifier = Modifier.fillMaxSize(), // 👈 ocupa tudo
            onNavigateToPlansScreen = onNavigateToPlansScreen,
            onNavigateToProfileScreen = onNavigateToProfileScreen
        )
    }
}


@Preview
@Composable
private fun HomeContentPreview() {
    XBizWorkTheme {
        HomeContent(
            paddingValues = PaddingValues(),
            onNavigateToPlansScreen = {},
            onNavigateToProfileScreen = {}
        )
    }
}