package com.br.xbizitwork.ui.presentation.features.schedule.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.theme.XBizWorkTheme

@Composable
fun SearchScheduleBar(
    modifier: Modifier = Modifier,
    queryTextState: TextFieldState
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        SearchTextField(
            queryTextState = queryTextState,
            modifier = Modifier.weight(1f)
        )

        CleanButton(
            isVisible = queryTextState.text.isNotBlank(),
            onClearClick = { queryTextState.edit { delete(0, length) }}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchScheduleBarPreview() {
    XBizWorkTheme{
        SearchScheduleBar(
            queryTextState = TextFieldState("Buscar")
        )
    }
}