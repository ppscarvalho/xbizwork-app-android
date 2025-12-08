package com.br.xbizitwork.ui.presentation.components.carousel

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollBy
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
import androidx.compose.runtime.LaunchedEffect
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
import com.br.xbizitwork.ui.presentation.components.carouselitem.CarouselItemView
import com.br.xbizitwork.ui.presentation.features.home.models.CarouselImageItem
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.example.xbizitwork.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.abs

@Composable
fun AutoScrollingCarousel(
    modifier: Modifier = Modifier,
    items: List<CarouselImageItem>,
    imageHeight: Dp = 320.dp,
    itemWidthFraction: Float = 0.96f,
    itemSpacing: Dp = 12.dp,
    pauseBetweenItemsMillis: Long = 3500L,
) {
    if (items.isEmpty()) {
        Box(
            modifier
                .fillMaxWidth()
                .height(imageHeight + 60.dp),
            contentAlignment = Alignment.Center
        ) {  }
        return
    }

    val actualListSize = items.size
    val listWithDuplicates = remember(items) { items + items }
    val lazyListState = rememberLazyListState()
    val density = LocalDensity.current

    var currentCalculatedItemWidthDp by remember { mutableStateOf(0.dp) }

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val screenWidth = this.maxWidth
        currentCalculatedItemWidthDp = screenWidth * itemWidthFraction
        val itemWidthPx = with(density) { currentCalculatedItemWidthDp.toPx() }
        val itemSpacingPx = with(density) { itemSpacing.toPx() }
        val itemWidthWithSpacingPx = itemWidthPx + itemSpacingPx

        LaunchedEffect(key1 = items, key2 = lazyListState, key3 = itemWidthWithSpacingPx) { // Adicionado currentCalculatedItemWidthDp como chave
            if (items.size > 1 && itemWidthWithSpacingPx > 0f) {
                lazyListState.scrollToItem(0, 0)
                delay(100) // Pequeno delay para o scrollToItem inicial assentar antes de começar o loop

                while (isActive) {
                    delay(pauseBetweenItemsMillis)

                    val steps = 30 // Número de passos para a animação de um item
                    val durationPerItemMs = 500L // Duração para rolar UM item

                    val currentItemOffsetPx = lazyListState.firstVisibleItemScrollOffset.toFloat()
                    val scrollAmountForNextItemPx = itemWidthWithSpacingPx - currentItemOffsetPx

                    if (scrollAmountForNextItemPx <= 0.01f && lazyListState.firstVisibleItemScrollOffset == 0) {}

                    val scrollStep = scrollAmountForNextItemPx / steps
                    val delayStep = durationPerItemMs / steps

                    if (scrollAmountForNextItemPx > 0.01f) { // Só rola se houver algo para rolar
                        repeat(steps) {
                            if (!isActive) return@LaunchedEffect // Sai se o efeito for cancelado
                            lazyListState.scrollBy(scrollStep)
                            delay(delayStep)
                        }
                    }
                    val currentIndexAfterScroll = lazyListState.firstVisibleItemIndex
                    if (currentIndexAfterScroll >= actualListSize) {
                        val equivalentIndexInFirstHalf = currentIndexAfterScroll % actualListSize
                        lazyListState.scrollToItem(
                            index = equivalentIndexInFirstHalf,
                            scrollOffset = 0 // Garante que comece alinhado na primeira metade
                        )
                        delay(50)
                    }
                }
            }
        }

        // UI (LazyRow e Indicadores) - Permanece o mesmo
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
                    items = listWithDuplicates,
                    key = { index, item -> "${item.id}-${index / actualListSize}" }
                ) { _, item ->
                    CarouselItemView(
                        item = item,
                        imageHeight = imageHeight,
                        modifier = Modifier.width(currentCalculatedItemWidthDp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            val indicatorIndex = remember {
                derivedStateOf {
                    val currentIndexInFullList = lazyListState.firstVisibleItemIndex
                    val offsetInFullList = lazyListState.firstVisibleItemScrollOffset
                    val itemWidthPxLocal = with(density) { currentCalculatedItemWidthDp.toPx() } // Use a largura Dp atual
                    val itemSpacingPxLocal = with(density) { itemSpacing.toPx() }
                    val itemWidthWithSpacingPxLocal = itemWidthPxLocal + itemSpacingPxLocal

                    if (itemWidthWithSpacingPxLocal == 0f) {
                        (currentIndexInFullList % actualListSize).toFloat()
                    } else {
                        val progressInCurrentItem = (offsetInFullList / itemWidthWithSpacingPxLocal).coerceIn(0f, 1f)
                        (currentIndexInFullList % actualListSize + progressInCurrentItem).toFloat()
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 8.dp)
            ) {
                repeat(actualListSize) { index ->
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
        } // Fim da Column
    } // Fim do BoxWithConstraints
}

@Preview
@Composable
private fun AutoScrollingCarouselPreview() {
    XBizWorkTheme {
        val carouselItems = remember {
            listOf(
                CarouselImageItem(1,"Educação", resourceId = R.drawable.educacao),
                CarouselImageItem(2,"Saúde", resourceId = R.drawable.saude),
                CarouselImageItem(3,"Diarista", resourceId = R.drawable.diarista),
                CarouselImageItem(4,"Manicure", resourceId = R.drawable.manicure),
                CarouselImageItem(5,"Contrução Civil", resourceId = R.drawable.construcao),
                CarouselImageItem(6,"Limpeza de Terreno", resourceId = R.drawable.limpeza_terreno),
                CarouselImageItem(7,"Refrigeração",resourceId = R.drawable.refrigeracao_ar_condicionado
                ),
            )
        }

        AutoScrollingCarousel(
            items = carouselItems,
            imageHeight = 200.dp,
            pauseBetweenItemsMillis = 3000L
        )
    }
}