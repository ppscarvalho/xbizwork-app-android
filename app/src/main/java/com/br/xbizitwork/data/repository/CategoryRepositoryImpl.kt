package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.mappers.toDomainResult
import com.br.xbizitwork.data.remote.category.datasource.CategoryRemoteDataSource
import com.br.xbizitwork.domain.repository.CategoryRepository
import com.br.xbizitwork.domain.result.category.CategoryResult
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementação de CategoryRepository
 *
 * Responsabilidades:
 * - Converter DefaultResult<List<CategoryResponseModel>> do RemoteDataSource
 *   em DefaultResult<CategoryResult> para a camada de Domain
 * - Aplicar mapeamento de ResponseModel → DomainResult
 * - Executar em thread IO via CoroutineDispatcher
 *
 * Padrão de fluxo:
 * 1. RemoteDataSource retorna DefaultResult<List<CategoryResponseModel>>
 * 2. Repository mapeia para DefaultResult<CategoryResult>
 * 3. UseCase recebe DefaultResult<CategoryResult>
 * 4. Presentation recebe dados formatados para UI
 */
class CategoryRepositoryImpl @Inject constructor(
    private val remoteDataSource: CategoryRemoteDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : CategoryRepository {

    override suspend fun getAllCategory(parameters: Unit): DefaultResult<List<CategoryResult>> =
        withContext(coroutineDispatcherProvider.io()) {
            when (val result = remoteDataSource.getAllCategory()) {
                is DefaultResult.Success ->
                    DefaultResult.Success(result.data.map { it.toDomainResult() })
                is DefaultResult.Error ->
                    DefaultResult.Error(message = result.message)
            }
        }
}
