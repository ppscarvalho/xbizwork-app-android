package com.br.xbizitwork.ui.presentation.features.home.models

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color

data class CarouselImageItem(
    val id: Int,
    val label: String,
    @DrawableRes val resourceId: Int
)
