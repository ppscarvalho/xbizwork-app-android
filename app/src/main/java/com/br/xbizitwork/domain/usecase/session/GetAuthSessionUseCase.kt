package com.br.xbizitwork.domain.usecase.session

import com.br.xbizitwork.core.usecase.RawFlowUseCase
import com.br.xbizitwork.domain.repository.UserAuthRepository
import com.br.xbizitwork.domain.session.AuthSession
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

interface GetAuthSessionUseCase {
    suspend operator fun invoke(parameters: Unit = Unit): Flow<AuthSession>
}

class GetAuthSessionUseCaseImpl @Inject constructor(
    private val authRepository: UserAuthRepository
) : GetAuthSessionUseCase, RawFlowUseCase<Unit, AuthSession>() {
    override suspend fun executeTaskFlow(parameters: Unit): Flow<AuthSession> {
        return authRepository.observeSession()
            .catch { error ->
                emit(AuthSession(errorMessage = error.message ?: "Erro desconhecido"))
            }
    }
}
