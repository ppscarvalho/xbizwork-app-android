package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.faq.datasource.FaqRemoteDataSource
import com.br.xbizitwork.domain.model.faq.FaqSection
import com.br.xbizitwork.domain.repository.FaqRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementação do FaqRepository
 * Delega as operações para o RemoteDataSource
 * Seguindo o mesmo padrão do SkillsRepositoryImpl
 */
class FaqRepositoryImpl @Inject constructor(
    private val remoteDataSource: FaqRemoteDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : FaqRepository {

    override suspend fun getPublicFaqSections(): DefaultResult<List<FaqSection>> =
        withContext(coroutineDispatcherProvider.io()) {
            remoteDataSource.getPublicFaqSections()
        }
}
