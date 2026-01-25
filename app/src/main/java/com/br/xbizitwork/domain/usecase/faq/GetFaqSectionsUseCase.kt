package com.br.xbizitwork.domain.usecase.faq

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.faq.FaqSectionModel
import com.br.xbizitwork.domain.repository.FaqRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Caso de uso para obter as seções de FAQ com suas perguntas
 * Segue o padrão do GetUserSkillsUseCase
 */
interface GetFaqSectionsUseCase {
    operator fun invoke(): Flow<UiState<List<FaqSectionModel>>>
}

/**
 * Implementação do GetFaqSectionsUseCase
 */
class GetFaqSectionsUseCaseImpl @Inject constructor(
    private val repository: FaqRepository
) : GetFaqSectionsUseCase, FlowUseCase<Unit, List<FaqSectionModel>>() {

    override suspend fun executeTask(parameters: Unit): UiState<List<FaqSectionModel>> {
        return try {
            when (val result = repository.getFaqSections()) {
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
