package com.br.xbizitwork.domain.usecase.cep

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.model.cep.CepModel
import com.br.xbizitwork.domain.repository.cep.CepRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * UseCase para buscar dados de endereço por CEP
 */
interface GetCepUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<CepModel>>

    data class Parameters(val cep: String)
}

class GetCepUseCaseImpl @Inject constructor(
    private val cepRepository: CepRepository
) : GetCepUseCase {

    override fun invoke(parameters: GetCepUseCase.Parameters): Flow<UiState<CepModel>> = flow {
        emit(UiState.Loading)

        logInfo("GET_CEP_USE_CASE", "Buscando CEP: ${parameters.cep}")

        when (val result = cepRepository.getCep(parameters.cep)) {
            is DefaultResult.Success -> {
                logInfo("GET_CEP_USE_CASE", "CEP encontrado: ${result.data.logradouro}")
                emit(UiState.Success(result.data))
            }
            is DefaultResult.Error -> {
                logInfo("GET_CEP_USE_CASE", "Erro ao buscar CEP: ${result.message}")
                emit(UiState.Error(Exception(result.message)))
            }
        }
    }
}

