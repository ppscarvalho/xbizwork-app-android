package com.br.xbizitwork.domain.usecase.session

import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.RawFlowUseCase
import com.br.xbizitwork.domain.repository.auth.UserAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface RemoveAuthSessionUseCase {
    suspend operator fun invoke(parameters: Unit = Unit): Flow<UiState<Unit>>
}

class RemoveAuthSessionUseCaseImpl @Inject constructor(
    private val authRepository: UserAuthRepository,
) : RemoveAuthSessionUseCase, RawFlowUseCase<Unit, UiState<Unit>>() {
    override suspend fun executeTaskFlow(parameters: Unit): Flow<UiState<Unit>> {
        return flow {
            try {
                emit(UiState.Success(authRepository.clearSession()))
            } catch (e: Exception) {
                emit(UiState.Error(e))
            }
        }
    }
}
