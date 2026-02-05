package com.br.xbizitwork.ui.presentation.components.bottomsheet

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.model.professional.SkillInfo
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

/**
 * BottomSheet para visualização rápida de informações do profissional
 * Usado quando o usuário clica em um marcador não selecionado no mapa
 *
 * Mostra informações básicas:
 * - Nome completo
 * - Habilidade/Especialidade
 * - Telefone parcialmente mascarado (privacidade)
 * - Localização (Cidade - Estado)
 * - Botão "Ver Perfil" para navegação completa
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalQuickViewBottomSheet(
    professional: ProfessionalSearchBySkill?,
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onViewProfile: (ProfessionalSearchBySkill) -> Unit
) {
    if (!isVisible || professional == null) return

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Nome e habilidade
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = null,
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

                Text(
                    text = professional.skill.description,
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 32.dp)
                )
            }

            HorizontalDivider()

            // Telefone parcial
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
                        text = maskPhone(professional.mobilePhone),
                        fontFamily = poppinsFontFamily,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

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

            // Botão Ver Perfil
            Button(
                onClick = { onViewProfile(professional) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Ver Perfil",
                    fontFamily = poppinsFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

/**
 * Mascara o telefone mostrando apenas parte dos números
 * Protege a privacidade do profissional até que o usuário acesse o perfil completo
 *
 * Exemplo: (91) 99999-9999 → (91) 99999-****
 */
private fun maskPhone(phone: String): String {
    return if (phone.length > 4) {
        phone.substring(0, phone.length - 4) + "****"
    } else {
        phone
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
private fun ProfessionalQuickViewBottomSheetPreview() {
    XBizWorkTheme {
        ProfessionalQuickViewBottomSheet(
            professional = ProfessionalSearchBySkill(
                id = 14,
                name = "Paula Manuela",
                mobilePhone = "(91) 99999-9999",
                city = "Belém",
                state = "PA",
                latitude = -1.4566499,
                longitude = -48.4827653,
                skill = SkillInfo(9, "Educador Físico")
            ),
            isVisible = true,
            onDismiss = {},
            onViewProfile = {}
        )
    }
}
