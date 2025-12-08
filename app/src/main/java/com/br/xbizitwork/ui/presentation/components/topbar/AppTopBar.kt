package com.br.xbizitwork.ui.presentation.components.topbar

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.presentation.common.StringAssets
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import com.br.xbizitwork.ui.theme.poppinsFOntFamily

/**
 * AppTopBar flexível com suporte a dois modos:
 * 
 * 1. Modo Home (isHomeMode = true):
 *    - Exibe logo da empresa, saudação e nome do usuário, ícone pessoa
 *    - Layout: Logo | Olá + Nome | Ícone Pessoa
 * 
 * 2. Modo Navegação (isHomeMode = false):
 *    - Exibe título, botão voltar e ações customizadas
 *    - Layout: Botão Voltar | Título | Ações
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    isHomeMode: Boolean = true,
    
    // Parâmetros modo Home
    username: String? = null,
    onRightIconClick: () -> Unit = {},
    companyLogoContent: @Composable () -> Unit = { CompanyLogo() },
    
    // Parâmetros modo Navegação
    title: String = "",
    navigationImageVector: ImageVector? = null,
    actionImageVector: ImageVector? = null,
    contentDescription: String? = null,
    onNavigationIconButton: () -> Unit = {},
    onActionIconButton: () -> Unit = {},
    enableNavigationUp: Boolean = false,
    enableAction: Boolean = false,
) {
    if (isHomeMode) {
        HomeTopBar(
            modifier = modifier,
            username = username,
            onRightIconClick = onRightIconClick,
            companyLogoContent = companyLogoContent
        )
    } else {
        NavigationTopBar(
            modifier = modifier,
            title = title,
            navigationImageVector = navigationImageVector,
            actionImageVector = actionImageVector,
            contentDescription = contentDescription,
            onNavigationIconButton = onNavigationIconButton,
            onActionIconButton = onActionIconButton,
            enableNavigationUp = enableNavigationUp,
            enableAction = enableAction
        )
    }
}

/**
 * TopBar para tela Home
 * Exibe: Logo | Saudação + Nome | Ícone Pessoa
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(
    modifier: Modifier = Modifier,
    username: String? = null,
    onRightIconClick: () -> Unit,
    companyLogoContent: @Composable () -> Unit
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                // Left: Company logo
                Column(modifier = Modifier.padding(bottom = 4.dp)) {
                    companyLogoContent()
                }

                // Center: Greeting + Username
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = StringAssets.WELCOME_TEXT),
                        fontSize = 12.sp,
                        fontFamily = poppinsFOntFamily,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = if (username.isNullOrEmpty()) "Usuário" else username,  // ✅ CORRIGIDO: Trata null E string vazia
                        fontSize = 14.sp,
                        fontFamily = poppinsFOntFamily,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center
                    )
                }

                // Right: Person icon button
                Column(modifier = Modifier.padding(bottom = 4.dp)) {
                    Icon(
                        imageVector = Icons.Outlined.Person,
                        contentDescription = stringResource(id = StringAssets.SETTINGS_TEXT),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f),
                                shape = CircleShape
                            )
                            .clickable(onClick = onRightIconClick)
                            .padding(4.dp)
                    )
                }
            }
        }
    )
}

/**
 * TopBar para telas de navegação
 * Exibe: Botão Voltar | Título | Ações
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NavigationTopBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationImageVector: ImageVector?,
    actionImageVector: ImageVector?,
    contentDescription: String?,
    onNavigationIconButton: () -> Unit,
    onActionIconButton: () -> Unit,
    enableNavigationUp: Boolean,
    enableAction: Boolean
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        title = {
            Text(
                text = title,
                maxLines = 1,
                fontFamily = poppinsFOntFamily,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        navigationIcon = {
            if (navigationImageVector != null) {
                IconButton(
                    onClick = onNavigationIconButton,
                    enabled = enableNavigationUp
                ) {
                    Icon(
                        imageVector = navigationImageVector,
                        contentDescription = contentDescription,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        actions = {
            if (actionImageVector != null) {
                IconButton(
                    onClick = onActionIconButton,
                    enabled = enableAction
                ) {
                    Icon(
                        imageVector = actionImageVector,
                        contentDescription = contentDescription,
                        tint = if (enableAction) MaterialTheme.colorScheme.onPrimary 
                               else Color.Transparent
                    )
                }
            }
        }
    )
}

@Composable
private fun CompanyLogo(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .size(42.dp)
            .clip(CircleShape)
            .background(
                color = Color(0xFFC0DEDD),
                shape = CircleShape
            )
            .padding(4.dp)
    ) {
        Text(
            text = stringResource(id = StringAssets.APP_NAME_XBIZ),
            fontSize = 12.sp,
            fontFamily = poppinsFOntFamily,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFFF6E10),
            textAlign = TextAlign.Center,
            lineHeight = 8.sp
        )
        Text(
            text = stringResource(id = StringAssets.APP_NAME_WORK),
            fontSize = 10.sp,
            fontFamily = poppinsFOntFamily,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black,
            textAlign = TextAlign.Center,
            lineHeight = 8.sp
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppTopBarHomePreview() {
    XBizWorkTheme {
        AppTopBar(
            isHomeMode = true,
            username = "João Silva",
            onRightIconClick = { }
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppTopBarNavigationPreview() {
    XBizWorkTheme {
        AppTopBar(
            isHomeMode = false,
            title = "Login",
            navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            enableNavigationUp = true,
            onNavigationIconButton = { }
        )
    }
}
