package com.br.xbizitwork.domain.usecase.faq

import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.model.faq.FaqSection
import com.br.xbizitwork.domain.repository.FaqRepository
import com.br.xbizitwork.core.result.DefaultResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for fetching public FAQ sections
 */
interface GetPublicFaqSectionsUseCase {
    operator fun invoke(parameters: Parameters = Parameters): Flow<UiState<List<FaqSection>>>
    
    object Parameters
}

/**
 * Implementation of GetPublicFaqSectionsUseCase
 * Following the same pattern as GetUserSkillsUseCase
 */
class GetPublicFaqSectionsUseCaseImpl @Inject constructor(
    private val repository: FaqRepository
) : GetPublicFaqSectionsUseCase, FlowUseCase<GetPublicFaqSectionsUseCase.Parameters, List<FaqSection>>() {

    override suspend fun executeTask(parameters: GetPublicFaqSectionsUseCase.Parameters): UiState<List<FaqSection>> {
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
