package com.br.xbizitwork.data.remote.user.datasource

import com.br.xbizitwork.data.remote.user.dtos.responses.UserApiResponse

interface UserRemoteDataSource {
    suspend fun getUserById(userId: Int): UserApiResponse
}