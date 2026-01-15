package com.br.xbizitwork.data.remote.skills.datasource

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.skills.ProfessionalSearchResult
import com.br.xbizitwork.domain.model.skills.SaveUserSkillsRequestModel

/**
 * Interface que define o contrato para acesso remoto aos dados de habilidades
 * Camada de abstração entre Repository e API
 */
interface SkillsRemoteDataSource {
    /**
     * Salva as habilidades do usuário no servidor remoto
     *
     * @param model Modelo de domínio com os IDs das categorias
     * @return DefaultResult com sucesso ou erro
     */
    suspend fun saveUserSkills(model: SaveUserSkillsRequestModel): DefaultResult<ApiResultModel>

    /**
     * Obtém as habilidades salvas do usuário
     *
     * @param userId ID do usuário
     * @return DefaultResult com lista de IDs das categorias
     */
    suspend fun getUserSkills(userId: Int): DefaultResult<List<Int>>

    /**
     * Busca profissionais por habilidade
     *
     * @param skill Termo de busca da habilidade
     * @return DefaultResult com lista de profissionais encontrados
     */
    suspend fun searchProfessionalsBySkill(skill: String): DefaultResult<List<ProfessionalSearchResult>>
}