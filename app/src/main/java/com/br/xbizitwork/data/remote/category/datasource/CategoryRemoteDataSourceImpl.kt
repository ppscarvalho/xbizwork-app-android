package com.br.xbizitwork.data.remote.category.datasource

import com.br.xbizitwork.core.exceptions.ErrorResponseException
import com.br.xbizitwork.core.network.ErrorMapper
import com.br.xbizitwork.core.network.RetryPolicy
import com.br.xbizitwork.core.network.SimpleCache
import com.br.xbizitwork.core.network.retryWithExponentialBackoff
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.mappers.toCategoryModel
import com.br.xbizitwork.data.model.category.CategoryModel
import com.br.xbizitwork.data.remote.category.api.CategoryApiService
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

/**
 * Implementação de CategoryRemoteDataSource
 *
 * Responsabilidades:
 * - Fazer chamadas HTTP para API de categorias
 * - Tratamento de erros (HTTP, conexão, timeout)
 * - Retry automático com backoff exponencial
 * - Cache de respostas para evitar múltiplas requisições
 * - Mapeamento de exceções para DefaultResult
 * - Conversão de CategoryResponse → CategoryModel
 */
class CategoryRemoteDataSourceImpl @Inject constructor(
    private val categoryApiService: CategoryApiService
) : CategoryRemoteDataSource {

    companion object {
        // Cache para respostas de categorias (10 minutos de TTL)
        private val categoryCache = SimpleCache<String, List<CategoryModel>>()

        // Política de retry: 3 tentativas, backoff exponencial (100ms até 2s)
        private val retryPolicy = RetryPolicy(
            maxRetries = 3,
            initialDelayMs = 100L,
            maxDelayMs = 2000L,
            backoffMultiplier = 2f
        )
    }

    override suspend fun getAllCategory(): DefaultResult<List<CategoryModel>> {
        return try {
            // Tenta obter do cache primeiro
            val cached = categoryCache.get("all_categories")
            if (cached != null) {
                return DefaultResult.Success(data = cached)
            }

            // Se não estiver em cache, faz requisição com retry automático
            val response = retryWithExponentialBackoff(
                policy = retryPolicy,
                shouldRetry = { exception ->
                    // Faz retry apenas em erros de conexão/timeout
                    // Não faz retry em erros HTTP (4xx, 5xx)
                    exception is IOException || exception is TimeoutException
                },
                operation = {
                    categoryApiService.getAllCategory()
                }
            )

            // Se sucesso: mapeia, armazena em cache e retorna
            if (response.isSuccessful && response.data != null) {
                val models = response.data!!.map { it.toCategoryModel() }
                categoryCache.put("all_categories", models, ttlMs = 10 * 60 * 1000)
                DefaultResult.Success(data = models)
            } else {
                DefaultResult.Error(message = response.message)
            }

        } catch (e: ErrorResponseException) {
            // Erro HTTP (4xx, 5xx) da API
            DefaultResult.Error(code = e.error.httpCode.toString(), message = e.error.message)
        } catch (e: Exception) {
            // Erro genérico (conexão, timeout, parsing JSON, etc)
            val networkError = ErrorMapper.mapThrowableToNetworkError(e)
            DefaultResult.Error(message = networkError.message)
        }
    }
}