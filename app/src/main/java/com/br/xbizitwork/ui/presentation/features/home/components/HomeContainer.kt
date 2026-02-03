package com.br.xbizitwork.ui.presentation.features.home.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.cards.PromotionalContainer
import com.br.xbizitwork.ui.presentation.components.carousel.CarouselContainer
import com.br.xbizitwork.ui.presentation.components.professionalhighlight.ProfessionalsHighlightContainer
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun HomeContainer(
    modifier: Modifier = Modifier,
    onNavigationToSignInScreen: () -> Unit,
    onNavigateToProfileScreen: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues()
    ) {
        item {
            CarouselContainer(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        item {
            PromotionalContainer(
                modifier = Modifier.fillMaxWidth(),
                onNavigationToSignInScreen = onNavigationToSignInScreen
            )
        }

        item {
            ProfessionalsHighlightContainer(
                modifier = Modifier.fillMaxWidth(),
                onProfileClick = onNavigateToProfileScreen
            )
        }
    }
}


@Preview (showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF082F49,
    )
@Composable
private fun HomeContainerPreview() {
    XBizWorkTheme {
        HomeContainer(
            modifier = Modifier.fillMaxSize(),
            onNavigationToSignInScreen = {},
            onNavigateToProfileScreen = {}
        )
    }
}