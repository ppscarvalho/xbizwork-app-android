package com.br.xbizitwork.data.remote.professional.api

import com.br.xbizitwork.core.model.api.ApiResponse
import com.br.xbizitwork.data.remote.professional.dtos.responses.ProfessionalSearchBySkillDto

/**
 * Interface defining API operations for professional search
 * Following the same pattern as SkillsApiService
 */
interface ProfessionalSearchApiService {
    /**
     * Search professionals by skill name
     *
     * @param skill Skill name (full or partial match)
     * @return API response with list of professionals
     */
    suspend fun searchProfessionalsBySkill(skill: String): ApiResponse<List<ProfessionalSearchBySkillDto>>
}
