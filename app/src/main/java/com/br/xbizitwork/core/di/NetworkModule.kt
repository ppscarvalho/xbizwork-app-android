package com.br.xbizitwork.core.di

import com.example.xbizitwork.BuildConfig
import com.google.gson.Gson
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

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOKHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        okHttpClient: OkHttpClient,
    ): HttpClient {
        return HttpClient(OkHttp) {
            defaultRequest {
                url(BuildConfig.BASE_URL)
            }
            engine {
                preconfigured = okHttpClient
                config { }
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Timber.tag("Logger Ktor =>").v(message)
                    }
                }
                level = LogLevel.INFO
            }

            install(ContentNegotiation) {
                gson {
                    setLenient()        // Permite JSON com pequenas inconsistências
                    setPrettyPrinting() // Formata JSON para melhor leitura em logs
                    serializeNulls()    // Inclui campos nulos na serialização
                }
            }
            install(WebSockets) { }
        }
    }

}