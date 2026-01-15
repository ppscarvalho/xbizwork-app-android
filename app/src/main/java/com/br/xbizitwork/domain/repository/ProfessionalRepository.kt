package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.professional.ProfessionalProfile
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.model.service.SearchFilters
import com.br.xbizitwork.domain.model.service.SearchResult

/**
 * Contrato de repositório para operações de Profissionais
 *
 * Responsabilidades:
 * - Buscar profissionais com filtros
 * - Obter detalhes de um profissional
 * - Atualizar perfil do profissional
 * - Cache de profissionais frequentemente acessados
 */
interface ProfessionalRepository {
    
    /**
     * Busca profissionais por habilidade (skill)
     *
     * @param skill Nome completo ou parcial da habilidade
     * @return Lista de profissionais encontrados
     */
    suspend fun searchProfessionalsBySkill(skill: String): DefaultResult<List<ProfessionalSearchBySkill>>
    
    /**
     * Busca profissionais com base em filtros
     *
     * @param filters Critérios de busca (localização, categoria, preço, etc)
     * @return SearchResult paginado com profissionais encontrados
     */
    suspend fun searchProfessionals(filters: SearchFilters): DefaultResult<SearchResult>
    
    /**
     * Obtém detalhes completos de um profissional
     *
     * @param professionalId ID do profissional
     * @return Perfil completo do profissional
     */
    suspend fun getProfessionalDetails(professionalId: String): DefaultResult<ProfessionalProfile>
    
    /**
     * Atualiza o perfil do profissional autenticado
     *
     * @param profile Dados atualizados do perfil
     * @return Perfil atualizado
     */
    suspend fun updateProfile(profile: ProfessionalProfile): DefaultResult<ProfessionalProfile>
    
    /**
     * Obtém profissionais recomendados (trending/top-rated)
     *
     * @param count Número de profissionais a retornar
     * @return Lista de profissionais em destaque
     */
    suspend fun getTrendingProfessionals(count: Int = 10): DefaultResult<List<ProfessionalProfile>>
    
    /**
     * Obtém profissionais favoritos do usuário
     *
     * @return Lista de profissionais adicionados aos favoritos
     */
    suspend fun getFavoriteProfessionals(): DefaultResult<List<ProfessionalProfile>>
    
    /**
     * Adiciona um profissional aos favoritos
     */
    suspend fun addToFavorites(professionalId: String): DefaultResult<Unit>
    
    /**
     * Remove um profissional dos favoritos
     */
    suspend fun removeFromFavorites(professionalId: String): DefaultResult<Unit>
    
    /**
     * Obtém histórico de profissionais visitados
     *
     * @return Lista de profissionais recentemente visualizados
     */
    suspend fun getViewedProfessionals(): DefaultResult<List<ProfessionalProfile>>
}
