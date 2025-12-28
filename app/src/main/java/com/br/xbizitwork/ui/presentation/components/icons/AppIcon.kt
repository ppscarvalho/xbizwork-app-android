package com.br.xbizitwork.ui.presentation.components.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import com.br.xbizitwork.R

@Composable
fun AppIcon(
    modifier: Modifier = Modifier
) {
    val icon = if (isSystemInDarkTheme())
        painterResource(id = R.drawable.ic_logo_oficial_sem_texto_inferiror_light)
    else
        painterResource(id = R.drawable.ic_logo_oficial_sem_texto_inferiror_light)

    Image(
        painter = icon,
        contentDescription = "Ícone do App",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(100.dp)
            .shadow(8.dp, CircleShape, clip = false) // ✅ SOMBRA / RELEVO
            .clip(CircleShape)                      // ✅ RECORTE CIRCULAR
            //.border(0.dp, Color.Black, CircleShape)  // ✅ BORDA TEMPORÁRIA
    )
}

