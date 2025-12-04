package com.br.xbizitwork.ui.presentation.features.auth.domain.usecase

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignUpRequestModel
import kotlinx.coroutines.flow.Flow

/**
 * Caso de uso responsável pelo cadastro de um novo usuário (Sign Up).
 *
 * Este contrato define a execução do fluxo de cadastro, retornando um [Flow]
 * que emite estados de UI através de [UiState], como:
 * - Loading: enquanto a operação está em andamento
 * - Success: quando o cadastro é realizado com sucesso
 * - Error: quando ocorre erro de negócio ou técnico
 */
interface SignUpUseCase {
    /**
     * Executa o fluxo de cadastro do usuário.
     *
     * @param parameters Parâmetros necessários para realizar o Sign Up.
     * @return Flow contendo o estado da operação encapsulado em [UiState].
     */
    operator fun invoke(parameters: Parameters): Flow<UiState<ApiResultModel>>
    /**
     * Parâmetros necessários para o cadastro do usuário.
     *
     * @property signUpRequestModel Dados de entrada para criação da conta.
     */
    data class Parameters(
        val signUpRequestModel: SignUpRequestModel
    )
}

