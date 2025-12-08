package com.br.xbizitwork.ui.presentation.features.home.models

import androidx.annotation.DrawableRes

data class CarouselImageItem(
    val id: Int,
    val label: String,
    @DrawableRes val resourceId: Int
)
