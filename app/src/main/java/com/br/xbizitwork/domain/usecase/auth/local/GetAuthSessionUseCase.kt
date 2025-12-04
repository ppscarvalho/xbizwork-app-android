package com.br.xbizitwork.domain.usecase.auth.local

import com.br.xbizitwork.core.usecase.RawFlowUseCase
import com.br.xbizitwork.data.local.auth.model.AuthSession
import com.br.xbizitwork.domain.repository.auth.UserAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.take
import javax.inject.Inject

interface GetAuthSessionUseCase{
    suspend operator fun invoke(parameters: Unit = Unit): Flow<AuthSession>
}

class GetAuthSessionUseCaseImpl @Inject constructor(
    private val authRepository: UserAuthRepository
) :  GetAuthSessionUseCase, RawFlowUseCase<Unit, AuthSession>() {
    override suspend fun executeTaskFlow(parameters: Unit): Flow<AuthSession> {
        return authRepository.observeSession()
            .catch { error ->
                emit(AuthSession(errorMessage = error.message ?: "Erro desconhecido"))
            }.take(1)
    }
}