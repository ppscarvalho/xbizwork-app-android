package com.br.xbizitwork.data.remote.schedule.datasource

import com.br.xbizitwork.core.exceptions.ErrorResponseException
import com.br.xbizitwork.core.network.ErrorMapper
import com.br.xbizitwork.core.network.RetryPolicy
import com.br.xbizitwork.core.network.retryWithExponentialBackoff
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.schedule.api.ScheduleApiService
import com.br.xbizitwork.data.remote.schedule.dtos.request.SaveScheduleRequest
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

/**
 * Implementação de ScheduleRemoteDataSource
 */
class ScheduleRemoteDataSourceImpl @Inject constructor(
    private val scheduleApiService: ScheduleApiService
) : ScheduleRemoteDataSource {

    companion object {
        // Política de retry
        private val retryPolicy = RetryPolicy(
            maxRetries = 3,
            initialDelayMs = 100L,
            maxDelayMs = 2000L,
            backoffMultiplier = 2f
        )
    }

    override suspend fun saveSchedule(request: SaveScheduleRequest): DefaultResult<Unit> {
        return try {
            val response = retryWithExponentialBackoff(
                policy = retryPolicy,
                shouldRetry = { exception ->
                    exception is IOException || exception is TimeoutException
                },
                operation = {
                    scheduleApiService.saveSchedule(request)
                }
            )

            if (response.isSuccessful) {
                DefaultResult.Success(data = Unit)
            } else {
                DefaultResult.Error(message = response.message)
            }

        } catch (e: ErrorResponseException) {
            DefaultResult.Error(code = e.error.httpCode.toString(), message = e.error.message)
        } catch (e: Exception) {
            val networkError = ErrorMapper.mapThrowableToNetworkError(e)
            DefaultResult.Error(message = networkError.message)
        }
    }
}
