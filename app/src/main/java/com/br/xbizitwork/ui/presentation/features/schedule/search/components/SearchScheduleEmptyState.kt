package com.br.xbizitwork.ui.presentation.features.schedule.search.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.br.xbizitwork.ui.presentation.components.state.EmptyState
import com.br.xbizitwork.R
import com.br.xbizitwork.ui.theme.poppinsFontFamily

@Composable
fun SearchScheduleEmptyState(
    modifier: Modifier = Modifier
) {
    EmptyState(
        image = painterResource(R.drawable.ic_empty_state_recipes),
        title = stringResource(id = R.string.empty_schedule_text),
        subTitle = {
            Text(
                text = stringResource(R.string.try_again_text),
                fontFamily = poppinsFontFamily,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        modifier = modifier
    )
}