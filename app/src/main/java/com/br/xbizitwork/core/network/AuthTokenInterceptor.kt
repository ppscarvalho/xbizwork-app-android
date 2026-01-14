package com.br.xbizitwork.core.network

import com.br.xbizitwork.core.util.logging.logError
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.data.local.auth.datastore.AuthSessionLocalDataSource
import io.ktor.client.plugins.api.ClientPlugin
import io.ktor.client.plugins.api.createClientPlugin
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.first

/**
 * Plugin Ktor que adiciona automaticamente o token JWT ao header Authorization
 * e detecta quando o token expira (401 Unauthorized).
 *
 * ## Responsabilidades:
 * - Intercepta todas as requisições HTTP saídas
 * - Busca o token JWT armazenado localmente (DataStore criptografado)
 * - Adiciona o token ao header Authorization (formato: "Bearer {token}")
 * - Intercepta respostas 401 e limpa sessão automaticamente
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
 * 6. Resposta volta → Plugin verifica status
 * 7. Se 401: limpa sessão e força reautenticação
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

            // ✅ INTERCEPTOR DE REQUISIÇÃO (adiciona token)
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

            // ✅ INTERCEPTOR DE RESPOSTA (detecta 401 e limpa sessão)
            onResponse { response ->
                try {
                    if (response.status == HttpStatusCode.Unauthorized) {
                        logInfo("AUTH_INTERCEPTOR", "⚠️ Token inválido/expirado (401) - Limpando sessão...")

                        // Limpa a sessão automaticamente
                        authSessionLocalDataSource.clearSession()

                        logInfo("AUTH_INTERCEPTOR", "✅ Sessão limpa! Usuário precisa fazer login novamente.")
                    }
                } catch (e: Exception) {
                    logError("AUTH_INTERCEPTOR", "Erro ao processar resposta 401: ${e.message}")
                }
            }
        }
}