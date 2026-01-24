package com.br.xbizitwork.data.remote.searchprofessional.mappers

import com.br.xbizitwork.data.remote.searchprofessional.dtos.responses.ProfessionalSearchBySkillDto
import com.br.xbizitwork.data.remote.searchprofessional.dtos.responses.SkillDto
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.model.professional.SkillInfo
import kotlin.Double

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
        latitude =  this.latitude,
        longitude = this.longitude,
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
