package com.br.xbizitwork.data.remote.cep.api

import com.br.xbizitwork.data.remote.cep.dtos.responses.CepResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

/**
 * Implementação do CepApiService usando Ktor HttpClient
 */
class CepApiServiceImpl @Inject constructor(
    private val client: HttpClient
) : CepApiService {

    /**
     * Busca dados de endereço por CEP
     * Endpoint: GET /api/v1/cep/{cep}
     */
    override suspend fun getCep(cep: String): CepResponse {
        val response = client.get("cep/$cep")
        return response.body()
    }
}

