package com.br.xbizitwork.ui.presentation.features.searchprofessional.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.presentation.components.background.AppGradientBackground
import com.br.xbizitwork.ui.presentation.features.searchprofessional.events.SearchProfessionalBySkillEvent
import com.br.xbizitwork.ui.presentation.features.searchprofessional.state.SearchProfessionalBySkillUIState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

/**
 * Content component - responsável apenas por layout e scroll
 * Stateless UI rendering only
 */
@Composable
fun SearchProfessionalBySkillContent(
    modifier: Modifier = Modifier,
    uiState: SearchProfessionalBySkillUIState,
    paddingValues: PaddingValues,
    onEvent: (SearchProfessionalBySkillEvent) -> Unit
) {
    AppGradientBackground(
        modifier = modifier,
        paddingValues = paddingValues
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Digite o nome da habilidade para encontrar profissionais",
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )
            
            SearchProfessionalBySkillContainer(
                modifier = Modifier.fillMaxWidth(),
                uiState = uiState,
                onEvent = onEvent
            )
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0f344e
)
@Composable
private fun SearchProfessionalBySkillContentPreview() {
    XBizWorkTheme {
        SearchProfessionalBySkillContent(
            paddingValues = PaddingValues(0.dp),
            uiState = SearchProfessionalBySkillUIState(),
            onEvent = {}
        )
    }
}
