package com.br.xbizitwork.ui.presentation.features.auth.domain.usecase

import com.br.xbizitwork.core.state.UiState
import kotlinx.coroutines.flow.Flow

interface RemoveAuthSessionUseCase {
    suspend operator fun invoke(parameters: Unit = Unit): Flow<UiState<Unit>>
}