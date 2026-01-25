package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.faq.datasource.FaqRemoteDataSource
import com.br.xbizitwork.domain.model.faq.FaqSection
import com.br.xbizitwork.domain.repository.FaqRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of FaqRepository
 * Delegates operations to the RemoteDataSource
 * Following the same pattern as SkillsRepositoryImpl
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
