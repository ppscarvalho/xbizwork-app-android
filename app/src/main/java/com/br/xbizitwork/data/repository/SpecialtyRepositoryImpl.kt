package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.local.auth.datastore.AuthSessionLocalDataSource
import com.br.xbizitwork.data.mappers.toDomain
import com.br.xbizitwork.data.remote.specialty.datasource.SpecialtyRemoteDataSource
import com.br.xbizitwork.domain.repository.SpecialtyRepository
import com.br.xbizitwork.domain.result.specialty.SpecialtyResult
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SpecialtyRepositoryImpl @Inject constructor(
    private val remoteDataSource: SpecialtyRemoteDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : SpecialtyRepository {

    override suspend fun getSpecialtiesByCategory(categoryId: Int): DefaultResult<List<SpecialtyResult>> =
        withContext(coroutineDispatcherProvider.io()) {
            val response = remoteDataSource.getSpecialtiesByCategory(categoryId)

            if (response.isSuccessful && response.data != null)
                DefaultResult.Success(response.data.toDomain())
            else
                DefaultResult.Error(message = response.message)
        }
}
