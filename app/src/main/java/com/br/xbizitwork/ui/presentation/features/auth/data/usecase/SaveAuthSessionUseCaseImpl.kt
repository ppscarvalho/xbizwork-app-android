package com.br.xbizitwork.ui.presentation.features.auth.data.usecase

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.ui.presentation.features.auth.domain.repository.UserAuthRepository
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.SaveAuthSessionUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveAuthSessionUseCaseImpl @Inject constructor(
   private val authRepository: UserAuthRepository,
   private val coroutineDispatcherProvider: CoroutineDispatcherProvider
): SaveAuthSessionUseCase, FlowUseCase<SaveAuthSessionUseCase.Parameters, Unit>() {
    override suspend fun executeTask(parameters: SaveAuthSessionUseCase.Parameters): UiState<Unit> {
        return try {
            withContext(coroutineDispatcherProvider.io()) {
                UiState.Success(authRepository.saveSession(
                    name = parameters.name,
                    email = parameters.email,
                    token = parameters.token))
            }
        }catch (e: Exception){
            UiState.Error(e)
        }
    }
}