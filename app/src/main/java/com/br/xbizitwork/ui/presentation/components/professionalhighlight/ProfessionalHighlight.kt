package com.br.xbizitwork.ui.presentation.components.professionalhighlight

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFOntFamily
import com.example.xbizitwork.R

@Composable
fun ProfessionalHighlight(
    name: String,
    role: String,
    recommendations: Int,
    rating: Float,
    @DrawableRes imageRes: Int,
    backgroundColor: Color = Color.White,
    onProfileClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 12.dp,
                vertical = 2.dp
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick = onProfileClick
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor) // ✅ Aqui está o fundo aplicado
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = name,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        name,
                        fontWeight = FontWeight.Medium,
                        style = MaterialTheme.typography.titleMedium,
                        fontFamily = poppinsFOntFamily,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        "⭐️ $rating ($recommendations)",
                        fontSize = 11.sp,
                        fontFamily = poppinsFOntFamily
                        )
                }
                Text(role, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}

@Preview
@Composable
private fun ProfessionalHighlightComponentPreview() {
    XBizWorkTheme {
        ProfessionalHighlight(
            name = "Helena de Jesus",
            role = "Diarista",
            recommendations = 10,
            rating = 4.5f,
            imageRes = R.drawable.avatar_1,
            backgroundColor = Color(0xFFC0DEDD),
            onProfileClick = ({})
        )
    }
}