package com.br.xbizitwork.data.remote.profile.datasource

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.profile.UpdateProfileRequestModel

/**
 * Interface que define o contrato para acesso remoto aos dados de perfil
 * Camada de abstração entre Repository e API
 */
interface ProfileRemoteDataSource {
    /**
     * Atualiza o perfil do usuário no servidor remoto
     *
     * @param model Modelo de domínio com os dados do perfil
     * @return DefaultResult com sucesso ou erro
     */
    suspend fun updateProfile(model: UpdateProfileRequestModel): DefaultResult<ApiResultModel>
}