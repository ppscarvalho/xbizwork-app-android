package com.br.xbizitwork.data.repository.cep

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.cep.CepModel
import com.br.xbizitwork.domain.repository.cep.CepRepository
import com.br.xbizitwork.domain.source.cep.CepRemoteDataSource
import javax.inject.Inject

/**
 * Implementação do CepRepository
 */
class CepRepositoryImpl @Inject constructor(
    private val remoteDataSource: CepRemoteDataSource
) : CepRepository {

    override suspend fun getCep(cep: String): DefaultResult<CepModel> {
        return remoteDataSource.getCep(cep)
    }
}

