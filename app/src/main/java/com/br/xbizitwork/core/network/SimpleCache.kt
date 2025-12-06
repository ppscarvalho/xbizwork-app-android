package com.br.xbizitwork.core.network

import java.util.concurrent.ConcurrentHashMap
import kotlin.system.measureTimeMillis

/**
 * Representa um item em cache com sua data de expiração.
 */
private data class CacheEntry<T>(
    val data: T,
    val expiresAtMs: Long
) {
    fun isExpired(): Boolean = System.currentTimeMillis() > expiresAtMs
}

/**
 * Gerenciador de cache genérico com expiração automática.
 *
 * Características:
 * - Cache thread-safe com ConcurrentHashMap
 * - Expiração automática por TTL
 * - Limpeza lazy (remove item ao acessar se expirado)
 * - Sem overhead de background tasks
 *
 * Exemplo:
 * ```
 * val cache = SimpleCache<String, List<User>>()
 *
 * // Armazenar com TTL de 5 minutos
 * cache.put("users", listUsers(), ttlMs = 5 * 60 * 1000)
 *
 * // Recuperar
 * val users = cache.get("users") // null se expirado
 * ```
 */
class SimpleCache<K, V> {
    private val cacheMap = ConcurrentHashMap<K, CacheEntry<V>>()

    /**
     * Armazena um valor em cache com expiração.
     *
     * @param key Chave do cache
     * @param value Valor a armazenar
     * @param ttlMs Time-to-live em milissegundos
     */
    fun put(key: K, value: V, ttlMs: Long = 5 * 60 * 1000) {
        val expiresAtMs = System.currentTimeMillis() + ttlMs
        cacheMap[key] = CacheEntry(value, expiresAtMs)
    }

    /**
     * Recupera um valor do cache.
     *
     * Se o item tiver expirado, é removido e retorna null.
     *
     * @param key Chave do cache
     * @return Valor em cache ou null se não encontrado/expirado
     */
    fun get(key: K): V? {
        val entry = cacheMap[key] ?: return null

        return if (entry.isExpired()) {
            cacheMap.remove(key)
            null
        } else {
            entry.data
        }
    }

    /**
     * Remove um item do cache.
     */
    fun remove(key: K) {
        cacheMap.remove(key)
    }

    /**
     * Limpa todo o cache.
     */
    fun clear() {
        cacheMap.clear()
    }

    /**
     * Retorna o tamanho atual do cache.
     */
    fun size(): Int = cacheMap.size

    /**
     * Verifica se uma chave existe e não está expirada.
     */
    fun contains(key: K): Boolean = get(key) != null
}

/**
 * Extensão para usar cache com operações suspensas.
 *
 * Se o valor estiver em cache e não expirado, retorna do cache.
 * Caso contrário, executa a operação e armazena no cache.
 *
 * Exemplo:
 * ```
 * val cache = SimpleCache<String, List<User>>()
 *
 * val users = cache.getOrPut("users") {
 *     apiService.fetchUsers()  // Executado apenas se não estiver em cache
 * }
 * ```
 */
suspend inline fun <K, V> SimpleCache<K, V>.getOrPut(
    key: K,
    ttlMs: Long = 5 * 60 * 1000,
    crossinline operation: suspend () -> V
): V {
    get(key)?.let { return it }

    val value = operation()
    put(key, value, ttlMs)
    return value
}

/**
 * Extensão para medir tempo de execução e armazenar no cache.
 */
suspend inline fun <K, V> SimpleCache<K, V>.getOrPutMeasured(
    key: K,
    ttlMs: Long = 5 * 60 * 1000,
    crossinline operation: suspend () -> V
): Pair<V, Long> {
    get(key)?.let { return it to 0L }

    var value: V
    val timeMs = measureTimeMillis {
        value = operation()
    }
    put(key, value, ttlMs)
    return value to timeMs
}
