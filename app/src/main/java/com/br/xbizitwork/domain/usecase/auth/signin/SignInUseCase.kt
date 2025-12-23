package com.br.xbizitwork.domain.usecase.auth.signin

import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.auth.SignInModel
import com.br.xbizitwork.domain.repository.UserAuthRepository
import com.br.xbizitwork.domain.result.auth.SignInResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface SignInUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<SignInResult>>
    data class Parameters(val signInModel: SignInModel)
}

class SignInUseCaseImpl @Inject constructor(
    private val authRepository: UserAuthRepository,
) : SignInUseCase, FlowUseCase<SignInUseCase.Parameters, SignInResult>() {
    override suspend fun executeTask(parameters: SignInUseCase.Parameters): UiState<SignInResult> {
        return try {
            when (val response = authRepository.signIn(parameters.signInModel)) {
                is DefaultResult.Success -> {
                    UiState.Success(response.data)
                }

                is DefaultResult.Error -> {
                    UiState.Error(Throwable(response.message))
                }
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
