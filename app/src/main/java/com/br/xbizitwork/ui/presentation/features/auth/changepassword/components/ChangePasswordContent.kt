package com.br.xbizitwork.ui.presentation.features.auth.changepassword.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.presentation.components.profile.UserAvatar
import com.br.xbizitwork.ui.presentation.features.auth.changepassword.state.ChangePasswordState
import com.br.xbizitwork.ui.theme.BeigeBackground
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFontFamily

/**
 * Conteúdo da tela de alteração de senha
 * Seguindo o mesmo padrão do SignUpContent
 * Componente stateless - apenas renderiza UI
 */
@Composable
fun ChangePasswordContent(
    modifier: Modifier = Modifier,
    uiState: ChangePasswordState,
    paddingValues: PaddingValues,
    onCurrentPasswordChanged: (String) -> Unit,
    onNewPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    onChangePasswordClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(paddingValues)
    ) {
        // Header curvo com fundo bege
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(BeigeBackground)
        ) {
            UserAvatar(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .align(Alignment.Center)
                    .padding(top = 20.dp),
                userName = uiState.results?.name.toString()
            )
        }

        // Conteúdo scrollável
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 180.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Mensagem de erro (se houver)
            if (!uiState.changePasswordErrorMessage.isNullOrEmpty() || !uiState.fieldErrorMessage.isNullOrEmpty()) {
                Text(
                    text = uiState.changePasswordErrorMessage ?: uiState.fieldErrorMessage.orEmpty(),
                    fontFamily = poppinsFontFamily,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFFD32F2F),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Formulário
            ChangePasswordContainer(
                modifier = Modifier.fillMaxWidth(),
                isLoading = uiState.isLoading,
                buttonEnabled = uiState.isFormValid,
                currentPasswordValue = uiState.currentPassword,
                newPasswordValue = uiState.newPassword,
                confirmPasswordValue = uiState.confirmPassword,
                onCurrentPasswordChanged = onCurrentPasswordChanged,
                onNewPasswordChanged = onNewPasswordChanged,
                onConfirmPasswordChanged = onConfirmPasswordChanged,
                onChangePasswordClick = onChangePasswordClick
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
private fun ChangePasswordContentPreview() {
    XBizWorkTheme {
        ChangePasswordContent(
            modifier = Modifier.fillMaxSize(),
            uiState = ChangePasswordState(
                currentPassword = "",
                newPassword = "",
                confirmPassword = ""
            ),
            paddingValues = PaddingValues(),
            onCurrentPasswordChanged = {},
            onNewPasswordChanged = {},
            onConfirmPasswordChanged = {},
            onChangePasswordClick = {}
        )
    }
}
