package com.br.xbizitwork.ui.presentation.features.professionalprofile.screen

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.model.professional.SkillInfo
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.professionalprofile.components.ProfessionalProfileContent
import com.br.xbizitwork.ui.presentation.features.professionalprofile.events.ProfessionalProfileEvent
import com.br.xbizitwork.ui.presentation.features.professionalprofile.state.ProfessionalProfileUiState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Screen composable for professional profile
 * Following the same pattern as SearchProfessionalsScreen
 */
@Composable
fun ProfessionalProfileScreen(
    uiState: ProfessionalProfileUiState,
    sideEffectFlow: Flow<AppSideEffect>,
    onEvent: (ProfessionalProfileEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current

    // Tratamento de SideEffects
    LifecycleEventEffect(sideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.OpenExternalUrl -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sideEffect.url))
                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Erro ao abrir WhatsApp. Certifique-se de que está instalado.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            is AppSideEffect.ShowToast -> {
                Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
            }
            else -> { /* Outros side effects ignorados */ }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Perfil do Profissional",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = onNavigateBack
            )
        },
        content = { paddingValues ->
            ProfessionalProfileContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onEvent = onEvent
            )
        }
    )
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_4
)
@Composable
private fun ProfessionalProfileScreenPreview() {
    XBizWorkTheme {
        ProfessionalProfileScreen(
            uiState = ProfessionalProfileUiState(
                professional = ProfessionalSearchBySkill(
                    id = 14,
                    name = "Paula Manuela",
                    mobilePhone = "(91) 99999-9999",
                    city = "Belém",
                    state = "PA",
                    latitude = -1.4566499,
                    longitude = -48.4827653,
                    skill = SkillInfo(9, "Educador Físico")
                )
            ),
            sideEffectFlow = flowOf(),
            onEvent = {},
            onNavigateBack = {}
        )
    }
}
