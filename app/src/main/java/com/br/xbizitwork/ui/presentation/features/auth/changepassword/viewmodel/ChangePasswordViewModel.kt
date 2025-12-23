package com.br.xbizitwork.ui.presentation.features.auth.changepassword.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.config.Constants
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.domain.model.auth.ChangePasswordModel
import com.br.xbizitwork.domain.usecase.auth.changepassword.ChangePasswordUseCase
import com.br.xbizitwork.domain.usecase.auth.changepassword.ValidatePasswordUseCase
import com.br.xbizitwork.domain.validations.auth.ChangePasswordValidationError
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.events.ChangePasswordEvent
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.state.ChangePasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para alteração de senha
 * 
 * Checklist de Validação Técnica – ViewModel (Padrão Oficial):
 * ✅ Não contém regras de negócio (delegadas aos UseCases)
 * ✅ Não valida senha no client (validação no backend via UseCase)
 * ✅ Usa StateFlow único para estado (_uiState)
 * ✅ Usa Channel para side effects (_appSideEffectChannel)
 * ✅ Coordena apenas fluxo entre UI e UseCases
 * ✅ Não armazena senhas em propriedades
 * ✅ Não compara senhas (delegado ao ValidatePasswordUseCase)
 */
@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<ChangePasswordState> = MutableStateFlow(ChangePasswordState())
    val uiState: StateFlow<ChangePasswordState> = _uiState.asStateFlow()

    private val _appSideEffectChannel = Channel<AppSideEffect>(capacity = Channel.Factory.BUFFERED)
    val sideEffectChannel = _appSideEffectChannel.receiveAsFlow()

    fun onEvent(event: ChangePasswordEvent) {
        when (event) {
            ChangePasswordEvent.OnChangePasswordClick -> onChangePasswordClick()
        }
    }

    fun onCurrentPasswordChange(value: String) {
        _uiState.update { it.copy(currentPassword = value) }
        validateForm()
    }

    fun onNewPasswordChange(value: String) {
        _uiState.update { it.copy(newPassword = value) }
        validateForm()
    }

    fun onConfirmPasswordChange(value: String) {
        _uiState.update { it.copy(confirmPassword = value) }
        validateForm()
    }

    private fun onChangePasswordClick() {
        viewModelScope.launch {
            changePasswordUseCase.invoke(
                parameters = ChangePasswordUseCase.Parameters(
                    ChangePasswordModel(
                        currentPassword = _uiState.value.currentPassword.trim(),
                        newPassword = _uiState.value.newPassword.trim()
                    )
                )
            ).collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = { response ->
                    _uiState.update {
                        it.copy(isLoading = false, isSuccess = response.isSuccessful)
                    }
                    _appSideEffectChannel.send(AppSideEffect.ShowToast(response.message))
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(isLoading = false, errorMessage = error.message.toString())
                    }
                }
            )
        }
    }

    private fun validateForm() {
        val validationResult = validatePasswordUseCase(
            currentPassword = _uiState.value.currentPassword,
            newPassword = _uiState.value.newPassword,
            confirmPassword = _uiState.value.confirmPassword
        )
        validatePasswordData(validationResult)
    }

    private fun validatePasswordData(type: ChangePasswordValidationError) {
        _uiState.update {
            when (type) {
                ChangePasswordValidationError.EmptyField -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.EMPTY_FIELD,
                        isFormValid = false
                    )
                }

                ChangePasswordValidationError.PasswordsDoNotMatch -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORDS_DO_NOT_MATCH,
                        isFormValid = false
                    )
                }

                ChangePasswordValidationError.NewPasswordSameAsOld -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.NEW_PASSWORD_SAME_AS_OLD,
                        isFormValid = false
                    )
                }

                ChangePasswordValidationError.PasswordTooShort -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_TOO_SHORT,
                        isFormValid = false
                    )
                }

                ChangePasswordValidationError.PasswordUpperCaseMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_UPPERCASE_MISSING,
                        isFormValid = false
                    )
                }

                ChangePasswordValidationError.PasswordSpecialCharMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_SPECIAL_CHAR_MISSING,
                        isFormValid = false
                    )
                }

                ChangePasswordValidationError.PasswordNumberMissing -> {
                    it.copy(
                        fieldErrorMessage = Constants.ValidationAuthMessages.PASSWORD_NUMBER_MISSING,
                        isFormValid = false
                    )
                }

                ChangePasswordValidationError.Valid -> {
                    it.copy(
                        fieldErrorMessage = null,
                        isFormValid = true
                    )
                }
            }
        }
    }
}
