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
import com.br.xbizitwork.ui.presentation.components.carousel.AutoScrollingCarousel
import com.br.xbizitwork.ui.presentation.features.home.state.CarouselImageItem
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.example.xbizitwork.R

@Composable
fun CarouselContainer(
    modifier: Modifier = Modifier
) {
    val educacao = stringResource(id = R.string.educacao_text)
    val saude = stringResource(id = R.string.saude_text)
    val diarista = stringResource(id = R.string.diarista_text)
    val manicure = stringResource(id = R.string.manicure_text)
    val construcao_civil = stringResource(id = R.string.construcao_civil_text)
    val limpeza_terreno = stringResource(id = R.string.limpeza_terreno_text)
    val refrigeracao = stringResource(id = R.string.refrigeracao_text)

    val carouselItems = remember {
        listOf(
            CarouselImageItem(1, educacao, resourceId = ImageAssets.CATEGORIA_EDUCACAO),
            CarouselImageItem(2, saude, resourceId = ImageAssets.CATEGORIA_SAUDE),
            CarouselImageItem(3, diarista, resourceId = ImageAssets.CATEGORIA_DIARISTA),
            CarouselImageItem(4, manicure, resourceId = ImageAssets.CATEGORIA_MANICURE),
            CarouselImageItem(5, construcao_civil, resourceId = ImageAssets.CATEGORIA_CONSTRUCAO),
            CarouselImageItem(6, limpeza_terreno, resourceId = ImageAssets.CATEGORIA_LIMPEZA_TERRENO),
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
