package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.cep.CepModel
import com.br.xbizitwork.domain.repository.CepRepository
import com.br.xbizitwork.data.remote.cep.datasource.CepRemoteDataSource
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementação do CepRepository
 */
class CepRepositoryImpl @Inject constructor(
    private val remoteDataSource: CepRemoteDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : CepRepository {
    override suspend fun getCep(cep: String): DefaultResult<CepModel> =
        withContext(coroutineDispatcherProvider.io()) {
            remoteDataSource.getCep(cep)
        }
}