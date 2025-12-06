package com.br.xbizitwork.ui.presentation.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun HomeContainer(
    modifier: Modifier = Modifier,
    onNavigateToSignScreen: () -> Unit,
    onProfileClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(top = 1.dp, bottom = 16.dp)
        ) {
            item {
                CarouselContainer(
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                PromotionalContainer(
                    modifier = Modifier.fillMaxWidth(),
                    onNavigateToSignScreen = onNavigateToSignScreen
                )
            }

            item {
                ProfessionalsHighlightContainer(
                    modifier = Modifier.fillMaxWidth(),
                    onProfileClick = onProfileClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeContainerPreview() {
    XBizWorkTheme {
        HomeContainer(
            modifier = Modifier.fillMaxSize(),
            onNavigateToSignScreen = {},
            onProfileClick = {}
        )
    }
}