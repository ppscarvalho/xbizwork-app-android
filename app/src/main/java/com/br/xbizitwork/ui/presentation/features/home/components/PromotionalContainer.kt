package com.br.xbizitwork.ui.presentation.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.ui.presentation.components.cards.CardContainer
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun PromotionalContainer(
    modifier: Modifier = Modifier,
    onNavigateToSignScreen: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CardContainer(
            modifier = Modifier.fillMaxWidth(),
            onNavigateToSignScreen = onNavigateToSignScreen
        )
    }
}

@Preview
@Composable
private fun PromotionalContainerPreview() {
    XBizWorkTheme {
        PromotionalContainer(
            modifier = Modifier.fillMaxWidth(),
            onNavigateToSignScreen = {}
        )
    }
}
