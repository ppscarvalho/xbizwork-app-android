package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.skills.ProfessionalSearchResult
import com.br.xbizitwork.domain.model.skills.SaveUserSkillsRequestModel

/**
 * Interface do repositório de habilidades
 * Define o contrato para operações de skills seguindo Clean Architecture
 */
interface SkillsRepository {
    /**
     * Salva as habilidades (categorias) que o usuário domina
     *
     * @param model IDs das categorias selecionadas
     * @return DefaultResult com ApiResultModel contendo resultado da operação
     */
    suspend fun saveUserSkills(model: SaveUserSkillsRequestModel): DefaultResult<ApiResultModel>

    /**
     * Obtém os IDs das categorias (habilidades) que o usuário possui salvas
     *
     * @param userId ID do usuário
     * @return DefaultResult com lista de IDs das categorias
     */
    suspend fun getUserSkills(userId: Int): DefaultResult<List<Int>>

    /**
     * Busca profissionais por habilidade (skill)
     *
     * @param skill Termo de busca da habilidade (completo ou parcial)
     * @return DefaultResult com lista de profissionais encontrados
     */
    suspend fun searchProfessionalsBySkill(skill: String): DefaultResult<List<ProfessionalSearchResult>>
}

