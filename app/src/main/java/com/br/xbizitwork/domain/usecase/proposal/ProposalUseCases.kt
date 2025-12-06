package com.br.xbizitwork.domain.usecase.proposal

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.service.ServiceProposal
import com.br.xbizitwork.domain.repository.ProposalRepository
import javax.inject.Inject

/**
 * Use Case: Criar Nova Proposta
 *
 * Responsabilidade: Validar e criar uma nova proposta de serviço
 *
 * Fluxo:
 * 1. Valida dados da proposta (título, descrição, localização, etc)
 * 2. Valida valores monetários
 * 3. Envia para API
 * 4. Retorna proposta criada
 *
 * Exemplo de uso:
 * ```kotlin
 * val proposal = ServiceProposal(
 *     clientId = "client_123",
 *     title = "Pintar minha sala",
 *     description = "Sala de 20m², pintura branca",
 *     category = "PAINTING",
 *     ...
 * )
 * val result = createProposalUseCase(proposal)
 * ```
 */
class CreateProposalUseCase @Inject constructor(
    private val proposalRepository: ProposalRepository,
) {
    suspend operator fun invoke(proposal: ServiceProposal): DefaultResult<ServiceProposal> {
        return try {
            // Validações
            if (proposal.title.isBlank()) {
                return DefaultResult.Error(
                    code = "INVALID_TITLE",
                    message = "Título da proposta é obrigatório"
                )
            }
            
            if (proposal.description.isBlank()) {
                return DefaultResult.Error(
                    code = "INVALID_DESCRIPTION",
                    message = "Descrição é obrigatória"
                )
            }
            
            if (proposal.estimatedBudgetMin != null && proposal.estimatedBudgetMax != null) {
                if (proposal.estimatedBudgetMin!! > proposal.estimatedBudgetMax!!) {
                    return DefaultResult.Error(
                        code = "INVALID_BUDGET",
                        message = "Orçamento mínimo não pode ser maior que máximo"
                    )
                }
            }
            
            // Criação via repository
            proposalRepository.createProposal(proposal)
        } catch (e: Exception) {
            DefaultResult.Error(
                code = "CREATE_PROPOSAL_ERROR",
                message = "Erro ao criar proposta: ${e.message}"
            )
        }
    }
}

/**
 * Use Case: Aceitar Proposta
 *
 * Responsabilidade: Profissional aceita uma proposta de cliente
 */
class AcceptProposalUseCase @Inject constructor(
    private val proposalRepository: ProposalRepository,
) {
    suspend operator fun invoke(proposalId: String): DefaultResult<ServiceProposal> {
        return try {
            if (proposalId.isBlank()) {
                return DefaultResult.Error(
                    code = "INVALID_PROPOSAL_ID",
                    message = "ID da proposta é obrigatório"
                )
            }
            
            proposalRepository.acceptProposal(proposalId)
        } catch (e: Exception) {
            DefaultResult.Error(
                code = "ACCEPT_PROPOSAL_ERROR",
                message = "Erro ao aceitar proposta: ${e.message}"
            )
        }
    }
}

/**
 * Use Case: Recusar Proposta
 *
 * Responsabilidade: Profissional recusa uma proposta de cliente
 */
class DeclineProposalUseCase @Inject constructor(
    private val proposalRepository: ProposalRepository,
) {
    suspend operator fun invoke(
        proposalId: String,
        reason: String? = null
    ): DefaultResult<Unit> {
        return try {
            if (proposalId.isBlank()) {
                return DefaultResult.Error(
                    code = "INVALID_PROPOSAL_ID",
                    message = "ID da proposta é obrigatório"
                )
            }
            
            proposalRepository.declineProposal(proposalId, reason)
        } catch (e: Exception) {
            DefaultResult.Error(
                code = "DECLINE_PROPOSAL_ERROR",
                message = "Erro ao recusar proposta: ${e.message}"
            )
        }
    }
}
