package com.br.xbizitwork.ui.presentation.features.auth.domain.usecase

import com.br.xbizitwork.ui.presentation.features.auth.data.local.model.AuthSession
import kotlinx.coroutines.flow.Flow

interface GetAuthSessionUseCase{
    suspend operator fun invoke(parameters: Unit = Unit): Flow<AuthSession>
}