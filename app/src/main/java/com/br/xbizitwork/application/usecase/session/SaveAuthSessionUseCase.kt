package com.br.xbizitwork.application.usecase.session

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.repository.auth.UserAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SaveAuthSessionUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<Unit>>
    data class Parameters(val name: String, val email: String, val token: String)
}

class SaveAuthSessionUseCaseImpl @Inject constructor(
    private val authRepository: UserAuthRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
): SaveAuthSessionUseCase, FlowUseCase<SaveAuthSessionUseCase.Parameters, Unit>() {
    override suspend fun executeTask(parameters: SaveAuthSessionUseCase.Parameters): UiState<Unit> {
        return try {
            withContext(coroutineDispatcherProvider.io()) {
                UiState.Success(
                    authRepository.saveSession(
                        name = parameters.name,
                        email = parameters.email,
                        token = parameters.token
                    )
                )
            }
        }catch (e: Exception){
            UiState.Error(e)
        }
    }
}