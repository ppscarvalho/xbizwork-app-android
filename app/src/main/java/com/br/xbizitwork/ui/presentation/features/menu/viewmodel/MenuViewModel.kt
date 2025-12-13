package com.br.xbizitwork.ui.presentation.features.menu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.usecase.session.RemoveAuthSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val removeAuthSessionUseCase: RemoveAuthSessionUseCase
) : ViewModel() {

    // ✅ SideEffect Channel para notificações (Toast, etc)
    private val _sideEffectChannel = Channel<SideEffect>(capacity = Channel.Factory.BUFFERED)
    val sideEffectChannel = _sideEffectChannel.receiveAsFlow()

    fun logout() {
        viewModelScope.launch {
            removeAuthSessionUseCase.invoke().collectUiState(
                onLoading = {
                    // Loading state if needed
                },
                onFailure = {
                    logInfo("REMOVE_TOKEN", "Erro ao remover token: ${it.message}")
                    _sideEffectChannel.send(SideEffect.ShowToast("Erro ao fazer logout"))
                },
                onSuccess = {
                    logInfo("REMOVE_TOKEN", "Token removido com sucesso!")
                    viewModelScope.launch {
                        _sideEffectChannel.send(SideEffect.ShowToast("Você foi deslogado com sucesso!"))
                    }
                },
            )
        }
    }
}
