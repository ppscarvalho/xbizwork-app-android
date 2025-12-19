package com.br.xbizitwork.data.remote.cep.api

import com.br.xbizitwork.data.remote.cep.dtos.responses.CepResponse

/**
 * Interface do serviço de API para consulta de CEP
 */
interface CepApiService {
    /**
     * Busca dados de endereço por CEP
     * Endpoint: GET /api/v1/cep/{cep}
     */
    suspend fun getCep(cep: String): CepResponse
}

