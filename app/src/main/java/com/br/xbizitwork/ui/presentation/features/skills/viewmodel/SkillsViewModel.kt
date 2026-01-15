package com.br.xbizitwork.ui.presentation.features.skills.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.model.skills.SaveUserSkillsRequestModel
import com.br.xbizitwork.domain.usecase.category.GetCategoriesUseCase
import com.br.xbizitwork.domain.usecase.session.GetAuthSessionUseCase
import com.br.xbizitwork.domain.usecase.skills.GetUserSkillsUseCase
import com.br.xbizitwork.domain.usecase.skills.SaveUserSkillsUseCase
import com.br.xbizitwork.ui.presentation.features.skills.events.SkillsEvent
import com.br.xbizitwork.ui.presentation.features.skills.state.SkillItemUiState
import com.br.xbizitwork.ui.presentation.features.skills.state.SkillUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SkillsViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val saveUserSkillsUseCase: SaveUserSkillsUseCase,
    private val getUserSkillsUseCase: GetUserSkillsUseCase,
    private val getAuthSessionUseCase: GetAuthSessionUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<SkillUiState> = MutableStateFlow(SkillUiState())
    val uiState: StateFlow<SkillUiState> = _uiState.asStateFlow()

    private val _appSideEffectChannel = Channel<AppSideEffect>()
    val sideEffectChannel = _appSideEffectChannel.receiveAsFlow()

    init {
        loadCategories()
        loadUserSkills()
    }

    fun onEvent(event: SkillsEvent){
        when(event){
            SkillsEvent.OnLoadCategories -> loadCategories()
            is SkillsEvent.OnSaveSkills -> saveSkills(event.selectedSkills)
        }
    }

    /**
     * Salva as habilidades selecionadas pelo usuário
     * Seguindo o padrão do UpdateProfileUseCase
     */
    private fun saveSkills(selectedSkills: List<SkillItemUiState>) {
        viewModelScope.launch {
            // Skills que estavam salvas antes
            val previouslySaved = _uiState.value.savedSkillIds

            // Skills marcadas agora
            val currentSelected = selectedSkills.map { it.id }

            // Calcula o que foi adicionado e removido
            val added = currentSelected.filter { it !in previouslySaved }
            val removed = previouslySaved.filter { it !in currentSelected }

            // Log detalhado das mudanças
            logInfo("SKILLS_SAVE_VM", "🎯 Salvando habilidades:")
            logInfo("SKILLS_SAVE_VM", "  📋 Antes: $previouslySaved")
            logInfo("SKILLS_SAVE_VM", "  📋 Agora: $currentSelected")

            if (added.isNotEmpty()) {
                logInfo("SKILLS_SAVE_VM", "  ✅ ADICIONADAS: $added")
                added.forEach { id ->
                    val skill = selectedSkills.find { it.id == id }
                    logInfo("SKILLS_SAVE_VM", "     + ${skill?.description}")
                }
            }

            if (removed.isNotEmpty()) {
                logInfo("SKILLS_SAVE_VM", "  ❌ REMOVIDAS: $removed")
                removed.forEach { id ->
                    val category = _uiState.value.categories.find { it.id == id }
                    logInfo("SKILLS_SAVE_VM", "     - ${category?.description}")
                }
            }

            if (added.isEmpty() && removed.isEmpty()) {
                logInfo("SKILLS_SAVE_VM", "  ℹ️ Nenhuma mudança detectada")
            }

            logInfo("SKILLS_SAVE_VM", "  📤 Enviando para backend: $currentSelected")

            saveUserSkillsUseCase.invoke(
                parameters = SaveUserSkillsUseCase.Parameters(
                    SaveUserSkillsRequestModel(categoryIds = currentSelected)
                )
            ).collectUiState(
                onLoading = {
                    logInfo("SKILLS_SAVE_VM", "⏳ Loading...")
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = { response ->
                    logInfo("SKILLS_SAVE_VM", "✅ Sucesso: ${response.message}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = response.isSuccessful,
                            savedSkillIds = currentSelected // Atualiza o estado salvo
                        )
                    }
                    _appSideEffectChannel.send(AppSideEffect.ShowToast(response.message))
                },
                onFailure = { error ->
                    logInfo("SKILLS_SAVE_VM", "❌ Erro: ${error.message}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Erro ao salvar habilidades"
                        )
                    }
                    _appSideEffectChannel.send(
                        AppSideEffect.ShowToast(error.message ?: "Erro ao salvar habilidades")
                    )
                }
            )
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            getCategoriesUseCase.invoke().collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = { categories ->
                    _uiState.update {
                        it.copy(
                            categories = categories,
                            isLoading = false
                        )
                    }
                },
                onFailure = { error ->
                    logInfo("SKILLS_LOAD_VM", "❌ ERRO: ${error.message}")
                    logInfo("SKILLS_LOAD_VM", "❌ StackTrace: ${error.stackTraceToString()}")

                    // ⚠️ SE FOR ERRO 401 (token inválido), navega ao login
                    //if (error is IllegalStateException && error.message?.contains("Token inválido") == true) {
                   if (error.message?.contains("Token inválido", ignoreCase = true) == true) {
                        logInfo("SKILLS_LOAD_VM", "🔐 Token expirado! Navegando ao login...")

                        viewModelScope.launch {
                            _appSideEffectChannel.send(AppSideEffect.NavigateToHomeGraph)
                        }

                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = error.message ?: "Erro ao carregar categorias"
                            )
                        }
                       val errorMessage = error.message ?: "Sua sessão expirou!"
                       _appSideEffectChannel.send(
                           AppSideEffect.ShowToast("Sua sessão expirou. ${errorMessage}")
                       )
                    } else {
                        // Outros erros
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                errorMessage = "Erro ao carregar perfil: ${error.message}"
                            )
                        }
                    }
                }
            )
        }
    }

    /**
     * Carrega as habilidades já salvas do usuário
     * Obtém o userId da sessão e busca os IDs das categorias salvas
     */

    private fun loadUserSkills(){
        viewModelScope.launch {
            logInfo("SKILLS_LOAD_VM", "🔄 Iniciando carregamento das habilidades...")

            // Primeiro pega o userId da sessão
            getAuthSessionUseCase.invoke().collect { session ->
                logInfo("SKILLS_LOAD_VM", "📧 Sessão: email=${session.email}, name=${session.name}")
                val userId = session.id
                logInfo("SKILLS_LOAD_VM", "🔑 userId=$userId")

                // Agora busca dados completos da API
                getUserSkillsUseCase.invoke(
                    parameters = GetUserSkillsUseCase.Parameters(userId = userId)
                ).collectUiState(
                    onLoading = {
                        logInfo("SKILLS_LOAD_VM", "⏳ Loading skills...")
                        // Não mostra loading para não atrapalhar o carregamento das categorias
                    },
                    onSuccess = { skillIds ->
                        logInfo("SKILLS_LOAD_VM", "✅ Skills carregadas: $skillIds")
                        logInfo("SKILLS_LOAD_VM", "📋 Atualizando UI com savedSkillIds: $skillIds")
                        _uiState.update {
                            it.copy(savedSkillIds = skillIds)
                        }
                    },
                    onFailure = { error ->
                        logInfo("SKILLS_LOAD_VM", "❌ ERRO: ${error.message}")
                        logInfo("SKILLS_LOAD_VM", "❌ StackTrace: ${error.stackTraceToString()}")

                        // ⚠️ SE FOR ERRO 401 (token inválido), navega ao login
                        //if (error is IllegalStateException && error.message?.contains("Token inválido") == true) {
                        if (error.message?.contains("Token inválido", ignoreCase = true) == true) {
                            logInfo("SKILLS_LOAD_VM", "🔐 Token expirado! Navegando ao login...")

                            viewModelScope.launch {
                                _appSideEffectChannel.send(AppSideEffect.NavigateToHomeGraph)
                            }

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = "Sessão expirada. Faça login novamente."
                                )
                            }

                            val errorMessage = error.message ?: "Sua sessão expirou!"
                            _appSideEffectChannel.send(
                                AppSideEffect.ShowToast("Sua sessão expirou. ${errorMessage}")
                            )
                        } else {
                            // Outros erros
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = "Erro ao carregar perfil: ${error.message}"
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}