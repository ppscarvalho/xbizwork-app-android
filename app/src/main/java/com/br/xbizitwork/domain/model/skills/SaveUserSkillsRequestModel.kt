package com.br.xbizitwork.domain.model.skills

/**
 * Modelo de domínio para salvar habilidades do usuário
 * Representa os IDs das categorias que o usuário domina
 */
data class SaveUserSkillsRequestModel(
    val categoryIds: List<Int>
)

