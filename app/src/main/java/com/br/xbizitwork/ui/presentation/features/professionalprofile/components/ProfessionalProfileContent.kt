package com.br.xbizitwork.ui.presentation.features.professionalprofile.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.model.professional.SkillInfo
import com.br.xbizitwork.ui.presentation.components.background.AppGradientBackground
import com.br.xbizitwork.ui.presentation.features.professionalprofile.events.ProfessionalProfileEvent
import com.br.xbizitwork.ui.presentation.features.professionalprofile.state.ProfessionalProfileUiState
import com.br.xbizitwork.ui.theme.XBizWorkTheme

/**
 * Content composable for professional profile screen
 * Responsável apenas por layout e composição visual
 */
@Composable
fun ProfessionalProfileContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    uiState: ProfessionalProfileUiState,
    onEvent: (ProfessionalProfileEvent) -> Unit
) {
    AppGradientBackground(
        modifier = modifier,
        paddingValues = paddingValues
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                uiState.errorMessage != null -> {
                    Text(
                        text = uiState.errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }

                uiState.professional != null -> {
                    ProfessionalProfileContainer(
                        professional = uiState.professional,
                        onEvent = onEvent
                    )
                }

                else -> {
                    Text(
                        text = "Nenhum profissional selecionado",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun ProfessionalProfileContentPreview() {
    XBizWorkTheme {
        ProfessionalProfileContent(
            paddingValues = PaddingValues(0.dp),
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
            onEvent = {}
        )
    }
}

