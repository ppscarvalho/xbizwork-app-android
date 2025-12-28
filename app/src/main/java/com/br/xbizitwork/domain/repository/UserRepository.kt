package com.br.xbizitwork.domain.repository

import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.domain.model.user.UserModel

interface UserRepository {
    suspend fun getUserById(userId: Int): DefaultResult<UserModel>
}