package com.br.xbizitwork.ui.presentation.features.auth.domain.usecase

import com.br.xbizitwork.core.state.UiState
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.request.SignInRequestModel
import com.br.xbizitwork.ui.presentation.features.auth.data.remote.response.SignInResponseModel
import kotlinx.coroutines.flow.Flow

interface SignInUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<SignInResponseModel>>
    data class Parameters(val signInRequestModel: SignInRequestModel)
}
