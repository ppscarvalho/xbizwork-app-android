package com.br.xbizitwork.ui.presentation.features.auth.bottomsheet.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.presentation.features.auth.bottomsheet.events.AuthBottomSheetEvent
import com.br.xbizitwork.ui.presentation.features.auth.bottomsheet.state.AuthBottomSheetState
import com.br.xbizitwork.ui.theme.poppinsFontFamily

/**
 * Content do BottomSheet de autenticação
 * Segue o padrão do SignInContent
 */
@Composable
fun AuthBottomSheetContent(
    modifier: Modifier = Modifier,
    uiState: AuthBottomSheetState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onEvent: (AuthBottomSheetEvent) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Container com campos
        AuthBottomSheetContainer(
            uiState = uiState,
            onEmailChange = onEmailChange,
            onPasswordChange = onPasswordChange,
            onEvent = onEvent
        )

        // Botão Login
        Button(
            onClick = { onEvent(AuthBottomSheetEvent.OnLoginClick) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            enabled = uiState.isFormValid && !uiState.isLoading
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = Color.White,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Entrar",
                    fontFamily = poppinsFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
