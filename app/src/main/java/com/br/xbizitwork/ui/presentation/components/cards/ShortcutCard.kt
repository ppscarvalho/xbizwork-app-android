package com.br.xbizitwork.ui.presentation.components.cards

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun ShortcutCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    backgroundColor: Color = Color.White
) {
    Card(
        modifier = modifier
            .width(160.dp)
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center, // ⬅️ Centraliza verticalmente
            horizontalAlignment = Alignment.CenterHorizontally // ⬅️ Centraliza horizontalmente
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = Color(0xFFFF6E10),
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterHorizontally
               ) // ⬅️ Garantia extra
            )
            Spacer(modifier = Modifier.height(8.dp)) // ⬅️ Espaçamento entre ícone e texto
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    title, fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    subtitle, fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Preview (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ShortcutCardPreview () {
    XBizWorkTheme {
        ShortcutCard(
            icon = Icons.Default.Share,
            title = "Divulgue",
            subtitle = "Seu trabalho",
            onClick = { },
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        )
    }
}