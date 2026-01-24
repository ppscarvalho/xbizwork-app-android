package com.br.xbizitwork.ui.presentation.features.searchprofessionals.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.ui.presentation.components.background.AppGradientBackground
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.events.SearchProfessionalBySkillEvent
import com.br.xbizitwork.ui.presentation.features.searchprofessionals.state.SearchProfessionalsUiState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily
import androidx.compose.foundation.layout.PaddingValues

/**
 * Content composable for professional search by skill
 * Following the same pattern as SearchScheduleContent
 */
@Composable
fun SearchProfessionalsContent(
    modifier: Modifier = Modifier,
    uiState: SearchProfessionalsUiState,
    paddingValues: PaddingValues,
    onEvent: (SearchProfessionalBySkillEvent) -> Unit,
    onProfessionalSelected: (ProfessionalSearchBySkill) -> Unit = {}
) {
    AppGradientBackground(
        modifier = modifier,
        paddingValues = paddingValues
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Search bar (sem botão, busca automática)
            SearchProfessionalBar(
                queryTextState = uiState.queryTextState
            )

            // Results container
            SearchProfessionalsContainer(
                modifier = Modifier.fillMaxSize(),
                uiState = uiState,
                onEvent = onEvent,
                onProfessionalSelected = onProfessionalSelected
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
private fun SearchProfessionalsContentPreview() {
    XBizWorkTheme {
        SearchProfessionalsContent(
            uiState = SearchProfessionalsUiState(),
            paddingValues = PaddingValues(0.dp),
            onEvent = {}
        )
    }
}
