package com.br.xbizitwork.data.model.specialty

/**
 * Model da camada de Data para Specialty
 * Sem anotações GSON - não deserializa JSON diretamente
 * Recebe dados convertidos de SpecialtyResponse (API/DTO)
 * Usado entre DataSource e Repository
 */
data class SpecialtyModel(
    val id: Int,
    val description: String
)

