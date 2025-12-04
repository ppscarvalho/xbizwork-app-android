package com.br.xbizitwork.ui.presentation.features.auth.data.usecase

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.response.SignInResponseModel
import com.br.xbizitwork.ui.presentation.features.auth.domain.repository.UserAuthRepository
import com.br.xbizitwork.ui.presentation.features.auth.domain.usecase.SignInUseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignInUseCaseImpl @Inject constructor(
    private val authRepository: UserAuthRepository,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : SignInUseCase, FlowUseCase<SignInUseCase.Parameters, SignInResponseModel>() {
    override suspend fun executeTask(parameters: SignInUseCase.Parameters): UiState<SignInResponseModel> {
        return try {
            withContext(coroutineDispatcherProvider.io()) {
                when (val response = authRepository.signIn(parameters.signInRequestModel)) {
                    is DefaultResult.Success -> {
                        UiState.Success(response.data)
                    }

                    is DefaultResult.Error -> {
                        UiState.Error(Throwable(response.message))
                    }
                }
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}

