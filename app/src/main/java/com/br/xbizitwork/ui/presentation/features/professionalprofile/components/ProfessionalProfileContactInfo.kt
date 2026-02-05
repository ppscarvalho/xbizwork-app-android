
package com.br.xbizitwork.ui.presentation.features.professionalprofile.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.model.professional.SkillInfo
import com.br.xbizitwork.ui.theme.poppinsFontFamily

@Composable
fun ProfessionalProfileContactInfo(
    professional: ProfessionalSearchBySkill
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Informações de Contato",
                fontFamily = poppinsFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Telefone
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Phone,
                    contentDescription = "Telefone",
                    tint = MaterialTheme.colorScheme.primary
                )
                Column {
                    Text(
                        text = "Telefone",
                        fontFamily = poppinsFontFamily,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = professional.mobilePhone,
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            HorizontalDivider()

            // Localização
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Localização",
                    tint = MaterialTheme.colorScheme.primary
                )
                Column {
                    Text(
                        text = "Localização",
                        fontFamily = poppinsFontFamily,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "${professional.city} - ${professional.state}",
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfessionalProfileContactInfoPreview() {
    val professionalPreview = ProfessionalSearchBySkill(
        mobilePhone = "(91) 99123-4567",
        city = "Belém",
        state = "PA",
        skill = SkillInfo(9, "Educador Físico"),
        name = "Paula Manuela",
        id = 14
    )

    MaterialTheme {
        ProfessionalProfileContactInfo(
            professional = professionalPreview
        )
    }
}