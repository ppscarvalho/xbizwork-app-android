package com.br.xbizitwork.domain.usecase.specialty

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.repository.SpecialtyRepository
import com.br.xbizitwork.domain.result.specialty.SpecialtyResult
import javax.inject.Inject

/**
 * Use Case para obter especialidades por categoria
 */
class GetSpecialtiesByCategoryUseCase @Inject constructor(
    private val repository: SpecialtyRepository
) {
    suspend operator fun invoke(categoryId: Int): DefaultResult<List<SpecialtyResult>> {
        return repository.getSpecialtiesByCategory(categoryId)
    }
}
