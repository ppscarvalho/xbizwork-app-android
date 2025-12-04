package com.br.xbizitwork.data.repository.auth

import com.br.xbizitwork.core.model.api.ApiResultModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.local.auth.datastore.AuthSessionLocalDataSource
import com.br.xbizitwork.data.local.auth.model.AuthSession
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignInRequestModel
import com.br.xbizitwork.data.remote.auth.dtos.requests.SignUpRequestModel
import com.br.xbizitwork.data.remote.auth.source.UserAuthRemoteDataSource
import com.br.xbizitwork.data.remote.auth.dtos.responses.SignInResponseModel
import com.br.xbizitwork.domain.repository.auth.UserAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserAuthRemoteDataSource,
    private val localDataSource: AuthSessionLocalDataSource
): UserAuthRepository {
    override suspend fun signIn(signInRequestModel: SignInRequestModel): DefaultResult<SignInResponseModel> {
        return remoteDataSource.signIn(signInRequestModel)
    }

    override suspend fun signUp(signUpRequestModel: SignUpRequestModel): DefaultResult<ApiResultModel> {
        return remoteDataSource.signUp(signUpRequestModel)
    }

    override fun observeSession(): Flow<AuthSession> = localDataSource.observeSession()

    override suspend fun saveSession(
        name: String,
        email: String,
        token: String,
    ) {
        return localDataSource.saveSession(
            name = name,
            email = email,
            token = token
        )
    }

    override suspend fun getSession(): AuthSession?  = localDataSource.getSession()

    override suspend fun clearSession() = localDataSource.clearSession()
}