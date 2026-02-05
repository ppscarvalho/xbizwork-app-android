package com.br.xbizitwork.ui.presentation.components.carousel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.common.ImageAssets
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import kotlin.math.abs

/**
 * Componente de carrossel para exibir portfólio de imagens
 * Baseado em AutoScrollingCarousel mas SEM autoplay
 * Scroll apenas por interação manual do usuário
 */
@Composable
fun PortfolioCarousel(
    modifier: Modifier = Modifier,
    images: List<Int>, // Resource IDs das imagens
    imageHeight: Dp = 200.dp,
    itemWidthFraction: Float = 0.90f,
    itemSpacing: Dp = 12.dp
) {
    if (images.isEmpty()) {
        Box(
            modifier
                .fillMaxWidth()
                .height(imageHeight + 60.dp),
            contentAlignment = Alignment.Center
        ) { }
        return
    }

    val lazyListState = rememberLazyListState()
    val density = LocalDensity.current

    var currentCalculatedItemWidthDp by remember { mutableStateOf(0.dp) }

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val screenWidth = this.maxWidth
        currentCalculatedItemWidthDp = screenWidth * itemWidthFraction
        val itemWidthPx = with(density) { currentCalculatedItemWidthDp.toPx() }
        val itemSpacingPx = with(density) { itemSpacing.toPx() }
        val itemWidthWithSpacingPx = itemWidthPx + itemSpacingPx

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LazyRow(
                state = lazyListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(itemSpacing),
                contentPadding = PaddingValues(horizontal = (screenWidth - currentCalculatedItemWidthDp) / 2)
            ) {
                itemsIndexed(
                    items = images,
                    key = { index, _ -> "portfolio-$index" }
                ) { _, imageRes ->
                    PortfolioItemView(
                        imageRes = imageRes,
                        imageHeight = imageHeight,
                        modifier = Modifier.width(currentCalculatedItemWidthDp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Indicadores de página (dots)
            val indicatorIndex = remember {
                derivedStateOf {
                    val currentIndexInList = lazyListState.firstVisibleItemIndex
                    val offsetInList = lazyListState.firstVisibleItemScrollOffset

                    if (itemWidthWithSpacingPx > 0f) {
                        val offsetInItems = offsetInList / itemWidthWithSpacingPx
                        (currentIndexInList + offsetInItems).coerceIn(0f, (images.size - 1).toFloat())
                    } else {
                        0f
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                images.indices.forEach { index ->
                    val selectionFraction =
                        (1f - abs(indicatorIndex.value - index)).coerceIn(0f, 1f)
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(
                                lerp(
                                    Color.LightGray,
                                    MaterialTheme.colorScheme.primary,
                                    selectionFraction
                                )
                            )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PortfolioCarouselPreview() {
    XBizWorkTheme {
        PortfolioCarousel(
            images = listOf(
                ImageAssets.PORTFOLIO_EDUCADOR_1,
                ImageAssets.PORTFOLIO_EDUCADOR_2,
                ImageAssets.PORTFOLIO_EDUCADOR_3,
                ImageAssets.PORTFOLIO_MANICURE_1,
                ImageAssets.PORTFOLIO_MANICURE_2,
                ImageAssets.PORTFOLIO_MANICURE_3,
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
