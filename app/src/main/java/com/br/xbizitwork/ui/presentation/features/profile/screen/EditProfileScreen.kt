package com.br.xbizitwork.ui.presentation.features.profile.screen

import android.app.Activity
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.br.xbizitwork.core.sideeffects.SideEffect
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.profile.components.EditProfileContent
import com.br.xbizitwork.ui.presentation.features.profile.events.EditProfileEvent
import com.br.xbizitwork.ui.presentation.features.profile.viewmodel.EditProfileViewModel
import com.br.xbizitwork.ui.theme.DarkHeaderBackground
import kotlinx.coroutines.flow.collectLatest

/**
 * Tela de Edição de Perfil do Usuário
 * 
 * Responsabilidades:
 * - Exibir formulário de edição de dados cadastrais
 * - Gerenciar status bar color e appearance
 * - Conectar eventos da UI ao ViewModel
 * - Processar side effects (Toast, navegação)
 * - Permitir voltar à tela anterior
 * - Navegar ao login se token expirar (401)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onNavigateToLogin: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val view = LocalView.current

    // Configurar status bar para background escuro e ícones claros
    SideEffect {
        val activity = (context as? Activity) ?: return@SideEffect
        activity.window.statusBarColor = DarkHeaderBackground.toArgb()
        WindowCompat.getInsetsController(activity.window, view)
            ?.isAppearanceLightStatusBars = false
    }

    // Processar side effects (Toast, Navegação, etc)
    LaunchedEffect(Unit) {
        viewModel.sideEffectChannel.collectLatest { sideEffect ->
            when (sideEffect) {
                is SideEffect.ShowToast -> {
                    Toast.makeText(context, sideEffect.message, Toast.LENGTH_SHORT).show()
                }
                is SideEffect.NavigateToLogin -> {
                    // Token expirado, volta ao login
                    onNavigateToLogin()
                }
                is SideEffect.NavigateBack -> {
                    // Navega de volta para MenuScreen
                    onNavigateBack()
                }
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Editar Perfil",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = { viewModel.onEvent(EditProfileEvent.OnCancelClick) }
            )
        },

//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(
//                        text = "Editar Perfil",
//                        color = Color.White,
//                        style = MaterialTheme.typography.titleMedium
//                    )
//                },
//                navigationIcon = {
//                    IconButton(onClick = { viewModel.onEvent(EditProfileEvent.OnCancelClick) }) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Voltar",
//                            tint = Color.White
//                        )
//                    }
//                },
//                colors = TopAppBarDefaults.topAppBarColors(
//                    containerColor = DarkHeaderBackground,
//                    navigationIconContentColor = Color.White,
//                    titleContentColor = Color.White
//                )
//            )
//        }
    ) { paddingValues ->
        EditProfileContent(
            modifier = Modifier.fillMaxSize(),
            uiState = uiState,
            paddingValues = paddingValues,
            onNameChanged = { viewModel.onEvent(EditProfileEvent.OnNameChanged(it)) },
            onCpfChanged = { viewModel.onEvent(EditProfileEvent.OnCpfChanged(it)) },
            onDateOfBirthChanged = { viewModel.onEvent(EditProfileEvent.OnDateOfBirthChanged(it)) },
            onGenderChanged = { viewModel.onEvent(EditProfileEvent.OnGenderChanged(it)) },
            onEmailChanged = { viewModel.onEvent(EditProfileEvent.OnEmailChanged(it)) },
            onPhoneChanged = { viewModel.onEvent(EditProfileEvent.OnPhoneChanged(it)) },
            onZipCodeChanged = { viewModel.onEvent(EditProfileEvent.OnZipCodeChanged(it)) },
            onZipCodeBlur = { viewModel.onEvent(EditProfileEvent.OnZipCodeBlur) },  // ✅ Busca endereço
            onAddressChanged = { viewModel.onEvent(EditProfileEvent.OnAddressChanged(it)) },
            onNumberChanged = { viewModel.onEvent(EditProfileEvent.OnNumberChanged(it)) },
            onNeighborhoodChanged = { viewModel.onEvent(EditProfileEvent.OnNeighborhoodChanged(it)) },
            onCityChanged = { viewModel.onEvent(EditProfileEvent.OnCityChanged(it)) },
            onStateChanged = { viewModel.onEvent(EditProfileEvent.OnStateChanged(it)) },
            onSaveClick = { viewModel.onEvent(EditProfileEvent.OnSaveClick) },
            onCancelClick = { viewModel.onEvent(EditProfileEvent.OnCancelClick) }
        )
    }
}
