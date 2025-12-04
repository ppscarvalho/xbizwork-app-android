package com.br.xbizitwork.ui.presentation.features.auth.domain.usecase

import com.br.xbizitwork.core.state.UiState
import kotlinx.coroutines.flow.Flow

interface SaveAuthSessionUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<Unit>>
    data class Parameters(val name: String, val email: String, val token: String)
}