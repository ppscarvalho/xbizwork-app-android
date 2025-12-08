package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.domain.common.DomainDefaultResult
import com.br.xbizitwork.domain.result.category.CategoryResult

/**
 * Interface de repositório para categorias
 * 
 * Define contrato para acesso aos dados de categorias
 * Retorna DomainDefaultResult com lista de CategoryResult
 */
interface CategoryRepository {
    suspend fun getAllCategory(parameters: Unit = Unit): DomainDefaultResult<List<CategoryResult>>
}