package com.br.xbizitwork.domain.usecase.auth.sign

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponseModel
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequestModel
import com.br.xbizitwork.domain.repository.auth.UserAuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface SignInUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<SignInResponseModel>>
    data class Parameters(val signInRequestModel: SignInRequestModel)
}

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