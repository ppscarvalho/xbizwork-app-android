package com.br.xbizitwork.ui.presentation.features.searchprofessionals.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CleanSearchButton(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onClearClick: () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible
    ) {
        Icon(
            imageVector = Icons.Outlined.DeleteOutline,
            contentDescription = "Limpar busca",
            modifier = modifier.clickable(onClick = onClearClick),
            tint = Color(0xFF212121) // Preto/cinza muito escuro fixo
        )
    }
}

