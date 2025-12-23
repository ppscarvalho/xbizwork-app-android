package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.cep.CepModel

/**
 * Interface do Repository para consulta de CEP
 */
interface CepRepository {
    suspend fun getCep(cep: String): DefaultResult<CepModel>
}