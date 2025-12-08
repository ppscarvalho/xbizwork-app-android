
package com.br.xbizitwork.data.di

import com.example.xbizitwork.BuildConfig
import com.br.xbizitwork.core.network.AuthTokenInterceptor
import com.br.xbizitwork.data.local.auth.datastore.AuthSessionLocalDataSource
import com.google.gson.Gson
import com.google.gson.Strictness
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.gson.gson
import okhttp3.OkHttpClient
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Módulo responsável por prover as dependências de rede da aplicação.
 *
 * Este módulo é instalado no escopo de [SingletonComponent], garantindo que
 * todas as instâncias fornecidas aqui tenham ciclo de vida de Singleton.
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Fornece uma instância singleton configurada de [OkHttpClient].
     *
     * Este cliente HTTP é utilizado como base para todas as comunicações de rede
     * da aplicação, sendo responsável por executar requisições REST, chamadas a APIs
     * externas, uploads e downloads.
     *
     * ## Configurações aplicadas:
     * - Tempo máximo de leitura: 1 minuto
     * - Tempo máximo de escrita: 1 minuto
     *
     * Esses valores foram definidos para oferecer maior tolerância a:
     * - Conexões móveis instáveis
     * - APIs com maior latência de resposta
     * - Uploads de arquivos maiores
     *
     * Como está anotado com [Singleton], a mesma instância será compartilhada
     * por toda a aplicação.
     *
     * @return Instância única e configurada de [OkHttpClient].
     */
    @Provides
    @Singleton
    fun provideOKHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    /**
     * Fornece uma instância singleton do [Gson] para serialização e desserialização de JSON.
     *
     * Esta instância é utilizada em toda a aplicação para:
     * - Converter objetos Kotlin em JSON (serialização)
     * - Converter JSON em objetos Kotlin (desserialização)
     *
     * O uso como [Singleton] garante:
     * - Melhor desempenho
     * - Menor consumo de memória
     * - Padronização na conversão de dados em toda a aplicação
     *
     * Esta instância pode ser futuramente customizada com:
     * - Adapters personalizados
     * - Serialização de datas
     * - Estratégias de nomes de campos
     *
     * @return Instância única de [Gson].
     */
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    /**
     * Fornece uma instância singleton configurada do [HttpClient] do Ktor,
     * utilizando o engine [OkHttp] como base de transporte.
     *
     * Este cliente é responsável por toda a comunicação HTTP da aplicação,
     * incluindo requisições REST e comunicação via WebSocket.
     *
     * ## Configurações aplicadas:
     *
     * ### 1. URL base padrão
     * Define automaticamente a [BuildConfig.BASE_URL] como base para todas as
     * requisições feitas através do client.
     *
     * ### 2. Engine OkHttp
     * Utiliza um [OkHttpClient] previamente configurado via injeção de dependência,
     * garantindo reutilização de timeouts, interceptors e demais configurações.
     *
     * ### 3. Logging
     * Ativa o plugin [Logging] para registrar as requisições e respostas HTTP.
     * Os logs são direcionados ao [Timber], facilitando a depuração em ambiente
     * de desenvolvimento.
     *
     * - Nível de log: [LogLevel.INFO]
     *
     * ### 4. Content Negotiation
     * Ativa o plugin [ContentNegotiation] com suporte a JSON via [Gson], permitindo:
     * - Leitura flexível de JSON (setLenient)
     * - Formatação legível (setPrettyPrinting)
     * - Serialização de campos nulos (serializeNulls)
     *
     * ### 5. WebSockets
     * Habilita suporte a comunicação em tempo real via WebSocket.
     *
     * Como este provider está anotado com [Singleton], a mesma instância do
     * [HttpClient] será compartilhada por toda a aplicação.
     *
     * @param okHttpClient Instância previamente configurada de [OkHttpClient].
     * @return Instância única e totalmente configurada de [HttpClient].
     */
    @Provides
    @Singleton
    fun provideHttpClient(
        okHttpClient: OkHttpClient,
        authSessionLocalDataSource: AuthSessionLocalDataSource
    ): HttpClient {
        return HttpClient(OkHttp) {
            /**
             * Define a URL base padrão para todas as requisições HTTP.
             */
            defaultRequest {
                url(BuildConfig.BASE_URL)
            }

            /**
             * Configuração do engine OkHttp utilizando uma instância previamente
             * configurada via injeção de dependência.
             */
            engine {
                preconfigured = okHttpClient
                config {
                }
            }

            /**
             * Plugin responsável pelo log de todas as requisições e respostas HTTP.
             */
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.tag("Logger Ktor =>").v(message)
                    }
                }
                level = LogLevel.INFO
            }
            /**
             * Plugin responsável pela conversão automática de JSON para objetos
             * Kotlin e vice-versa, utilizando Gson.
             */
            install(ContentNegotiation) {
                gson {
                    setStrictness(Strictness.LENIENT)        // Permite JSON com pequenas inconsistências
                    setPrettyPrinting() // Formata JSON para melhor leitura em logs
                    serializeNulls()    // Inclui campos nulos na serialização
                }
            }
            /**
             * Habilita suporte a comunicação via WebSocket.
             */
            install(WebSockets) { }
            
            /**
             * ✨ NOVO: Interceptor de autenticação
             * Adiciona automaticamente o token JWT ao header Authorization de todas
             * as requisições autenticadas. O token é buscado do DataStore encriptado.
             */
            install(AuthTokenInterceptor.create(authSessionLocalDataSource))
        }
    }
}