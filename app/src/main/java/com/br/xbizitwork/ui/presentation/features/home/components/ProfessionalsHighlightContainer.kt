package com.br.xbizitwork.ui.presentation.features.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.common.ImageAssets
import com.br.xbizitwork.ui.presentation.components.professionalhighlight.ProfessionalHighlight
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun ProfessionalsHighlightContainer(
    modifier: Modifier = Modifier,
    onProfileClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        ProfessionalHighlight(
            name = "João Silva",
            role = "Professor",
            recommendations = 10,
            rating = 4.5f,
            imageRes = ImageAssets.AVATAR_1,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            onProfileClick = onProfileClick
        )

        ProfessionalHighlight(
            name = "Fernanda Souza",
            role = "Enfermeira",
            recommendations = 10,
            rating = 4.5f,
            imageRes = ImageAssets.AVATAR_4,
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            onProfileClick = onProfileClick
        )

        ProfessionalHighlight(
            name = "Maria Ferreira",
            role = "Manicure",
            recommendations = 10,
            rating = 4.5f,
            imageRes = ImageAssets.AVATAR_1,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            onProfileClick = onProfileClick
        )
    }
}

@Preview
@Composable
private fun ProfessionalsHighlightContainerPreview() {
    XBizWorkTheme {
        ProfessionalsHighlightContainer(
            modifier = Modifier.fillMaxWidth(),
            onProfileClick = {}
        )
    }
}
