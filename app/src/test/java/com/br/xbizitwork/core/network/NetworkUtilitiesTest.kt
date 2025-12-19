package com.br.xbizitwork.core.network

import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import java.io.IOException

/**
 * Testes unitários para RetryPolicy e retryWithExponentialBackoff.
 *
 * Testa:
 * - Sucesso na primeira tentativa
 * - Retry com backoff exponencial
 * - Falha após esgotar tentativas
 * - Condição shouldRetry
 */
class RetryPolicyTest {

    @Test
    fun retryWithExponentialBackoff_succeedsOnFirstAttempt() = runTest {
        // Arrange
        var callCount = 0
        val operation: suspend () -> String = {
            callCount++
            "Success"
        }

        // Act
        val result = retryWithExponentialBackoff(
            policy = RetryPolicy.DEFAULT,
            operation = operation
        )

        // Assert
        assertThat(result).isEqualTo("Success")
        assertThat(callCount).isEqualTo(1)
    }

    @Test
    fun retryWithExponentialBackoff_retriesAndEventuallySucceeds() = runTest {
        // Arrange
        var callCount = 0
        val operation: suspend () -> String = {
            callCount++
            if (callCount < 3) {
                throw Exception("Temporary failure")
            }
            "Success on third attempt"
        }

        // Act
        val result = retryWithExponentialBackoff(
            policy = RetryPolicy(maxRetries = 3),
            operation = operation
        )

        // Assert
        assertThat(result).isEqualTo("Success on third attempt")
        assertThat(callCount).isEqualTo(3)
    }

    @Test
    fun retryWithExponentialBackoff_failsAfterMaxRetries() = runTest {
        // Arrange
        var callCount = 0
        val operation: suspend () -> String = {
            callCount++
            throw Exception("Always fails")
        }

        // Act & Assert
        val exception = try {
            retryWithExponentialBackoff(
                policy = RetryPolicy(maxRetries = 3),
                operation = operation
            )
            null
        } catch (e: Exception) {
            e
        }

        assertThat(exception).isNotNull()
        assertThat(callCount).isEqualTo(3)
    }

    @Test
    fun retryWithExponentialBackoff_respectsShouldRetryCondition() = runTest {
        // Arrange
        var callCount = 0
        val operation: suspend () -> String = {
            callCount++
            throw IllegalArgumentException("Should not retry this")
        }

        // Act & Assert
        val exception = try {
            retryWithExponentialBackoff(
                policy = RetryPolicy(maxRetries = 3),
                shouldRetry = { exception ->
                    // Só faz retry em IOException
                    exception is IOException
                },
                operation = operation
            )
            null
        } catch (e: Exception) {
            e
        }

        assertThat(exception).isNotNull()
        // Deve falhar na primeira tentativa sem fazer retry
        assertThat(callCount).isEqualTo(1)
    }
}

/**
 * Testes unitários para SimpleCache.
 *
 * Testa:
 * - Armazenamento e recuperação de valores
 * - Expiração automática
 * - Remoção de itens
 * - Operação getOrPut
 */
class SimpleCacheTest {

    @Test
    fun cache_storesAndRetrievesValue() {
        // Arrange
        val cache = SimpleCache<String, String>()
        val key = "test_key"
        val value = "test_value"

        // Act
        cache.put(key, value)
        val retrieved = cache.get(key)

        // Assert
        assertThat(retrieved).isEqualTo(value)
    }

    @Test
    fun cache_returnsNullForExpiredValue() {
        // Arrange
        val cache = SimpleCache<String, String>()
        val key = "test_key"
        val value = "test_value"

        // Act - Armazena com TTL de 1ms
        cache.put(key, value, ttlMs = 1L)
        
        // Aguarda um pouco para garantir que expire
        Thread.sleep(10)
        
        val retrieved = cache.get(key)

        // Assert
        assertThat(retrieved).isNull()
    }

    @Test
    fun cache_removeItem() {
        // Arrange
        val cache = SimpleCache<String, String>()
        val key = "test_key"
        val value = "test_value"

        // Act
        cache.put(key, value)
        cache.remove(key)
        val retrieved = cache.get(key)

        // Assert
        assertThat(retrieved).isNull()
    }

    @Test
    fun cache_clear() {
        // Arrange
        val cache = SimpleCache<String, String>()
        cache.put("key1", "value1")
        cache.put("key2", "value2")
        cache.put("key3", "value3")

        // Act
        cache.clear()

        // Assert
        assertThat(cache.size()).isEqualTo(0)
    }

    @Test
    fun cache_containsReturnsTrueForValidItem() {
        // Arrange
        val cache = SimpleCache<String, String>()
        val key = "test_key"

        // Act
        cache.put(key, "value")

        // Assert
        assertThat(cache.contains(key)).isTrue()
    }

    @Test
    fun cache_containsReturnsFalseForExpiredItem() {
        // Arrange
        val cache = SimpleCache<String, String>()
        val key = "test_key"

        // Act
        cache.put(key, "value", ttlMs = 1L)
        Thread.sleep(10)

        // Assert
        assertThat(cache.contains(key)).isFalse()
    }

    @Test
    fun cache_getOrPut_usesCache() = runTest {
        // Arrange
        val cache = SimpleCache<String, String>()
        var callCount = 0

        // Act
        val result1 = cache.getOrPut("key") {
            callCount++
            "value"
        }

        val result2 = cache.getOrPut("key") {
            callCount++
            "different_value"
        }

        // Assert
        assertThat(result1).isEqualTo("value")
        assertThat(result2).isEqualTo("value") // Vem do cache
        assertThat(callCount).isEqualTo(1) // Operação chamada apenas uma vez
    }

    @Test
    fun cache_getOrPut_executeOperationIfNotCached() = runTest {
        // Arrange
        val cache = SimpleCache<String, String>()
        var callCount = 0

        // Act
        val result = cache.getOrPut("key") {
            callCount++
            "computed_value"
        }

        // Assert
        assertThat(result).isEqualTo("computed_value")
        assertThat(callCount).isEqualTo(1)
    }
}
