package com.br.xbizitwork.ui.presentation.components.carousel

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.common.ImageAssets
import com.br.xbizitwork.ui.theme.XBizWorkTheme

/**
 * Item individual do carrossel de portfólio
 * Exibe uma imagem em um Card com bordas arredondadas
 */
@Composable
fun PortfolioItemView(
    imageRes: Int,
    imageHeight: Dp,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Imagem do portfólio",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(imageHeight)
            )
        }
    }
}

@Preview
@Composable
private fun PortfolioItemViewPreview() {
    XBizWorkTheme {
        PortfolioItemView(
            imageRes = ImageAssets.PORTFOLIO_EDUCADOR_1,
            imageHeight = 200.dp
        )
    }
}
