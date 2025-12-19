package com.br.xbizitwork.data.remote.cep.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.data.remote.cep.api.CepApiService
import com.br.xbizitwork.data.remote.cep.mappers.toModel
import com.br.xbizitwork.domain.model.cep.CepModel
import com.br.xbizitwork.domain.source.cep.CepRemoteDataSource
import javax.inject.Inject

/**
 * Implementação do CepRemoteDataSource
 */
class CepRemoteDataSourceImpl @Inject constructor(
    private val cepApiService: CepApiService
) : CepRemoteDataSource {

    override suspend fun getCep(cep: String): DefaultResult<CepModel> {
        return try {
            logInfo("CEP_DATA_SOURCE", "Buscando CEP: $cep")

            val response = cepApiService.getCep(cep)

            // Valida se os campos obrigatórios vieram preenchidos
            if (response.zipCode.isNullOrBlank() ||
                response.address.isNullOrBlank() ||
                response.city.isNullOrBlank()) {
                logInfo("CEP_DATA_SOURCE", "CEP não encontrado ou incompleto: $cep")
                return DefaultResult.Error(message = "CEP não encontrado")
            }

            val cepModel = response.toModel()
            logInfo("CEP_DATA_SOURCE", "CEP encontrado: ${cepModel.logradouro}, ${cepModel.bairro}, ${cepModel.cidade}/${cepModel.estado}")

            DefaultResult.Success(cepModel)

        } catch (e: Exception) {
            logInfo("CEP_DATA_SOURCE", "Erro ao buscar CEP: ${e.message}")
            DefaultResult.Error(message = e.message ?: "Erro ao buscar CEP")
        }
    }
}

