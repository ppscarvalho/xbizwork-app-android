package com.br.xbizitwork.ui.presentation.features.menu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.usecase.session.RemoveAuthSessionUseCase
import com.br.xbizitwork.domain.usecase.session.GetAuthSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel do Menu
 * RESPONSABILIDADE: Apenas LOGOUT e navegação
 * NÃO deve ter lógica de edição de perfil!
 */
@HiltViewModel
class MenuViewModel @Inject constructor(
    private val removeAuthSessionUseCase: RemoveAuthSessionUseCase,
    private val getAuthSessionUseCase: GetAuthSessionUseCase
) : ViewModel() {

    // SideEffect Channel para notificações (Toast, etc)
    private val _appSideEffectChannel = Channel<AppSideEffect>(capacity = Channel.Factory.BUFFERED)
    val sideEffectChannel = _appSideEffectChannel.receiveAsFlow()

    /**
     * Realiza logout do usuário
     * Remove token da sessão
     */
    fun logout() {
        viewModelScope.launch {
            removeAuthSessionUseCase.invoke().collectUiState(
                onLoading = {
                    // Loading state if needed
                },
                onFailure = {
                    logInfo("REMOVE_TOKEN", "Erro ao remover token: ${it.message}")
                    _appSideEffectChannel.send(AppSideEffect.ShowToast("Erro ao fazer logout"))
                },
                onSuccess = {
                    logInfo("REMOVE_TOKEN", "Token removido com sucesso!")
                    _appSideEffectChannel.send(AppSideEffect.ShowToast("Você foi deslogado com sucesso!"))
                },
            )
        }
    }
}
