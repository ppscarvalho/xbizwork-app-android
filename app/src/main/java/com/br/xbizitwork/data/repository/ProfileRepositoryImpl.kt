package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.profile.UpdateProfileRequestModel
import com.br.xbizitwork.domain.repository.ProfileRepository
import com.br.xbizitwork.data.remote.profile.datasource.ProfileRemoteDataSource
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementação do ProfileRepository
 * Delega as operações para o RemoteDataSource
 */
class ProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ProfileRepository {

    override suspend fun updateProfile(model: UpdateProfileRequestModel): DefaultResult<ApiResultModel> =
        withContext(coroutineDispatcherProvider.io()) {
            remoteDataSource.updateProfile(model)
        }
}
