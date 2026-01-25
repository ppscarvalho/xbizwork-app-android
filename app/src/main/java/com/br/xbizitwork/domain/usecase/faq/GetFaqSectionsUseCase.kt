package com.br.xbizitwork.domain.usecase.faq

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.faq.FaqSection
import com.br.xbizitwork.domain.repository.FaqRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para obter seções públicas de FAQ
 * Retorna lista hierárquica de seções com suas perguntas
 */
interface GetFaqSectionsUseCase {
    operator fun invoke(): Flow<UiState<List<FaqSection>>>
}

/**
 * Implementação do GetFaqSectionsUseCase
 * Segue o mesmo padrão do GetUserSkillsUseCase
 */
class GetFaqSectionsUseCaseImpl @Inject constructor(
    private val repository: FaqRepository
) : GetFaqSectionsUseCase, FlowUseCase<Unit, List<FaqSection>>() {

    override suspend fun executeTask(parameters: Unit): UiState<List<FaqSection>> {
        return try {
            when (val result = repository.getPublicFaqSections()) {
                is DefaultResult.Success -> {
                    UiState.Success(result.data)
                }
                is DefaultResult.Error -> {
                    UiState.Error(Throwable(result.message))
                }
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
