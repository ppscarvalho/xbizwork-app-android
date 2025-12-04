package com.br.xbizitwork.ui.presentation.features.auth.data.usecase

import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.RawFlowUseCase
import com.br.xbizitwork.ui.presentation.features.auth.domain.repository.UserAuthRepository
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.RemoveAuthSessionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoveAuthSessionUseCaseImpl @Inject constructor(
    private val authRepository: UserAuthRepository,
): RemoveAuthSessionUseCase, RawFlowUseCase<Unit, UiState<Unit>>() {
    override suspend fun executeTaskFlow(parameters: Unit): Flow<UiState<Unit>> {
        return flow {
            try {
                emit(UiState.Success(authRepository.clearSession()))
            } catch (e: Exception){
                UiState.Error(e)
            }
        }
    }
}