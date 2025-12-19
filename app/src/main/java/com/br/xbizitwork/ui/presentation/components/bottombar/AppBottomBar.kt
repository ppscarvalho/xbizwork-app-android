package com.br.xbizitwork.ui.presentation.components.bottombar

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.xbizitwork.ui.theme.XBizWorkTheme
import androidx.compose.foundation.layout.navigationBarsPadding

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    isLoggedIn: Boolean = false,
    onNavigationToProfileScreen: () -> Unit,
    onNavigationToSearchScreen: () -> Unit,
    onNavigationToUsersConnectionScreen: () -> Unit,
    onNavigationToMenuScreen: () -> Unit,
) {
    BottomAppBar(
        modifier = modifier
            // Adiciona padding para respeitar a inset da navigation bar do sistema
            .navigationBarsPadding(),
        containerColor = MaterialTheme.colorScheme.secondary,
        actions = {
            // Row com espaçamento uniforme para distribuir ícones
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Conexões (Pessoas)
                BottomBarItem(
                    icon = Icons.Outlined.PeopleOutline,
                    label = "Conexões",
                    onClick = onNavigationToUsersConnectionScreen
                )

                // Pesquisar
                BottomBarItem(
                    icon = Icons.Outlined.Search,
                    label = "Pesquisar",
                    onClick = onNavigationToSearchScreen
                )

                // Perfil
                BottomBarItem(
                    icon = Icons.Outlined.Person,
                    label = "Perfil",
                    onClick = onNavigationToProfileScreen
                )

                // Menu - Somente exibir se usuário estiver logado
                if (isLoggedIn) {
                    BottomBarItem(
                        icon = Icons.Outlined.Menu,
                        label = "Menu",
                        onClick = onNavigationToMenuScreen
                    )
                }
            }
        }
    )
}

@Composable
private fun BottomBarItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .size(24.dp)
                .padding(bottom = 4.dp)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            fontFamily = MaterialTheme.typography.bodySmall.fontFamily
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun BottomBarPreview() {
    XBizWorkTheme {
        AppBottomBar(
            onNavigationToProfileScreen = {},
            onNavigationToSearchScreen = {},
            onNavigationToUsersConnectionScreen = {},
            onNavigationToMenuScreen = {}
        )
    }
}