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
    private val httpClient: HttpClient
): CepApiService {
    override suspend fun getCep(cep: String): CepResponse {
        val response = httpClient.get("cep/$cep")
        return response.body()
    }
}

