package com.br.xbizitwork.ui.presentation.features.schedule.list.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.br.xbizitwork.ui.presentation.components.state.EmptyState
import com.br.xbizitwork.R

@Composable
fun ListScheduleEmptyState(
    modifier: Modifier = Modifier
) {
    EmptyState(
        image = painterResource(R.drawable.ic_empty_state_recipes),
        title = stringResource(id = R.string.empty_schedule_text),
        modifier = modifier
    )
}