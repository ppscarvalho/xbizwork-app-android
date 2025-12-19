package com.br.xbizitwork.data.repository.profile

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.profile.UpdateProfileRequestModel
import com.br.xbizitwork.domain.repository.profile.ProfileRepository
import com.br.xbizitwork.domain.source.profile.ProfileRemoteDataSource
import javax.inject.Inject

/**
 * Implementação do ProfileRepository
 * Delega as operações para o RemoteDataSource
 */
class ProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource
) : ProfileRepository {

    override suspend fun updateProfile(model: UpdateProfileRequestModel): DefaultResult<ApiResultModel> {
        return remoteDataSource.updateProfile(model)
    }
}

