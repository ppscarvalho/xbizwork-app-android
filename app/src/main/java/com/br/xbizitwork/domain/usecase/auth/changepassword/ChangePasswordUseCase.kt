package com.br.xbizitwork.domain.usecase.auth.changepassword

import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.auth.ChangePasswordModel
import com.br.xbizitwork.domain.repository.UserAuthRepository
import com.br.xbizitwork.domain.result.auth.ChangePasswordResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase responsável por alteração de senha
 * 
 * Responsabilidades:
 * - Obter o usuário autenticado
 * - Validar senha atual (backend)
 * - Aplicar nova senha
 * - Persistir alteração
 * 
 * ⚠️ Validação de senha atual ocorre SOMENTE no backend
 * ⚠️ Nenhuma senha é armazenada em sessão
 * ⚠️ Nenhuma regra de negócio na UI
 */
interface ChangePasswordUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<ChangePasswordResult>>
    data class Parameters(val changePasswordModel: ChangePasswordModel)
}

class ChangePasswordUseCaseImpl @Inject constructor(
    private val authRepository: UserAuthRepository
) : ChangePasswordUseCase, FlowUseCase<ChangePasswordUseCase.Parameters, ChangePasswordResult>() {
    override suspend fun executeTask(parameters: ChangePasswordUseCase.Parameters): UiState<ChangePasswordResult> {
        return try {
            when (val response = authRepository.changePassword(parameters.changePasswordModel)) {
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
