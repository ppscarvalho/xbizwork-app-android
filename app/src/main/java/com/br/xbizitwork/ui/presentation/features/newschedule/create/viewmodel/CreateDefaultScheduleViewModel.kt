package com.br.xbizitwork.ui.presentation.features.newschedule.create.viewmodel

import androidx.lifecycle.ViewModel
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.data.local.auth.datastore.AuthSessionLocalDataSource
import com.br.xbizitwork.ui.presentation.features.newschedule.create.events.CreateDefaultScheduleEvent
import com.br.xbizitwork.ui.presentation.features.newschedule.create.state.CreateDefaultScheduleUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class CreateDefaultScheduleViewModel @Inject constructor(
    private val authSessionLocalDataSource: AuthSessionLocalDataSource
): ViewModel() {
    private val _uiState: MutableStateFlow<CreateDefaultScheduleUIState> = MutableStateFlow(CreateDefaultScheduleUIState())
    val uiState: StateFlow<CreateDefaultScheduleUIState> = _uiState.asStateFlow()

    private val _appSideEffectChannel = Channel<AppSideEffect>()
    val sideEffectChannel = _appSideEffectChannel.receiveAsFlow()

    fun onEvent(event: CreateDefaultScheduleEvent) {
        // Handle events here and update _uiState or send side effects via _appSideEffectChannel
    }
}