package com.br.xbizitwork.ui.presentation.features.profile.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FolderZip
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.components.inputs.AppDateField
import com.br.xbizitwork.ui.presentation.components.inputs.AppDropdownField
import com.br.xbizitwork.ui.presentation.components.inputs.AppTextField
import com.br.xbizitwork.ui.presentation.components.inputs.DropdownOption
import com.br.xbizitwork.ui.presentation.features.profile.state.CepVisualTransformation
import com.br.xbizitwork.ui.presentation.features.profile.state.CpfVisualTransformation
import com.br.xbizitwork.ui.presentation.features.profile.state.PhoneVisualTransformation
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import java.time.LocalDate

private val CPF_ALLOWED_REGEX = Regex("[0-9.-]*")
private val ONLY_NUMBERS_REGEX = Regex("[0-9]*")

/**
 * Container com formulário COMPLETO de edição de perfil
 * ATUALIZADO com TODOS os campos do Prisma
 */
@Composable
fun EditProfileContainer(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    buttonEnabled: Boolean,

    // Dados básicos
    nameValue: String,
    cpfValue: String,
    dateOfBirthValue: LocalDate?,

    // Contato
    emailValue: String,
    phoneValue: String,

    // Endereço
    zipCodeValue: String,
    addressValue: String,
    numberValue: String,
    neighborhoodValue: String,
    cityValue: String,
    stateValue: String,

    // Callbacks
    onNameChanged: (String) -> Unit,
    onCpfChanged: (String) -> Unit,
    onDateOfBirthChanged: (LocalDate?) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPhoneChanged: (String) -> Unit,
    onZipCodeChanged: (String) -> Unit,
    onZipCodeBlur: () -> Unit,  // ✅ NOVO: Busca endereço quando sai do campo
    onAddressChanged: (String) -> Unit,
    onNumberChanged: (String) -> Unit,
    onNeighborhoodChanged: (String) -> Unit,
    onCityChanged: (String) -> Unit,
    onStateChanged: (String) -> Unit,
    onSaveClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // ========== DADOS BÁSICOS ==========

        // Nome completo
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Nome completo",
            placeholder = "Digite seu nome",
            value = nameValue,
            onValueChange = onNameChanged,
            leadingIcon = Icons.Outlined.Person,
            textColor = colorScheme.onPrimary,
            cursorColor = colorScheme.onPrimary
        )
        // CPF
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "CPF",
            placeholder = "000.000.000-00",

            // 1. Passe o valor PURO (apenas números) vindo do ViewModel/State
            value = cpfValue.filter { it.isDigit() },

            onValueChange = { input ->
                // 2. Filtra apenas números e limita a 11 caracteres
                val digits = input.filter { it.isDigit() }.take(11)
                onCpfChanged(digits)
            },

            // 3. Adicione esta linha (seu AppTextField deve repassar isso ao BasicTextField/OutlinedTextField)
            visualTransformation = CpfVisualTransformation(),

            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = Icons.Outlined.Person,
            textColor = colorScheme.onPrimary,
            cursorColor = colorScheme.onPrimary
        )

        // Data de Nascimento - USA AppDateField!
        AppDateField(
            modifier = Modifier.fillMaxWidth(),
            label = "Data de Nascimento",
            placeholder = "DD/MM/AAAA",
            value = dateOfBirthValue,
            onValueChange = onDateOfBirthChanged,
            textColor = colorScheme.onPrimary,
            cursorColor = colorScheme.onPrimary
        )
        // ========== CONTATO ==========

        // E-mail
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Email",
            placeholder = "seu@email.com",
            value = emailValue,
            onValueChange = onEmailChanged,
            leadingIcon = Icons.Outlined.Email,
            textColor = colorScheme.onPrimary,
            cursorColor = colorScheme.onPrimary,
            enabled = false  // ✅ Email não pode ser alterado
        )

        // Telefone
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Telefone",
            placeholder = "(00) 00000-0000",
            // Passa apenas os números para o componente
            value = phoneValue.filter { it.isDigit() },
            onValueChange = { input ->
                val digits = input.filter { it.isDigit() }.take(11)
                onPhoneChanged(digits)
            },
            // Aplica a transformação visual
            visualTransformation = PhoneVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = Icons.Outlined.Phone,
            textColor = colorScheme.onPrimary,
            cursorColor = colorScheme.onPrimary
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            // CEP
            AppTextField(
                modifier = Modifier.weight(2f).padding(end = 2.dp),
                label = "CEP",
                placeholder = "00000-000",
                // Passa apenas os números para o componente
                value = zipCodeValue.filter { it.isDigit() },
                onValueChange = { input ->
                    val digits = input.filter { it.isDigit() }.take(8)
                    onZipCodeChanged(digits)
                },
                // Aplica a transformação visual
                visualTransformation = CepVisualTransformation(),
                onFocusLost = onZipCodeBlur,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                leadingIcon = Icons.Outlined.LocationCity,
                textColor = colorScheme.onPrimary,
                cursorColor = colorScheme.onPrimary
            )

            AppTextField(
                modifier = Modifier.weight(2f),
                label = "Número",
                placeholder = "123",
                value = numberValue,
                onValueChange = onNumberChanged,
                leadingIcon = Icons.Outlined.Home,
                textColor = colorScheme.onPrimary,
                cursorColor = colorScheme.onPrimary
            )
        }

        // ========== ENDEREÇO ==========

        // Endereço
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Endereço",
            placeholder = "Rua, Avenida...",
            value = addressValue,
            onValueChange = onAddressChanged,
            leadingIcon = Icons.Outlined.Home,
            textColor = colorScheme.onPrimary,
            cursorColor = colorScheme.onPrimary,
            enabled = false  // ✅ Preenchido automaticamente pelo CEP
        )

        // Bairro
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Bairro",
            placeholder = "Centro",
            value = neighborhoodValue,
            onValueChange = onNeighborhoodChanged,
            leadingIcon = Icons.Outlined.LocationCity,
            textColor = colorScheme.onPrimary,
            cursorColor = colorScheme.onPrimary,
            enabled = false  // ✅ Preenchido automaticamente pelo CEP
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            // Cidade
            AppTextField(
                modifier = Modifier.weight(4f)
                    .padding(end = 2.dp),
                label = "Cidade",
                placeholder = "São Paulo",
                value = cityValue,
                onValueChange = onCityChanged,
                leadingIcon = Icons.Outlined.LocationCity,
                textColor = colorScheme.onPrimary,
                cursorColor = colorScheme.onPrimary,
                enabled = false  // ✅ Preenchido automaticamente pelo CEP
            )

            // Estado
            AppTextField(
                modifier = Modifier.weight(2f),
                label = "UF",
                placeholder = "SP",
                value = stateValue,
                onValueChange = onStateChanged,
                leadingIcon = Icons.Outlined.LocationCity,
                textColor = colorScheme.onPrimary,
                cursorColor = colorScheme.onPrimary,
                enabled = false  // ✅ Preenchido automaticamente pelo CEP
            )
        }


        // ========== BOTÃO ==========

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            AppButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .shadow(5.dp, RoundedCornerShape(25.dp)),
                text = "Salvar Alterações",
                contentColor = Color.White,
                enabled = buttonEnabled,
                onClick = onSaveClick,
                isLoading = isLoading,
            )
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES,
    backgroundColor = 0xFF0f344e)
@Composable
private fun EditProfileContainerPreview() {
    XBizWorkTheme{
        EditProfileContainer(
            modifier = Modifier.fillMaxWidth(),
            isLoading = false,
            buttonEnabled = true,
            nameValue = "João da Silva",
            cpfValue = "12345678900",  // ✅ Apenas dígitos - máscara aplicada automaticamente
            dateOfBirthValue = null,
            emailValue = "joao@gmail.com",
            phoneValue = "11987654321",  // ✅ Apenas dígitos - máscara aplicada automaticamente
            zipCodeValue = "12345678",  // ✅ Apenas dígitos - máscara aplicada automaticamente
            addressValue = "Av. Paulista",
            numberValue = "1000",
            neighborhoodValue = "Bela Vista",
            cityValue = "São Paulo",
            stateValue = "SP",
            onNameChanged = {},
            onCpfChanged = {},
            onDateOfBirthChanged = {},
            onEmailChanged = {},
            onPhoneChanged = {},
            onZipCodeChanged = {},
            onZipCodeBlur = {},
            onAddressChanged = {},
            onNumberChanged = {},
            onNeighborhoodChanged = {},
            onCityChanged = {},
            onStateChanged = {},
            onSaveClick = {}
        )
    }
}