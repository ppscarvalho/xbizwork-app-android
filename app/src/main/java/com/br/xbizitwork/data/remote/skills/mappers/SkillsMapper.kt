package com.br.xbizitwork.data.remote.skills.mappers

import com.br.xbizitwork.data.remote.skills.dtos.requests.SaveUserSkillsRequest
import com.br.xbizitwork.data.remote.skills.dtos.responses.ProfessionalSkillItemResponse
import com.br.xbizitwork.data.remote.skills.dtos.responses.SkillInfoResponse
import com.br.xbizitwork.domain.model.skills.ProfessionalSearchResult
import com.br.xbizitwork.domain.model.skills.SaveUserSkillsRequestModel
import com.br.xbizitwork.domain.model.skills.SkillInfo

/**
 * Extensão para converter domain model para DTO de request
 */
fun SaveUserSkillsRequestModel.toRequest(): SaveUserSkillsRequest {
    return SaveUserSkillsRequest(
        categoryIds = this.categoryIds
    )
}

/**
 * Extensão para converter DTO de resposta para domain model
 */
fun ProfessionalSkillItemResponse.toDomain(): ProfessionalSearchResult {
    return ProfessionalSearchResult(
        id = this.id,
        name = this.name,
        mobilePhone = this.mobilePhone,
        city = this.city,
        state = this.state,
        skill = this.skill.toDomain()
    )
}

/**
 * Extensão para converter SkillInfoResponse para domain model
 */
fun SkillInfoResponse.toDomain(): SkillInfo {
    return SkillInfo(
        id = this.id,
        description = this.description
    )
}

