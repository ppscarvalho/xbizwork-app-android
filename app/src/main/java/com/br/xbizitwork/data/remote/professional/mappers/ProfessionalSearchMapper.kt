package com.br.xbizitwork.data.remote.professional.mappers

import com.br.xbizitwork.data.remote.professional.dtos.responses.ProfessionalSearchBySkillDto
import com.br.xbizitwork.data.remote.professional.dtos.responses.SkillDto
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.model.professional.SkillInfo

/**
 * Extension to convert ProfessionalSearchBySkillDto to domain model
 */
fun ProfessionalSearchBySkillDto.toDomain(): ProfessionalSearchBySkill {
    return ProfessionalSearchBySkill(
        id = this.id,
        name = this.name,
        mobilePhone = this.mobilePhone,
        city = this.city,
        state = this.state,
        skill = this.skill.toDomain()
    )
}

/**
 * Extension to convert SkillDto to domain model
 */
fun SkillDto.toDomain(): SkillInfo {
    return SkillInfo(
        id = this.id,
        description = this.description
    )
}
