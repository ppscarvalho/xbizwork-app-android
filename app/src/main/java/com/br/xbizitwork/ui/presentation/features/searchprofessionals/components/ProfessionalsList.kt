package com.br.xbizitwork.ui.presentation.features.searchprofessionals.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.model.professional.SkillInfo
import com.br.xbizitwork.ui.theme.XBizWorkTheme

/**
 * Componente que exibe a lista de profissionais encontrados
 * Usa ProfessionalCard para cada item
 */
@Composable
fun ProfessionalsList(
    professionals: List<ProfessionalSearchBySkill>,
    modifier: Modifier = Modifier,
    onProfessionalClick: (ProfessionalSearchBySkill) -> Unit = {},
    onMapClick: (ProfessionalSearchBySkill) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = professionals,
            key = { professional -> professional.id }
        ) { professional ->
            ProfessionalCard(
                professional = professional,
                modifier = Modifier.clickable {
                    onProfessionalClick(professional)
                },
                onMapClick = onMapClick
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun ProfessionalsListPreview() {
    XBizWorkTheme {
        ProfessionalsList(
            professionals = listOf(
                ProfessionalSearchBySkill(
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
                ProfessionalSearchBySkill(
                    id = 14,
                    name = "Paula Manuela",
                    mobilePhone = "91988776655",
                    city = "Ananindeua",
                    state = "PA",
                    skill = SkillInfo(
                        id = 9,
                        description = "Educador Físico"
                    )
                ),
                ProfessionalSearchBySkill(
                    id = 15,
                    name = "Carlos Silva",
                    mobilePhone = "91977665544",
                    city = "Marituba",
                    state = "PA",
                    skill = SkillInfo(
                        id = 9,
                        description = "Educador Físico"
                    )
                )
            )
        )
    }
}
