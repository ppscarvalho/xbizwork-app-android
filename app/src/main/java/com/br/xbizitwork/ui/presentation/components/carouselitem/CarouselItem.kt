package com.br.xbizitwork.ui.presentation.components.carouselitem

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.presentation.features.home.state.CarouselImageItem

@Composable
fun CarouselItemView(
    modifier: Modifier = Modifier,
    item: CarouselImageItem,
    imageHeight: Dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(item.resourceId),
            contentDescription = item.label,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = item.label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            color = Color.DarkGray,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}