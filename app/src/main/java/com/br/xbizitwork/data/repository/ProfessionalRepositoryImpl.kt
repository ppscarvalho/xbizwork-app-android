package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.remote.professional.datasource.ProfessionalSearchRemoteDataSource
import com.br.xbizitwork.domain.model.professional.ProfessionalProfile
import com.br.xbizitwork.domain.model.professional.ProfessionalSearchBySkill
import com.br.xbizitwork.domain.model.service.SearchFilters
import com.br.xbizitwork.domain.model.service.SearchResult
import com.br.xbizitwork.domain.repository.ProfessionalRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementation of ProfessionalRepository
 * Delegates operations to RemoteDataSource
 * Following the same pattern as SkillsRepositoryImpl
 */
class ProfessionalRepositoryImpl @Inject constructor(
    private val searchRemoteDataSource: ProfessionalSearchRemoteDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : ProfessionalRepository {

    override suspend fun searchProfessionalsBySkill(skill: String): DefaultResult<List<ProfessionalSearchBySkill>> =
        withContext(coroutineDispatcherProvider.io()) {
            searchRemoteDataSource.searchProfessionalsBySkill(skill)
        }

    override suspend fun searchProfessionals(filters: SearchFilters): DefaultResult<SearchResult> {
        TODO("Not yet implemented - legacy search")
    }

    override suspend fun getProfessionalDetails(professionalId: String): DefaultResult<ProfessionalProfile> {
        TODO("Not yet implemented")
    }

    override suspend fun updateProfile(profile: ProfessionalProfile): DefaultResult<ProfessionalProfile> {
        TODO("Not yet implemented")
    }

    override suspend fun getTrendingProfessionals(count: Int): DefaultResult<List<ProfessionalProfile>> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteProfessionals(): DefaultResult<List<ProfessionalProfile>> {
        TODO("Not yet implemented")
    }

    override suspend fun addToFavorites(professionalId: String): DefaultResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromFavorites(professionalId: String): DefaultResult<Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getViewedProfessionals(): DefaultResult<List<ProfessionalProfile>> {
        TODO("Not yet implemented")
    }
}
