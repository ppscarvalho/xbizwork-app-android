package com.br.xbizitwork.domain.usecase.user

import android.os.Build
import androidx.annotation.RequiresApi
import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.data.remote.user.api.UserApiService
import com.br.xbizitwork.data.remote.user.mappers.toModel
import com.br.xbizitwork.domain.model.user.UserModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * UseCase para buscar dados completos do usuário da API
 */
interface GetUserByIdUseCase {
    operator fun invoke(parameters: Parameters): kotlinx.coroutines.flow.Flow<UiState<UserModel>>

    data class Parameters(val userId: Int)
}

class GetUserByIdUseCaseImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : GetUserByIdUseCase, FlowUseCase<GetUserByIdUseCase.Parameters, UserModel>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun executeTask(parameters: GetUserByIdUseCase.Parameters): UiState<UserModel> {
        return try {
            withContext(coroutineDispatcherProvider.io()) {
                val apiResponse = userApiService.getUserById(parameters.userId)

                // ✅ Log da resposta da API
                logInfo("GET_USER_BY_ID", "API Response: isSuccessful=${apiResponse.isSuccessful}, message=${apiResponse.message}")

                // ⚠️ VALIDAÇÃO: data pode ser null!
                if (apiResponse.data == null) {
                    logInfo("GET_USER_BY_ID", "ERRO: apiResponse.data é NULL! isSuccessful=${apiResponse.isSuccessful}, message=${apiResponse.message}")
                    throw IllegalStateException("Dados do usuário não encontrados: ${apiResponse.message}")
                }

                logInfo("GET_USER_BY_ID", "Dados recebidos: name=${apiResponse.data.name}, cpf=${apiResponse.data.cpf}")

                // Extrai o objeto "data" e converte para UserModel
                UiState.Success(apiResponse.data.toModel())
            }
        } catch (e: Exception) {
            logInfo("GET_USER_BY_ID", "Erro ao buscar usuário: ${e.message}")
            UiState.Error(e)
        }
    }
}

