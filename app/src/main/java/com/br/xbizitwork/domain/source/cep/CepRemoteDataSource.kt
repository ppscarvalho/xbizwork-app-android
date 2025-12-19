package com.br.xbizitwork.domain.source.cep

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.cep.CepModel

/**
 * Interface do Data Source para consulta de CEP
 */
interface CepRemoteDataSource {
    suspend fun getCep(cep: String): DefaultResult<CepModel>
}

