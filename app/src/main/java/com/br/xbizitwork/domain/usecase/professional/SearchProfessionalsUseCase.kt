package com.br.xbizitwork.domain.usecase.professional

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.service.SearchFilters
import com.br.xbizitwork.domain.model.service.SearchResult
import com.br.xbizitwork.domain.repository.ProfessionalRepository
import javax.inject.Inject

/**
 * Use Case: Buscar Profissionais
 *
 * Responsabilidade: Orquestrar a busca de profissionais com filtros
 *
 * Fluxo:
 * 1. Valida filtros de entrada
 * 2. Verifica cache local
 * 3. Se não houver cache, faz requisição à API
 * 4. Salva resultado em cache
 * 5. Retorna resultado
 *
 * Exemplo de uso:
 * ```kotlin
 * val filters = SearchFilters(
 *     latitude = -23.5505,
 *     longitude = -46.6333,
 *     categories = listOf("PLUMBING", "ELECTRICAL"),
 *     minRating = 4.5f,
 *     onlyVerified = true
 * )
 * val result = searchProfessionalsUseCase(filters)
 * ```
 */
class SearchProfessionalsUseCase @Inject constructor(
    private val professionalRepository: ProfessionalRepository,
) {
    suspend operator fun invoke(filters: SearchFilters): DefaultResult<SearchResult> {
        return try {
            // Validações básicas
            if (filters.radiusKm < 1 || filters.radiusKm > 100) {
                return DefaultResult.Error(
                    code = "INVALID_RADIUS",
                    message = "Raio deve estar entre 1 e 100 km"
                )
            }
            
            if (filters.minRating < 0f || filters.minRating > 5f) {
                return DefaultResult.Error(
                    code = "INVALID_RATING",
                    message = "Avaliação deve estar entre 0 e 5"
                )
            }
            
            // Chamada ao repository
            professionalRepository.searchProfessionals(filters)
        } catch (e: Exception) {
            DefaultResult.Error(
                code = "SEARCH_ERROR",
                message = "Erro ao buscar profissionais: ${e.message}"
            )
        }
    }
}
