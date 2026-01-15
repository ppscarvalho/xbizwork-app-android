package com.br.xbizitwork.ui.presentation.components.state

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.theme.poppinsFontFamily
import com.br.xbizitwork.R

@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    image: Painter,
    title: String,
    subTitle: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier.background(
            MaterialTheme.colorScheme.background
        )
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .padding(12.dp)
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = title,
                fontFamily = poppinsFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(4.dp))
            subTitle()
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "Light Mode"
)
@Composable
private fun EmptyStateLightPreview() {
    EmptyState(
        image = painterResource(R.drawable.ic_empty_state_recipes),
        title = "Nenhum registro encontrado!",
        subTitle ={
            Text(
                text = "Por favor tente novamente",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "Dark Mode"
)
@Composable
private fun EmptyStatDarkPreview() {
    EmptyState(
        image = painterResource(R.drawable.ic_empty_state_recipes),
        title = "Nenhum registro encontrado!",
        subTitle ={
            Text(
                text = "Por favor tente novamente",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black
            )
        }
    )
}