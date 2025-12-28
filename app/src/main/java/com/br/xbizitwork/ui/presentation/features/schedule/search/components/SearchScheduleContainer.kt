package com.br.xbizitwork.ui.presentation.features.schedule.search.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun SearchContainer(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        Text(
            text = "SearchContainer",
            modifier = Modifier
        )
    }
}

@Preview (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SearchContainerLightPreview() {
    XBizWorkTheme{
        SearchContainer(
            modifier = Modifier
        )
    }
}

@Preview (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SearchContainerDarkPreview() {
    XBizWorkTheme{
        SearchContainer(
            modifier = Modifier
        )
    }
}
