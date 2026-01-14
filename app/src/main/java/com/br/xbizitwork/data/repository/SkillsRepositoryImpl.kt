package com.br.xbizitwork.data.repository

import com.br.xbizitwork.core.dispatcher.CoroutineDispatcherProvider
import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.skills.SaveUserSkillsRequestModel
import com.br.xbizitwork.domain.repository.SkillsRepository
import com.br.xbizitwork.domain.source.skills.SkillsRemoteDataSource
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Implementação do SkillsRepository
 * Delega as operações para o RemoteDataSource
 * Seguindo o mesmo padrão do ProfileRepositoryImpl
 */
class SkillsRepositoryImpl @Inject constructor(
    private val remoteDataSource: SkillsRemoteDataSource,
    private val coroutineDispatcherProvider: CoroutineDispatcherProvider
) : SkillsRepository {

    override suspend fun saveUserSkills(model: SaveUserSkillsRequestModel): DefaultResult<ApiResultModel> =
        withContext(coroutineDispatcherProvider.io()) {
            remoteDataSource.saveUserSkills(model)
        }

    override suspend fun getUserSkills(userId: Int): DefaultResult<List<Int>> =
        withContext(coroutineDispatcherProvider.io()) {
            remoteDataSource.getUserSkills(userId)
        }
}

