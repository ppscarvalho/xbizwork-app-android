package com.br.xbizitwork.data.repository.schedule

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.mappers.toSaveScheduleRequest
import com.br.xbizitwork.data.remote.schedule.datasource.ScheduleRemoteDataSource
import com.br.xbizitwork.domain.common.DomainDefaultResult
import com.br.xbizitwork.domain.repository.ScheduleRepository
import com.br.xbizitwork.domain.result.schedule.ScheduleItemResult
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementação de ScheduleRepository
 */
class ScheduleRepositoryImpl @Inject constructor(
    private val remoteDataSource: ScheduleRemoteDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ScheduleRepository {

    override suspend fun saveSchedule(scheduleItems: List<ScheduleItemResult>): DomainDefaultResult<Unit> =
        withContext(coroutineDispatcherProvider.io()) {
            val request = scheduleItems.toSaveScheduleRequest()
            val result = remoteDataSource.saveSchedule(request)

            when (result) {
                is DefaultResult.Success -> {
                    DomainDefaultResult.Success(data = Unit)
                }
                is DefaultResult.Error -> {
                    DomainDefaultResult.Error(message = result.message)
                }
            }
        }
}
