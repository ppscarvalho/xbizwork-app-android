package com.br.xbizitwork.domain.usecase.user

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.user.UserModel
import com.br.xbizitwork.domain.repository.UserRepository

interface GetUserByIdUseCase{
    operator fun invoke(parameters: Parameters): kotlinx.coroutines.flow.Flow<UiState<UserModel>>
    data class Parameters(val userId: Int)
}

class GetUserByIdUseCaseImpl(
    private val repository: UserRepository,
): GetUserByIdUseCase, FlowUseCase<GetUserByIdUseCase.Parameters, UserModel>() {
    override suspend fun executeTask(parameters: GetUserByIdUseCase.Parameters): UiState<UserModel> {
        return try {
            when (val response = repository.getUserById(parameters.userId)) {
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