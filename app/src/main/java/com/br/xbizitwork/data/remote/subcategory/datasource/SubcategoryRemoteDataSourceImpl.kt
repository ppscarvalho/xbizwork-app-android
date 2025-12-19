package com.br.xbizitwork.data.remote.subcategory.datasource

import com.br.xbizitwork.core.exceptions.ErrorResponseException
import com.br.xbizitwork.core.network.ErrorMapper
import com.br.xbizitwork.core.network.RetryPolicy
import com.br.xbizitwork.core.network.SimpleCache
import com.br.xbizitwork.core.network.retryWithExponentialBackoff
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.mappers.toSubcategoryResponseModel
import com.br.xbizitwork.data.remote.subcategory.api.SubcategoryApiService
import com.br.xbizitwork.data.remote.subcategory.dtos.response.SubcategoryResponseModel
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

/**
 * Implementação de SubcategoryRemoteDataSource
 */
class SubcategoryRemoteDataSourceImpl @Inject constructor(
    private val subcategoryApiService: SubcategoryApiService
) : SubcategoryRemoteDataSource {

    companion object {
        // Cache para respostas de subcategorias (10 minutos de TTL)
        private val subcategoryCache = SimpleCache<Int, List<SubcategoryResponseModel>>()

        // Política de retry
        private val retryPolicy = RetryPolicy(
            maxRetries = 3,
            initialDelayMs = 100L,
            maxDelayMs = 2000L,
            backoffMultiplier = 2f
        )
    }

    override suspend fun getSubcategoriesByCategory(categoryId: Int): DefaultResult<List<SubcategoryResponseModel>> {
        return try {
            // Tenta obter do cache primeiro
            val cached = subcategoryCache.get(categoryId)
            if (cached != null) {
                return DefaultResult.Success(data = cached)
            }

            // Se não estiver em cache, faz requisição com retry automático
            val response = retryWithExponentialBackoff(
                policy = retryPolicy,
                shouldRetry = { exception ->
                    exception is IOException || exception is TimeoutException
                },
                operation = {
                    subcategoryApiService.getSubcategoriesByCategory(categoryId)
                }
            )

            if (response.isSuccessful && response.data != null) {
                val models = response.data!!.map { it.toSubcategoryResponseModel() }
                subcategoryCache.put(categoryId, models, ttlMs = 10 * 60 * 1000)
                DefaultResult.Success(data = models)
            } else {
                DefaultResult.Error(message = response.message)
            }

        } catch (e: ErrorResponseException) {
            DefaultResult.Error(code = e.error.httpCode.toString(), message = e.error.message)
        } catch (e: Exception) {
            val networkError = ErrorMapper.mapThrowableToNetworkError(e)
            DefaultResult.Error(message = networkError.message)
        }
    }
}
