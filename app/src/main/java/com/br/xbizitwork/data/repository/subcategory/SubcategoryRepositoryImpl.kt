package com.br.xbizitwork.data.repository.subcategory

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.mappers.toDomainResult
import com.br.xbizitwork.data.remote.subcategory.datasource.SubcategoryRemoteDataSource
import com.br.xbizitwork.domain.common.DomainDefaultResult
import com.br.xbizitwork.domain.repository.SubcategoryRepository
import com.br.xbizitwork.domain.result.subcategory.SubcategoryResult
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementação de SubcategoryRepository
 */
class SubcategoryRepositoryImpl @Inject constructor(
    private val remoteDataSource: SubcategoryRemoteDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : SubcategoryRepository {

    override suspend fun getSubcategoriesByCategory(categoryId: Int): DomainDefaultResult<List<SubcategoryResult>> =
        withContext(coroutineDispatcherProvider.io()) {
            val result = remoteDataSource.getSubcategoriesByCategory(categoryId)

            when (result) {
                is DefaultResult.Success -> {
                    val domainResults = result.data.map { it.toDomainResult() }
                    DomainDefaultResult.Success(data = domainResults)
                }
                is DefaultResult.Error -> {
                    DomainDefaultResult.Error(message = result.message)
                }
            }
        }
}
