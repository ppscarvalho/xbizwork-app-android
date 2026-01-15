package com.br.xbizitwork.ui.presentation.features.searchprofessionalbyskill.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.model.professional.SkillInfo
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

@Composable
fun ProfessionalCard(
    professional: ProfessionalSearchBySkill,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Name
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Professional",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = professional.name,
                    fontFamily = poppinsFontFamily,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            // Skill
            Text(
                text = professional.skill.description,
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 32.dp)
            )

            // Phone
            ProfessionalInfoRow(
                icon = Icons.Default.Phone,
                label = "Telefone",
                value = professional.mobilePhone
            )

            // Location
            ProfessionalInfoRow(
                icon = Icons.Default.LocationOn,
                label = "Localização",
                value = "${professional.city} - ${professional.state}"
            )
        }
    }
}

@Composable
private fun ProfessionalInfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontFamily = poppinsFontFamily,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ProfessionalCardPreview() {
    XBizWorkTheme {
        ProfessionalCard(
            professional = ProfessionalSearchBySkill(
                id = 13,
                name = "Pedro Carvalho",
                mobilePhone = "91992511848",
                city = "Belém",
                state = "PA",
                skill = SkillInfo(
                    id = 9,
                    description = "Educador Físico"
                )
            ),
            modifier = Modifier.padding(16.dp)
        )
    }
}
