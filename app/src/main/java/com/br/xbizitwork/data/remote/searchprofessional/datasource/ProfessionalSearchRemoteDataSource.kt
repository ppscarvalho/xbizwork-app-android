package com.br.xbizitwork.data.remote.searchprofessional.datasource

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill

/**
 * Interface defining the contract for remote access to professional search data
 * Abstraction layer between Repository and API
 */
interface ProfessionalSearchRemoteDataSource {
    /**
     * Search professionals by skill name
     *
     * @param skill Skill name (full or partial match)
     * @return DefaultResult with success or error
     */
    suspend fun searchProfessionalsBySkill(skill: String): DefaultResult<List<ProfessionalSearchBySkill>>
}
