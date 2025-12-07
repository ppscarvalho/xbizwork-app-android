package com.br.xbizitwork.ui.presentation.components.cards

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun PromotionalContainer(
    modifier: Modifier = Modifier,
    onNavigationToSignInScreen: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        CardContainer(
            modifier = Modifier.fillMaxWidth(),
            onNavigationToSignInScreen = onNavigationToSignInScreen
        )
    }
}

@Preview
@Composable
private fun PromotionalContainerPreview() {
    XBizWorkTheme {
        PromotionalContainer(
            modifier = Modifier.fillMaxWidth(),
            onNavigationToSignInScreen = {}
        )
    }
}
