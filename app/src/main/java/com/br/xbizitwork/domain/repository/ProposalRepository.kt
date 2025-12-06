package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.service.ProposalResponse
import com.br.xbizitwork.domain.model.service.ServiceProposal
import com.br.xbizitwork.domain.model.service.ServiceReview

/**
 * Contrato de repositório para operações de Propostas de Serviço
 *
 * Responsabilidades:
 * - Criar novas propostas
 * - Listar propostas (cliente/profissional)
 * - Aceitar/Recusar propostas
 * - Atualizar status (em andamento, concluído)
 * - Gerenciar avaliações
 */
interface ProposalRepository {
    
    // ========== CLIENTE ==========
    
    /**
     * Cria uma nova proposta de serviço
     *
     * @param proposal Dados da proposta a ser criada
     * @return Proposta criada com ID
     */
    suspend fun createProposal(proposal: ServiceProposal): DefaultResult<ServiceProposal>
    
    /**
     * Lista as propostas criadas pelo cliente autenticado
     *
     * @param page Página para paginação
     * @return Lista paginada de propostas do cliente
     */
    suspend fun getClientProposals(page: Int = 0): DefaultResult<List<ServiceProposal>>
    
    /**
     * Obtém detalhes de uma proposta específica
     *
     * @param proposalId ID da proposta
     * @return Detalhes completos da proposta
     */
    suspend fun getProposalDetails(proposalId: String): DefaultResult<ServiceProposal>
    
    /**
     * Atualiza uma proposta (antes de aceitar resposta)
     */
    suspend fun updateProposal(proposal: ServiceProposal): DefaultResult<ServiceProposal>
    
    /**
     * Cancela uma proposta
     */
    suspend fun cancelProposal(proposalId: String, reason: String): DefaultResult<Unit>
    
    /**
     * Obtém respostas dos profissionais para uma proposta
     */
    suspend fun getProposalResponses(proposalId: String): DefaultResult<List<ProposalResponse>>
    
    /**
     * Aceita a resposta de um profissional
     */
    suspend fun acceptProposalResponse(responseId: String): DefaultResult<ServiceProposal>
    
    // ========== PROFISSIONAL ==========
    
    /**
     * Lista propostas disponíveis na região do profissional
     *
     * @param professionalId ID do profissional
     * @param page Página para paginação
     * @return Propostas disponíveis para o profissional
     */
    suspend fun getAvailableProposals(page: Int = 0): DefaultResult<List<ServiceProposal>>
    
    /**
     * Lista propostas aceitas pelo profissional autenticado
     */
    suspend fun getProfessionalProposals(): DefaultResult<List<ServiceProposal>>
    
    /**
     * Aceita uma proposta
     */
    suspend fun acceptProposal(proposalId: String): DefaultResult<ServiceProposal>
    
    /**
     * Recusa uma proposta
     */
    suspend fun declineProposal(proposalId: String, reason: String? = null): DefaultResult<Unit>
    
    /**
     * Marca proposta como "em progresso"
     */
    suspend fun startProposal(proposalId: String): DefaultResult<ServiceProposal>
    
    /**
     * Marca proposta como "concluída"
     */
    suspend fun completeProposal(proposalId: String): DefaultResult<ServiceProposal>
    
    // ========== AVALIAÇÕES ==========
    
    /**
     * Deixa uma avaliação para um serviço completado
     */
    suspend fun submitReview(review: ServiceReview): DefaultResult<ServiceReview>
    
    /**
     * Obtém avaliações recebidas pelo profissional
     */
    suspend fun getProfessionalReviews(professionalId: String): DefaultResult<List<ServiceReview>>
    
    /**
     * Obtém avaliações deixadas pelo cliente
     */
    suspend fun getClientReviews(): DefaultResult<List<ServiceReview>>
}
