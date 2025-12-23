package com.br.xbizitwork.ui.presentation.features.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.usecase.session.GetAuthSessionUseCase
import com.br.xbizitwork.domain.usecase.session.RemoveAuthSessionUseCase
import com.br.xbizitwork.ui.presentation.features.home.state.HomeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAuthSessionUseCase: GetAuthSessionUseCase,
    private val removeAuthSessionUseCase: RemoveAuthSessionUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUIState> =  MutableStateFlow(HomeUIState())
    val uState: StateFlow<HomeUIState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUIState()
    )

    // ✅ NOVO: SideEffect Channel para notificações (Toast, etc)
    private val _App_sideEffectChannel = Channel<AppSideEffect>(capacity = Channel.Factory.BUFFERED)
    val sideEffectChannel = _App_sideEffectChannel.receiveAsFlow()

    init {
        // ✅ CORRIGIDO: Observar sessão continuamente em vez de apenas no onStart
        fetchUserName()
    }

    private fun fetchUserName() {
        viewModelScope.launch {
            getAuthSessionUseCase.invoke().collect{user ->
                _uiState.update { it.copy(userName = user.name) }
            }
        }
    }

    fun logout(){
        viewModelScope.launch {
            removeAuthSessionUseCase.invoke().collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onFailure = {
                    logInfo("REMOVE_TOKEN", "Erro ao remover token: ${it.message}")
                    _uiState.update { it.copy(isLoading = false) }
                    _App_sideEffectChannel.send(AppSideEffect.ShowToast("Erro ao fazer logout"))
                },
                onSuccess = {
                    logInfo("REMOVE_TOKEN", "Token removido com sucesso!")
                    // ✅ CORRIGIDO: Limpar userName do state e enviar SideEffect via Channel
                    _uiState.update { it.copy(
                        userName = null,
                        isLoading = false,
                        logoutMessage = null  // Limpa mensagem anterior
                    ) }
                    // ✅ NOVO: Enviar SideEffect via Channel em vez de atualizar state
                    viewModelScope.launch {
                        _App_sideEffectChannel.send(AppSideEffect.ShowToast("Você foi deslogado com sucesso!"))
                    }
                },
            )
        }
    }
}