package com.br.xbizitwork.core.network

import com.br.xbizitwork.core.util.logging.logError
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.data.local.auth.datastore.interfaces.AuthSessionLocalDataSource
import io.ktor.client.plugins.api.ClientPlugin
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Plugin Ktor que adiciona automaticamente o token JWT ao header Authorization.
 *
 * ## Responsabilidades:
 * - Intercepta todas as requisições HTTP saídas
 * - Busca o token JWT armazenado localmente (DataStore criptografado)
 * - Adiciona o token ao header Authorization (formato: "Bearer {token}")
 * - Trata erros silenciosamente (requisições públicas não falham)
 *
 * ## Padrão Google:
 * - ✅ Usa Ktor Plugins (recomendado para Kotlin)
 * - ✅ Async nativo (sem runBlocking)
 * - ✅ Simples e focado em uma coisa
 * - ✅ Thread-safe via Coroutines
 *
 * ## Uso na configuração do HttpClient:
 * ```kotlin
 * val httpClient = HttpClient {
 *     install(AuthTokenInterceptor.create(authSessionLocalDataSource))
 * }
 * ```
 *
 * ## Fluxo:
 * 1. Requisição saindo → Plugin intercepta
 * 2. Tenta buscar token de observeSession().first()
 * 3. Se sucesso: adiciona "Authorization: Bearer {token}"
 * 4. Se falha: continua sem header (pode ser requisição pública)
 * 5. Requisição segue seu caminho
 */
object AuthTokenInterceptor {
    
    /**
     * Cria o plugin Ktor de autenticação.
     * 
     * @param authSessionLocalDataSource Fonte do token JWT armazenado
     * @return Plugin pronto para instalar no HttpClient
     */
    fun create(authSessionLocalDataSource: AuthSessionLocalDataSource): ClientPlugin<Unit> =
        createClientPlugin(name = "AuthTokenInterceptor") {
            onRequest { request, _ ->
                try {
                    // Busca o token do DataStore (assíncrono, sem bloqueio)
                    val session = authSessionLocalDataSource.observeSession().first()

                    // Se houver token válido, adiciona ao header
                    if (session.token.isNotEmpty()) {
                        request.header(
                            HttpHeaders.Authorization,
                            "Bearer ${session.token}"
                        )
                        logInfo("AUTH_INTERCEPTOR", "Token injetado na requisição")
                    } else {
                        logInfo("AUTH_INTERCEPTOR", "Token vazio, requisição pública")
                    }
                } catch (e: Exception) {
                    // Se houver erro, continua sem token
                    // Isso permite requisições públicas funcionarem mesmo sem token
                    logError("AUTH_INTERCEPTOR", "Erro ao buscar token: ${e.message}")
                }
            }
        }
}
