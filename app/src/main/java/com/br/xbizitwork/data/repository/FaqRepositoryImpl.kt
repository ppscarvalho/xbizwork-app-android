package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.faq.datasource.FaqRemoteDataSource
import com.br.xbizitwork.domain.model.faq.FaqSectionModel
import com.br.xbizitwork.domain.repository.FaqRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementação do FaqRepository
 * Delega as operações para o RemoteDataSource
 * Segue o padrão do SkillsRepositoryImpl
 */
class FaqRepositoryImpl @Inject constructor(
    private val remoteDataSource: FaqRemoteDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : FaqRepository {

    override suspend fun getFaqSections(): DefaultResult<List<FaqSectionModel>> =
        withContext(coroutineDispatcherProvider.io()) {
            remoteDataSource.getFaqSections()
        }
}
