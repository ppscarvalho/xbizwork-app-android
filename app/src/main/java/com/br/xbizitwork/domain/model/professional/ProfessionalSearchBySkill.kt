package com.br.xbizitwork.domain.model.professional

import com.google.gson.annotations.SerializedName

/**
 * Domain: Professional data returned from skill search
 * 
 * Represents a professional found by skill search
 */
data class ProfessionalSearchBySkill(
    val id: Int,
    val name: String,
    val mobilePhone: String,
    val city: String,
    val state: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val skill: SkillInfo
)

/**
 * Domain: Skill information for professional
 */
data class SkillInfo(
    val id: Int,
    val description: String
)
