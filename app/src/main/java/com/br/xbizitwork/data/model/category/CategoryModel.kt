package com.br.xbizitwork.data.model.category

/**
 * Model da camada de Data para Category
 * Sem anotações GSON - não deserializa JSON diretamente
 * Recebe dados convertidos de CategoryResponse (API/DTO)
 * Usado entre DataSource e Repository
 */
data class CategoryModel(
    val id: Int,
    val description: String
)

