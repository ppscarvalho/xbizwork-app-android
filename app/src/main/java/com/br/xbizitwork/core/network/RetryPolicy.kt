package com.br.xbizitwork.core.network

import kotlinx.coroutines.delay

/**
 * Configuração de política de retry para requisições de rede.
 *
 * @param maxRetries Número máximo de tentativas
 * @param initialDelayMs Delay inicial em milissegundos
 * @param maxDelayMs Delay máximo em milissegundos
 * @param backoffMultiplier Multiplicador para backoff exponencial
 */
data class RetryPolicy(
    val maxRetries: Int = 3,
    val initialDelayMs: Long = 100L,
    val maxDelayMs: Long = 5000L,
    val backoffMultiplier: Float = 2f
) {
    companion object {
        val DEFAULT = RetryPolicy()
    }
}

/**
 * Executa uma operação suspensa com retry automático.
 *
 * Usa exponential backoff para calcular delay entre tentativas.
 *
 * Exemplo:
 * ```
 * val result = retryWithExponentialBackoff<String>(
 *     policy = RetryPolicy(maxRetries = 3),
 *     shouldRetry = { exception ->
 *         exception is IOException || exception is TimeoutException
 *     },
 *     operation = {
 *         apiService.fetchData()
 *     }
 * )
 * ```
 *
 * @param policy Configuração de retry
 * @param shouldRetry Função que determina se deve fazer retry baseado na exceção
 * @param operation Operação suspensa a executar
 * @return Resultado da operação ou exceção após esgotar tentativas
 */
suspend inline fun <T> retryWithExponentialBackoff(
    policy: RetryPolicy = RetryPolicy.DEFAULT,
    crossinline shouldRetry: (Exception) -> Boolean = { true },
    crossinline operation: suspend () -> T
): T {
    var currentDelayMs = policy.initialDelayMs
    var lastException: Exception? = null

    repeat(policy.maxRetries) { attempt ->
        try {
            return operation()
        } catch (exception: Exception) {
            lastException = exception

            // Se for a última tentativa ou não deve fazer retry, lança a exceção
            if (attempt == policy.maxRetries - 1 || !shouldRetry(exception)) {
                throw exception
            }

            // Aguarda o delay antes de tentar novamente
            delay(currentDelayMs)

            // Calcula próximo delay com backoff exponencial
            currentDelayMs = (currentDelayMs * policy.backoffMultiplier)
                .toLong()
                .coerceAtMost(policy.maxDelayMs)
        }
    }

    // Nunca deve alcançar aqui, mas por segurança
    throw lastException ?: Exception("Retry failed: Unknown error")
}
