package com.br.xbizitwork.data.repository.auth

import com.br.xbizitwork.application.mappers.toDomainResponse
import com.br.xbizitwork.application.mappers.toDomainResult
import com.br.xbizitwork.application.mappers.toSignInRequestModel
import com.br.xbizitwork.application.mappers.toSignUpRequestModel
import com.br.xbizitwork.core.result.DefaultResult
import com.br.xbizitwork.data.local.auth.datastore.interfaces.AuthSessionLocalDataSource
import com.br.xbizitwork.data.remote.auth.datasource.interfaces.UserAuthRemoteDataSource
import com.br.xbizitwork.domain.session.AuthSession
import com.br.xbizitwork.domain.model.auth.SignInModel
import com.br.xbizitwork.domain.model.auth.SignUpModel
import com.br.xbizitwork.domain.result.auth.SignInResult
import com.br.xbizitwork.domain.result.auth.SignUpResult
import com.br.xbizitwork.domain.repository.auth.UserAuthRepository
import com.br.xbizitwork.domain.common.DomainDefaultResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserAuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserAuthRemoteDataSource,
    private val localDataSource: AuthSessionLocalDataSource
): UserAuthRepository {
    override suspend fun signIn(signInModel: SignInModel): DomainDefaultResult<SignInResult> {
        // 1. Converte o DomainModel → LoginRequestModel (DTO para a API)
        val loginRequest = signInModel.toSignInRequestModel()

        // 2. Chama a API via DataSource
        val result = remoteDataSource.signIn(loginRequest)

        // 3. Converte DefaultResult -> DomainDefaultResult
        return when (result) {
            is DefaultResult.Success -> {
                val domainResponse = result.data.toDomainResponse()
                DomainDefaultResult.Success(domainResponse)
            }
            is DefaultResult.Error -> {
                DomainDefaultResult.Error(message = result.message)
            }
        }
    }

    override suspend fun signUp(signUpModel: SignUpModel): DomainDefaultResult<SignUpResult> {
        val sigUpnRequest = signUpModel.toSignUpRequestModel()
        val result = remoteDataSource.signUp(sigUpnRequest)
        return when (result) {
            is DefaultResult.Success -> {
                val domainResponse = result.data.toDomainResult()
                DomainDefaultResult.Success(domainResponse)
            }
            is DefaultResult.Error -> {
                DomainDefaultResult.Error(message = result.message)
            }
        }
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