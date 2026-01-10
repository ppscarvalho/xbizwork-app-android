package com.br.xbizitwork.ui.presentation.features.skills.components

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
import com.br.xbizitwork.ui.presentation.features.skills.state.SkillUiState
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

@Composable
fun SkillsContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    onSaveClick: (List<SkillUiState>) -> Unit
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
                text = "Marque as áreas que você domina",
                fontFamily = poppinsFontFamily,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )

            SkillsContainer(
                modifier = Modifier.fillMaxWidth(),
                onSaveClick = onSaveClick
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
private fun SkillsContentPreview() {
    XBizWorkTheme {
        SkillsContent(
            paddingValues = PaddingValues(),
            onSaveClick = {}
        )
    }
}
