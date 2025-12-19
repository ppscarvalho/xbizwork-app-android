package com.br.xbizitwork.ui.presentation.features.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationCity
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.buttons.AppButton
import com.br.xbizitwork.ui.presentation.components.inputs.AppDateField
import com.br.xbizitwork.ui.presentation.components.inputs.AppDropdownField
import com.br.xbizitwork.ui.presentation.components.inputs.AppTextField
import com.br.xbizitwork.ui.presentation.components.inputs.DropdownOption
import java.time.LocalDate

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
    genderValue: String,

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
    onGenderChanged: (String) -> Unit,
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
        verticalArrangement = Arrangement.spacedBy(15.dp)
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
            textColor = colorScheme.onSurfaceVariant,
            cursorColor = colorScheme.onSurfaceVariant
        )

        // CPF
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "CPF",
            placeholder = "000.000.000-00",
            value = cpfValue,
            onValueChange = onCpfChanged,
            leadingIcon = Icons.Outlined.Person,
            textColor = colorScheme.onSurfaceVariant,
            cursorColor = colorScheme.onSurfaceVariant
        )

        // Data de Nascimento - USA AppDateField!
        AppDateField(
            modifier = Modifier.fillMaxWidth(),
            label = "Data de Nascimento",
            placeholder = "DD/MM/AAAA",
            value = dateOfBirthValue,
            onValueChange = onDateOfBirthChanged,
            textColor = colorScheme.onSurfaceVariant,
            cursorColor = colorScheme.onSurfaceVariant
        )

        // Gênero - DROPDOWN!
        AppDropdownField(
            modifier = Modifier.fillMaxWidth(),
            label = "Gênero",
            placeholder = "Selecione seu gênero",
            selectedValue = genderValue,
            options = listOf(
                DropdownOption("M", "Masculino"),
                DropdownOption("F", "Feminino"),
                DropdownOption("Outro", "Outro"),
                DropdownOption("Prefiro não informar", "Prefiro não informar")
            ),
            onValueChange = onGenderChanged,
            leadingIcon = Icons.Outlined.Person,
            textColor = colorScheme.onSurfaceVariant
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
            textColor = colorScheme.onSurfaceVariant,
            cursorColor = colorScheme.onSurfaceVariant,
            enabled = false  // ✅ Email não pode ser alterado
        )

        // Telefone
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Telefone",
            placeholder = "(11) 98765-4321",
            value = phoneValue,
            onValueChange = onPhoneChanged,
            leadingIcon = Icons.Outlined.Phone,
            textColor = colorScheme.onSurfaceVariant,
            cursorColor = colorScheme.onSurfaceVariant
        )

        // ========== ENDEREÇO ==========

        // CEP
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "CEP",
            placeholder = "00000-000",
            value = zipCodeValue,
            onValueChange = onZipCodeChanged,
            onFocusLost = onZipCodeBlur,  // ✅ Busca endereço quando sai do campo
            leadingIcon = Icons.Outlined.LocationCity,
            textColor = colorScheme.onSurfaceVariant,
            cursorColor = colorScheme.onSurfaceVariant
        )

        // Endereço
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Endereço",
            placeholder = "Rua, Avenida...",
            value = addressValue,
            onValueChange = onAddressChanged,
            leadingIcon = Icons.Outlined.Home,
            textColor = colorScheme.onSurfaceVariant,
            cursorColor = colorScheme.onSurfaceVariant,
            enabled = false  // ✅ Preenchido automaticamente pelo CEP
        )

        // Número
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Número",
            placeholder = "123",
            value = numberValue,
            onValueChange = onNumberChanged,
            leadingIcon = Icons.Outlined.Home,
            textColor = colorScheme.onSurfaceVariant,
            cursorColor = colorScheme.onSurfaceVariant
        )

        // Bairro
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Bairro",
            placeholder = "Centro",
            value = neighborhoodValue,
            onValueChange = onNeighborhoodChanged,
            leadingIcon = Icons.Outlined.LocationCity,
            textColor = colorScheme.onSurfaceVariant,
            cursorColor = colorScheme.onSurfaceVariant,
            enabled = false  // ✅ Preenchido automaticamente pelo CEP
        )

        // Cidade
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Cidade",
            placeholder = "São Paulo",
            value = cityValue,
            onValueChange = onCityChanged,
            leadingIcon = Icons.Outlined.LocationCity,
            textColor = colorScheme.onSurfaceVariant,
            cursorColor = colorScheme.onSurfaceVariant,
            enabled = false  // ✅ Preenchido automaticamente pelo CEP
        )

        // Estado
        AppTextField(
            modifier = Modifier.fillMaxWidth(),
            label = "Estado",
            placeholder = "SP",
            value = stateValue,
            onValueChange = onStateChanged,
            leadingIcon = Icons.Outlined.LocationCity,
            textColor = colorScheme.onSurfaceVariant,
            cursorColor = colorScheme.onSurfaceVariant,
            enabled = false  // ✅ Preenchido automaticamente pelo CEP
        )

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

