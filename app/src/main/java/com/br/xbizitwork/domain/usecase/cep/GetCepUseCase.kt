package com.br.xbizitwork.domain.usecase.cep

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.model.cep.CepModel
import com.br.xbizitwork.domain.repository.CepRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para buscar dados de endereço por CEP
 */
interface GetCepUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<CepModel>>

    data class Parameters(val cep: String)
}

/**
 * Implementação do GetCepUseCase
 */
class GetCepUseCaseImpl @Inject constructor(
    private val cepRepository: CepRepository
) : GetCepUseCase, FlowUseCase<GetCepUseCase.Parameters, CepModel>() {

    override suspend fun executeTask(parameters: GetCepUseCase.Parameters): UiState<CepModel> {
        return try {
            logInfo("GET_CEP_USE_CASE", "Buscando CEP: ${parameters.cep}")

            when (val result = cepRepository.getCep(parameters.cep)) {
                is DefaultResult.Success -> {
                    logInfo("GET_CEP_USE_CASE", "CEP encontrado: ${result.data.logradouro}")
                    UiState.Success(result.data)
                }
                is DefaultResult.Error -> {
                    logInfo("GET_CEP_USE_CASE", "Erro ao buscar CEP: ${result.message}")
                    UiState.Error(Exception(result.message))
                }
            }
        } catch (e: Exception) {
            logInfo("GET_CEP_USE_CASE", "Exceção ao buscar CEP: ${e.message}")
            UiState.Error(e)
        }
    }
}

