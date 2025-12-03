package com.br.xbizitwork.core.data.remote.user

import com.br.xbizitwork.core.data.remote.user.request.CreateUserRequest
import com.br.xbizitwork.core.data.remote.common.response.ApiResultResponse
import com.br.xbizitwork.core.data.remote.user.response.CreateUserResponse

interface UserApiService {
    suspend fun createUser(createUserRequest: CreateUserRequest): ApiResultResponse
    suspend fun getAllUsers(): CreateUserResponse
}