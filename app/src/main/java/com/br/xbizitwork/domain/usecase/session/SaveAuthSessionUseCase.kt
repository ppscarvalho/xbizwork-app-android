package com.br.xbizitwork.domain.usecase.session

import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.repository.auth.UserAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SaveAuthSessionUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<Unit>>
    data class Parameters(val name: String, val email: String, val token: String)
}

class SaveAuthSessionUseCaseImpl @Inject constructor(
    private val authRepository: UserAuthRepository,
) : SaveAuthSessionUseCase, FlowUseCase<SaveAuthSessionUseCase.Parameters, Unit>() {
    override suspend fun executeTask(parameters: SaveAuthSessionUseCase.Parameters): UiState<Unit> {
        return try {
            UiState.Success(
                authRepository.saveSession(
                    name = parameters.name,
                    email = parameters.email,
                    token = parameters.token
                )
            )
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
