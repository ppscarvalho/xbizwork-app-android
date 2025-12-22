package com.br.xbizitwork.ui.presentation.features.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.model.profile.ProfileResultValidation
import com.br.xbizitwork.domain.model.profile.UpdateProfileRequestModel
import com.br.xbizitwork.domain.usecase.cep.GetCepUseCase
import com.br.xbizitwork.domain.usecase.profile.UpdateProfileUseCase
import com.br.xbizitwork.domain.usecase.profile.ValidateProfileUseCase
import com.br.xbizitwork.domain.usecase.session.GetAuthSessionUseCase
import com.br.xbizitwork.domain.usecase.user.GetUserByIdUseCase
import com.br.xbizitwork.ui.presentation.features.profile.events.EditProfileEvent
import com.br.xbizitwork.ui.presentation.features.profile.state.EditProfileUIState
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

/**
 * ViewModel para gerenciar o estado da tela de edição de perfil
 * ATUALIZADO para buscar dados completos da API
 */
@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val validateProfileUseCase: ValidateProfileUseCase,
    private val getAuthSessionUseCase: GetAuthSessionUseCase,
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val getCepUseCase: GetCepUseCase,
) : ViewModel() {

    // ============= STATE MANAGEMENT =============
    private val _uiState: MutableStateFlow<EditProfileUIState> = MutableStateFlow(EditProfileUIState())
    val uiState: StateFlow<EditProfileUIState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = EditProfileUIState()
    )

    // ============= SIDE EFFECTS =============
    private val _App_sideEffectChannel = Channel<AppSideEffect>(capacity = Channel.Factory.BUFFERED)
    val sideEffectChannel = _App_sideEffectChannel.receiveAsFlow()

    // ============= INIT =============
    init {
        loadUserProfile()
    }

    // ============= PUBLIC FUNCTIONS =============
    fun onEvent(event: EditProfileEvent) {
        when (event) {
            // Dados básicos
            is EditProfileEvent.OnNameChanged -> {
                _uiState.update { it.copy(name = event.name, hasChanges = true) }
                validateForm()
            }
            is EditProfileEvent.OnCpfChanged -> {
                _uiState.update { it.copy(cpf = event.cpf, hasChanges = true) }
                validateForm()
            }
            is EditProfileEvent.OnDateOfBirthChanged -> {
                _uiState.update { it.copy(dateOfBirth = event.date, hasChanges = true) }
            }
            is EditProfileEvent.OnGenderChanged -> {
                _uiState.update { it.copy(gender = event.gender, hasChanges = true) }
            }

            // Contato
            is EditProfileEvent.OnEmailChanged -> {
                _uiState.update { it.copy(email = event.email, hasChanges = true) }
                validateForm()
            }
            is EditProfileEvent.OnPhoneChanged -> {
                _uiState.update { it.copy(phoneNumber = event.phone, hasChanges = true) }
                validateForm()
            }

            // Endereço
            is EditProfileEvent.OnZipCodeChanged -> {
                _uiState.update { it.copy(zipCode = event.zipCode, hasChanges = true) }
            }
            EditProfileEvent.OnZipCodeBlur -> {
                // ✅ Busca endereço automaticamente quando sai do campo CEP
                searchAddressByCep()
            }
            is EditProfileEvent.OnAddressChanged -> {
                _uiState.update { it.copy(address = event.address, hasChanges = true) }
            }
            is EditProfileEvent.OnNumberChanged -> {
                _uiState.update { it.copy(number = event.number, hasChanges = true) }
            }
            is EditProfileEvent.OnNeighborhoodChanged -> {
                _uiState.update { it.copy(neighborhood = event.neighborhood, hasChanges = true) }
            }
            is EditProfileEvent.OnCityChanged -> {
                _uiState.update { it.copy(city = event.city, hasChanges = true) }
            }
            is EditProfileEvent.OnStateChanged -> {
                _uiState.update { it.copy(state = event.state, hasChanges = true) }
            }

            // Foto
            is EditProfileEvent.OnPhotoSelected -> {
                _uiState.update { it.copy(profilePhotoUri = event.uri, hasChanges = true) }
            }
            EditProfileEvent.OnPhotoRemove -> {
                _uiState.update { it.copy(profilePhotoUri = null, hasChanges = true) }
            }

            // Ações
            EditProfileEvent.OnSaveClick -> onUpdateProfile()
            EditProfileEvent.OnCancelClick -> handleCancel()
            EditProfileEvent.OnConfirmCancel -> {
                _uiState.update { it.copy(showConfirmDialog = false) }
            }
            EditProfileEvent.OnDismissConfirmDialog -> {
                _uiState.update { it.copy(showConfirmDialog = false) }
            }
            EditProfileEvent.OnScreenLoad -> loadUserProfile()
        }
    }

    // ============= PRIVATE FUNCTIONS =============

    /**
     * Carrega dados COMPLETOS do usuário da API
     * NÃO usa apenas a sessão!
     */
    private fun loadUserProfile() {
        viewModelScope.launch {
            logInfo("LOAD_PROFILE", "🔄 Iniciando carregamento do perfil...")

            // Primeiro pega o userId da sessão
            getAuthSessionUseCase.invoke().collect { session ->
                logInfo("LOAD_PROFILE", "📧 Sessão: email=${session.email}, name=${session.name}")

                // TODO: A sessão deveria ter o userId também!
                // Por enquanto, vou assumir userId = 13 como exemplo
                // Você precisa ajustar para pegar o userId correto da sessão
                val userId = 13 // FIXME: Pegar da sessão real

                logInfo("LOAD_PROFILE", "🔑 userId=$userId")

                // Agora busca dados completos da API
                getUserByIdUseCase.invoke(
                    GetUserByIdUseCase.Parameters(userId)
                ).collectUiState(
                    onLoading = {
                        logInfo("LOAD_PROFILE", "⏳ Loading...")
                        _uiState.update { it.copy(isLoading = true) }
                    },
                    onSuccess = { user ->
                        logInfo("LOAD_PROFILE", "✅ Dados recebidos da API:")
                        logInfo("LOAD_PROFILE", "  - id: ${user.id}")
                        logInfo("LOAD_PROFILE", "  - name: ${user.name}")
                        logInfo("LOAD_PROFILE", "  - cpf: ${user.cpf}")
                        logInfo("LOAD_PROFILE", "  - gender: ${user.gender}")
                        logInfo("LOAD_PROFILE", "  - mobilePhone: ${user.mobilePhone}")
                        logInfo("LOAD_PROFILE", "  - city: ${user.city}")
                        logInfo("LOAD_PROFILE", "  - state: ${user.state}")
                        logInfo("LOAD_PROFILE", "  - status: ${user.status}")
                        logInfo("LOAD_PROFILE", "  - registration: ${user.registration}")

                        _uiState.update {
                            it.copy(
                                userId = user.id,
                                name = user.name,
                                cpf = user.cpf ?: "",
                                dateOfBirth = user.dateOfBirth,
                                gender = user.gender ?: "",
                                email = session.email, // Email vem da sessão
                                phoneNumber = user.mobilePhone ?: "",
                                zipCode = user.zipCode ?: "",
                                address = user.address ?: "",
                                number = user.number ?: "",
                                neighborhood = user.neighborhood ?: "",
                                city = user.city ?: "",
                                state = user.state ?: "",
                                latitude = user.latitude?.toDouble(),
                                longitude = user.longitude?.toDouble(),
                                isLoading = false
                            )
                        }
                        logInfo("LOAD_PROFILE", "✅ Estado atualizado com sucesso!")

                        // ✅ VALIDAR FORMULÁRIO APÓS CARREGAR!
                        validateForm()
                    },
                    onFailure = { error ->
                        logInfo("LOAD_PROFILE", "❌ ERRO: ${error.message}")
                        logInfo("LOAD_PROFILE", "❌ StackTrace: ${error.stackTraceToString()}")

                        // ⚠️ SE FOR ERRO 401 (token inválido), navega ao login
                        if (error is IllegalStateException && error.message?.contains("Token inválido") == true) {
                            logInfo("LOAD_PROFILE", "🔐 Token expirado! Navegando ao login...")

                            viewModelScope.launch {
                                _App_sideEffectChannel.send(AppSideEffect.NavigateToLogin)
                            }

                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = "Sessão expirada. Faça login novamente."
                                )
                            }
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

    /**
     * Valida o formulário usando ValidateProfileUseCase
     * Seguindo o mesmo padrão do SignUpViewModel
     */
    private fun validateForm() {
        val state = _uiState.value

        // Usa o UseCase de validação
        val validation = validateProfileUseCase(
            name = state.name,
            email = state.email,
            phone = state.phoneNumber
        )

        // Processa o resultado da validação
        var nameError: String? = null
        var emailError: String? = null
        var phoneError: String? = null
        var isValid = false

        when (validation) {
            ProfileResultValidation.EmptyName -> {
                nameError = "Nome é obrigatório"
            }
            ProfileResultValidation.NameTooShort -> {
                nameError = "Nome deve ter pelo menos 3 caracteres"
            }
            ProfileResultValidation.InvalidEmail -> {
                emailError = "Email inválido"
            }
            ProfileResultValidation.InvalidPhone -> {
                phoneError = "Telefone deve ter 11 dígitos"
            }
            ProfileResultValidation.Valid -> {
                isValid = true
            }
        }

        _uiState.update {
            it.copy(
                nameError = nameError,
                emailError = emailError,
                phoneError = phoneError,
                isFormValid = isValid,
                hasChanges = true
            )
        }

        logInfo("VALIDATE_FORM", "Form valid: $isValid, Validation: $validation")
    }

    /**
     * Atualiza o perfil usando UpdateProfileUseCase
     * CHAMA A API DE VERDADE! Não é mais fake/TODO
     * Seguindo o mesmo padrão do SignUpViewModel
     */
    private fun onUpdateProfile() {
        viewModelScope.launch {
            val state = _uiState.value

            // Chama o UseCase que fará a chamada real à API
            // AGORA ENVIA TODOS OS CAMPOS DO FORMULÁRIO + ID + LOCALIZAÇÃO!
            updateProfileUseCase.invoke(
                parameters = UpdateProfileUseCase.Parameters(
                    UpdateProfileRequestModel(
                        // ID do usuário
                        id = state.userId,

                        // Dados básicos
                        name = state.name.trim(),
                        cpf = state.cpf.takeIf { it.isNotBlank() },
                        dateOfBirth = state.dateOfBirth,
                        gender = state.gender.takeIf { it.isNotBlank() },

                        // Contato
                        email = state.email.trim(),
                        mobilePhone = state.phoneNumber.takeIf { it.isNotBlank() },

                        // Endereço
                        zipCode = state.zipCode.takeIf { it.isNotBlank() },
                        address = state.address.takeIf { it.isNotBlank() },
                        number = state.number.takeIf { it.isNotBlank() },
                        neighborhood = state.neighborhood.takeIf { it.isNotBlank() },
                        city = state.city.takeIf { it.isNotBlank() },
                        state = state.state.takeIf { it.isNotBlank() },

                        // Localização
                        latitude = state.latitude,
                        longitude = state.longitude
                    )
                )
            ).collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                    logInfo("UPDATE_PROFILE", "Loading...")
                },
                onSuccess = { response ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = response.isSuccessful,
                            successMessage = response.message,
                            hasChanges = false
                        )
                    }
                    _App_sideEffectChannel.send(AppSideEffect.ShowToast(response.message))
                    logInfo("UPDATE_PROFILE", "Success: ${response.message}")

                    // ✅ NAVEGA DE VOLTA AUTOMATICAMENTE APÓS SUCESSO!
                    _App_sideEffectChannel.send(AppSideEffect.NavigateBack)
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Erro ao atualizar perfil"
                        )
                    }
                    _App_sideEffectChannel.send(
                        AppSideEffect.ShowToast(error.message ?: "Erro ao atualizar perfil")
                    )
                    logInfo("UPDATE_PROFILE", "Error: ${error.message}")
                }
            )
        }
    }

    /**
     * ✅ BUSCA ENDEREÇO POR CEP
     * Preenche automaticamente: logradouro, bairro, cidade e estado
     */
    private fun searchAddressByCep() {
        val cep = _uiState.value.zipCode.replace("-", "").trim()

        // Valida CEP (8 dígitos)
        if (cep.length != 8) {
            logInfo("SEARCH_CEP", "CEP inválido: $cep")
            return
        }

        viewModelScope.launch {
            getCepUseCase.invoke(
                parameters = GetCepUseCase.Parameters(cep = cep)
            ).collectUiState(
                onLoading = {
                    logInfo("SEARCH_CEP", "Buscando CEP: $cep...")
                },
                onSuccess = { cepModel ->
                    // ✅ PREENCHE OS CAMPOS AUTOMATICAMENTE!
                    _uiState.update {
                        it.copy(
                            address = cepModel.logradouro,
                            neighborhood = cepModel.bairro,
                            city = cepModel.cidade,
                            state = cepModel.estado,
                            hasChanges = true
                        )
                    }
                    _App_sideEffectChannel.send(AppSideEffect.ShowToast("Endereço encontrado!"))
                    logInfo("SEARCH_CEP", "CEP encontrado: ${cepModel.logradouro}, ${cepModel.bairro}")
                },
                onFailure = { error ->
                    _App_sideEffectChannel.send(
                        AppSideEffect.ShowToast(error.message ?: "CEP não encontrado")
                    )
                    logInfo("SEARCH_CEP", "Erro: ${error.message}")
                }
            )
        }
    }

    /**
     * ✅ MELHORADO: Sempre permite voltar, sem confirmação
     * Melhor UX: usuário tem controle total
     */
    private fun handleCancel() {
        viewModelScope.launch {
            _App_sideEffectChannel.send(AppSideEffect.NavigateBack)
        }
    }
}
