package com.br.xbizitwork.ui.presentation.features.professionalprofile.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.model.professional.SkillInfo
import com.br.xbizitwork.ui.presentation.common.ImageAssets
import com.br.xbizitwork.ui.presentation.components.carousel.PortfolioCarousel
import com.br.xbizitwork.ui.presentation.features.professionalprofile.events.ProfessionalProfileEvent
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

@Composable
fun ProfessionalProfileContainer(
    modifier: Modifier = Modifier,
    professional: ProfessionalSearchBySkill,
    onEvent: (ProfessionalProfileEvent) -> Unit
) {
    var showContactDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfessionalProfileHeader(
            professional = professional
        )

        ProfessionalProfileContactInfo(
            professional = professional
        )

        // Seção de Portfólio
        Text(
            text = "Trabalhos realizados",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            fontFamily = poppinsFontFamily,
            modifier = Modifier.padding(top = 8.dp)
        )

        PortfolioCarousel(
            images = getPortfolioImages(professional.skill.name),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { showContactDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.Call,
                contentDescription = "Contactar",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Contactar",
                fontFamily = poppinsFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }

    ContactConfirmationDialog(
        showDialog = showContactDialog,
        onConfirm = {
            showContactDialog = false
            onEvent(ProfessionalProfileEvent.OnContactClick)
        },
        onDismiss = {
            showContactDialog = false
        }
    )
}

/**
 * Helper function para retornar imagens de portfólio baseado na skill
 * Por enquanto usa imagens mock da pasta drawable
 */
private fun getPortfolioImages(skillName: String): List<Int> {
    return when {
        skillName.contains("Educador", ignoreCase = true) -> listOf(
            ImageAssets.PORTFOLIO_EDUCADOR_1,
            ImageAssets.PORTFOLIO_EDUCADOR_2,
            ImageAssets.PORTFOLIO_EDUCADOR_3
        )
        skillName.contains("Manicure", ignoreCase = true) ||
        skillName.contains("Manicura", ignoreCase = true) -> listOf(
            ImageAssets.PORTFOLIO_MANICURE_1,
            ImageAssets.PORTFOLIO_MANICURE_2,
            ImageAssets.PORTFOLIO_MANICURE_3
        )
        else -> listOf(
            ImageAssets.PORTFOLIO_EDUCADOR_1,
            ImageAssets.PORTFOLIO_EDUCADOR_2
        )
    }
}

@Preview (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ProfessionalProfileContainerPreview() {
    XBizWorkTheme{
        ProfessionalProfileContainer(
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
            onEvent = {}
        )
    }
}