package com.br.xbizitwork.ui.presentation.features.auth.data.usecase

import com.br.xbizitwork.core.usecase.RawFlowUseCase
import com.br.xbizitwork.ui.presentation.features.auth.data.local.model.AuthSession
import com.br.xbizitwork.ui.presentation.features.auth.domain.repository.UserAuthRepository
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.GetAuthSessionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.take
import javax.inject.Inject

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