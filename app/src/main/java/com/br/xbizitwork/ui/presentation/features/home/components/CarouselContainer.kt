package com.br.xbizitwork.ui.presentation.features.home.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.common.ImageAssets
import com.br.xbizitwork.ui.presentation.common.StringAssets
import com.br.xbizitwork.ui.presentation.components.carousel.AutoScrollingCarousel
import com.br.xbizitwork.ui.presentation.features.home.state.CarouselImageItem
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun CarouselContainer(
    modifier: Modifier = Modifier
) {
    val educacao = stringResource(id = StringAssets.CATEGORIA_EDUCACAO)
    val saude = stringResource(id = StringAssets.CATEGORIA_SAUDE)
    val diarista = stringResource(id = StringAssets.CATEGORIA_DIARISTA)
    val manicure = stringResource(id = StringAssets.CATEGORIA_MANICURE)
    val construcaoCivil = stringResource(id = StringAssets.CATEGORIA_CONSTRUCAO_CIVIL)
    val limpezaTerreno = stringResource(id = StringAssets.CATEGORIA_LIMPEZA_TERRENO)
    val refrigeracao = stringResource(id = StringAssets.CATEGORIA_REFRIGERACAO)

    val carouselItems = remember {
        listOf(
            CarouselImageItem(1, educacao, resourceId = ImageAssets.CATEGORIA_EDUCACAO),
            CarouselImageItem(2, saude, resourceId = ImageAssets.CATEGORIA_SAUDE),
            CarouselImageItem(3, diarista, resourceId = ImageAssets.CATEGORIA_DIARISTA),
            CarouselImageItem(4, manicure, resourceId = ImageAssets.CATEGORIA_MANICURE),
            CarouselImageItem(5, construcaoCivil, resourceId = ImageAssets.CATEGORIA_CONSTRUCAO),
            CarouselImageItem(6, limpezaTerreno, resourceId = ImageAssets.CATEGORIA_LIMPEZA_TERRENO),
            CarouselImageItem(7, refrigeracao, resourceId = ImageAssets.CATEGORIA_REFRIGERACAO),
        )
    }

    Spacer(modifier = Modifier.height(16.dp))
    
    if (carouselItems.isNotEmpty()) {
        AutoScrollingCarousel(
            items = carouselItems,
            modifier = modifier
        )
    } else {
        Text(
            "Nenhum item no carrossel para exibir.",
            modifier = modifier
        )
    }
    
    Spacer(modifier = Modifier.height(16.dp))
}

@Preview
@Composable
private fun CarouselContainerPreview() {
    XBizWorkTheme {
        CarouselContainer(
            modifier = Modifier.fillMaxWidth()
        )
    }
}
