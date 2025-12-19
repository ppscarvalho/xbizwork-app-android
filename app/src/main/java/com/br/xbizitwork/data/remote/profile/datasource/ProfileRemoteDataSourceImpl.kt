package com.br.xbizitwork.data.remote.profile.datasource

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.profile.api.ProfileApiService
import com.br.xbizitwork.data.remote.profile.mappers.toRequest
import com.br.xbizitwork.domain.model.profile.UpdateProfileRequestModel
import com.br.xbizitwork.domain.source.profile.ProfileRemoteDataSource
import javax.inject.Inject

/**
 * Implementação do ProfileRemoteDataSource
 * Responsável por chamar a API e converter exceptions em DefaultResult
 */
class ProfileRemoteDataSourceImpl @Inject constructor(
    private val profileApiService: ProfileApiService
) : ProfileRemoteDataSource {

    override suspend fun updateProfile(model: UpdateProfileRequestModel): DefaultResult<ApiResultModel> {
        return try {
            // Converte domain model para DTO request
            val request = model.toRequest()

            // Chama a API
            val response = profileApiService.updateProfile(request)

            // Retorna sucesso (response já é ApiResultResponse, que tem toModel)
            DefaultResult.Success(
                ApiResultModel(
                    isSuccessful = response.isSuccessful,
                    message = response.message
                )
            )

        } catch (e: Exception) {
            // Erro técnico (rede, timeout, etc)
            DefaultResult.Error(message = e.message ?: "Erro desconhecido")
        }
    }
}

