package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.result.specialty.SpecialtyResult

/**
 * Repository para gerenciamento de especialidades
 */
interface SpecialtyRepository {
    /**
     * Obtém todas as especialidades de uma categoria específica
     */
    suspend fun getSpecialtiesByCategory(categoryId: Int): DefaultResult<List<SpecialtyResult>>
}
