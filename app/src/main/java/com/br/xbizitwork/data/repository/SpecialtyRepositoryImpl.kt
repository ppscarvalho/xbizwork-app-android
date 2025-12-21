package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.mappers.toDomain
import com.br.xbizitwork.data.remote.specialty.datasource.SpecialtyRemoteDataSource
import com.br.xbizitwork.domain.repository.SpecialtyRepository
import com.br.xbizitwork.domain.result.specialty.SpecialtyResult
import javax.inject.Inject

class SpecialtyRepositoryImpl @Inject constructor(
    private val remoteDataSource: SpecialtyRemoteDataSource
) : SpecialtyRepository {
    override suspend fun getSpecialtiesByCategory(categoryId: Int): DefaultResult<List<SpecialtyResult>> {
        return try {
            val response = remoteDataSource.getSpecialtiesByCategory(categoryId)
            
            if (response.isSuccessful && response.data != null) {
                DefaultResult.Success(response.data.toDomain())
            } else {
                DefaultResult.Error(message = response.message ?: "Erro ao buscar especialidades")
            }
        } catch (e: Exception) {
            DefaultResult.Error(message = e.message ?: "Erro desconhecido")
        }
    }
}
