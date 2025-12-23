package com.br.xbizitwork.domain.usecase.specialty

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.core.usecase.FlowUseCase
import com.br.xbizitwork.domain.repository.SpecialtyRepository
import com.br.xbizitwork.domain.result.specialty.SpecialtyResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementação do GetSpecialtiesByCategoryUseCase
 */
class GetSpecialtiesByCategoryUseCaseImpl @Inject constructor(
    private val repository: SpecialtyRepository
) : FlowUseCase<Int, List<SpecialtyResult>>() {

    override suspend fun executeTask(parameters: Int): UiState<List<SpecialtyResult>> {
        return try {
            when (val result = repository.getSpecialtiesByCategory(parameters)) {
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

typealias GetSpecialtiesByCategoryUseCase = GetSpecialtiesByCategoryUseCaseImpl

