package com.br.xbizitwork.domain.repository.profile

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.profile.UpdateProfileRequestModel

/**
 * Interface do repositório de perfil
 * Define o contrato para operações de perfil seguindo Clean Architecture
 */
interface ProfileRepository {
    /**
     * Atualiza o perfil do usuário
     *
     * @param model Dados do perfil a serem atualizados
     * @return DefaultResult com ApiResultModel contendo resultado da operação
     */
    suspend fun updateProfile(model: UpdateProfileRequestModel): DefaultResult<ApiResultModel>
}

