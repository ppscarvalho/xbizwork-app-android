package com.br.xbizitwork.domain.model.skills

/**
 * Modelo de domínio para informações de habilidade
 */
data class SkillInfo(
    val id: Int,
    val description: String
)

/**
 * Modelo de domínio para um profissional encontrado na busca por habilidade
 */
data class ProfessionalSearchResult(
    val id: Int,
    val name: String,
    val mobilePhone: String,
    val city: String,
    val state: String,
    val skill: SkillInfo
)
