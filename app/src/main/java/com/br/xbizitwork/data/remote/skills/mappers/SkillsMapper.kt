package com.br.xbizitwork.data.remote.skills.mappers

import com.br.xbizitwork.data.remote.skills.dtos.requests.SaveUserSkillsRequest
import com.br.xbizitwork.domain.model.skills.SaveUserSkillsRequestModel

/**
 * Extensão para converter domain model para DTO de request
 */
fun SaveUserSkillsRequestModel.toRequest(): SaveUserSkillsRequest {
    return SaveUserSkillsRequest(
        categoryIds = this.categoryIds
    )
}

