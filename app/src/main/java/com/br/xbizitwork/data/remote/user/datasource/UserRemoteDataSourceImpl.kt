package com.br.xbizitwork.data.remote.user.datasource

import com.br.xbizitwork.data.remote.user.api.UserApiService
import com.br.xbizitwork.data.remote.user.dtos.responses.UserApiResponse
import javax.inject.Inject

class UserRemoteDataSourceImpl @Inject constructor(
    private val userApiService: UserApiService
) : UserRemoteDataSource {
    override suspend fun getUserById(userId: Int): UserApiResponse {
        return userApiService.getUserById(userId)
    }
}