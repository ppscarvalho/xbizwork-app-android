package com.br.xbizitwork.data.remote.searchprofessional.dtos.responses

import com.google.gson.annotations.SerializedName

/**
 * DTO: Professional returned from skill search
 * Matches the API response structure from /api/v1/user-skills/search
 */
data class ProfessionalSearchBySkillDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("name")
    val name: String,
    
    @SerializedName("mobilePhone")
    val mobilePhone: String,
    
    @SerializedName("city")
    val city: String,
    
    @SerializedName("state")
    val state: String,

    @SerializedName("latitude")
    val latitude: Double? = null,

    @SerializedName("longitude")
    val longitude: Double? = null,

    @SerializedName("skill")
    val skill: SkillDto
)

/**
 * DTO: Skill information
 */
data class SkillDto(
    @SerializedName("id")
    val id: Int,
    
    @SerializedName("description")
    val description: String
)
