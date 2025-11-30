package com.br.xbizitwork.core.di

import com.br.xbizitwork.core.util.common.CoroutineDispatcherProvider
import com.br.xbizitwork.core.util.common.CoroutineDispatcherProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
/**
 * Módulo de Injeção de Dependência responsável por registrar
 * as implementações relacionadas aos Dispatchers de Coroutines.
 *
 * Este módulo é instalado no [SingletonComponent], garantindo que
 * as dependências fornecidas tenham ciclo de vida de singleton
 * em toda a aplicação.
 *
 * Sua principal responsabilidade é associar a implementação concreta
 * [CoroutineDispatcherProviderImpl] ao contrato abstraído que será
 * utilizado no restante do app.
 */
@Module
@InstallIn(SingletonComponent::class)
interface CoroutineDispatcherModule {

    /**
     * Faz o bind da implementação [CoroutineDispatcherProviderImpl]
     * para a abstração utilizada pela aplicação.
     *
     * Isso permite:
     * - Desacoplamento entre interface e implementação
     * - Facilidade para testes (mock/fake)
     * - Centralização da configuração dos Dispatchers
     *
     * @param coroutinesDispatcherProviderImpl Implementação concreta do provider de Dispatchers.
     * @return A abstração utilizada na aplicação para acesso aos Dispatchers.
     */
    @Binds
    fun bindCoroutineDispatcherModule(
        coroutinesDispatcherProviderImpl: CoroutineDispatcherProviderImpl
    ): CoroutineDispatcherProvider
}