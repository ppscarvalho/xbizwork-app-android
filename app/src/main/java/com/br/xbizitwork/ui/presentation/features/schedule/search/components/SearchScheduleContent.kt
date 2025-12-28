package com.br.xbizitwork.ui.presentation.features.schedule.search.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun SearchContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        SearchContainer(
            modifier = Modifier
        )
    }
}

@Preview (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun SearchContentLightPreview() {
    XBizWorkTheme{
        SearchContent(
            modifier = Modifier
        )
    }
}

@Preview (showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SearchContentDarkPreview() {
    XBizWorkTheme{
        SearchContent(
            modifier = Modifier
        )
    }
}