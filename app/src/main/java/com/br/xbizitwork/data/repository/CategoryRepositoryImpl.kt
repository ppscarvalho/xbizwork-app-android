package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.mappers.toDomainResult
import com.br.xbizitwork.data.remote.category.datasource.CategoryRemoteDataSource
import com.br.xbizitwork.domain.common.DomainDefaultResult
import com.br.xbizitwork.domain.repository.CategoryRepository
import com.br.xbizitwork.domain.result.category.CategoryResult
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementação de CategoryRepository
 *
 * Responsabilidades:
 * - Converter DefaultResult<List<CategoryResponseModel>> do RemoteDataSource
 *   em DomainDefaultResult<CategoryResult> para a camada de Domain
 * - Aplicar mapeamento de ResponseModel → DomainResult
 * - Executar em thread IO via CoroutineDispatcher
 *
 * Padrão de fluxo:
 * 1. RemoteDataSource retorna DefaultResult<List<CategoryResponseModel>>
 * 2. Repository mapeia para DomainDefaultResult<CategoryResult>
 * 3. UseCase recebe DomainDefaultResult<CategoryResult>
 * 4. Presentation recebe dados formatados para UI
 */
class CategoryRepositoryImpl @Inject constructor(
    private val remoteDataSource: CategoryRemoteDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : CategoryRepository {

    override suspend fun getAllCategory(parameters: Unit): DomainDefaultResult<List<CategoryResult>> =
        withContext(coroutineDispatcherProvider.io()) {
            val result = remoteDataSource.getAllCategory()

            when (result) {
                is DefaultResult.Success -> {
                    // Mapeia List<CategoryResponseModel> → List<CategoryResult>
                    val domainResults = result.data.map { it.toDomainResult() }
                    DomainDefaultResult.Success(data = domainResults)
                }
                is DefaultResult.Error -> {
                    DomainDefaultResult.Error(message = result.message)
                }
            }
        }
}