# 🗺️ Guia Completo de Navegação com `popUpTo()`

## 📌 Índice
1. [O que é `popUpTo()`?](#o-que-é-popupto)
2. [Pilha de Navegação (Back Stack)](#pilha-de-navegação-back-stack)
3. [Quando Usar `popUpTo(0)`](#quando-usar-popupto0)
4. [Quando NÃO Usar `popUpTo()`](#quando-não-usar-popupto)
5. [Seu Caso: MenuScreen com Submenus](#seu-caso-menuscreen-com-submenus)
6. [Exemplos Práticos](#exemplos-práticos)
7. [Referência Rápida](#referência-rápida)

---

## O que é `popUpTo()`?

`popUpTo()` é um comando que **remove screens da pilha de navegação** antes de navegar para uma nova screen.

### Sintaxe:
```kotlin
navigate(DestinationScreen) {
    popUpTo(ReferenceScreen) {
        saveState = true      // Salva o estado
        inclusive = false     // Inclui ou não a reference screen
    }
    restoreState = true       // Restaura o estado salvo
    launchSingleTop = true    // Evita duplicatas
}
```

---

## Pilha de Navegação (Back Stack)

### Como funciona a pilha:

```
┌─────────────────────────────────────────┐
│  BACK STACK (Pilha de Navegação)        │
├─────────────────────────────────────────┤
│ [3] ← TOPO (Tela atual)                 │
│ [2]                                     │
│ [1]                                     │
│ [0] (ROOT - Tela inicial)               │
└─────────────────────────────────────────┘
```

Quando você pressiona "voltar" (back button), o Android vai do topo [3] para [2], depois [1], e por fim [0].

### Exemplo com seu app:

```
CENÁRIO SEM popUpTo():
┌─────────────────────────┐
│ [3] FinancialScreen     │  ← Atual (voltar vai para MenuScreen)
│ [2] MenuScreen          │
│ [1] HomeScreen          │
│ [0] AuthGraph (ROOT)    │
└─────────────────────────┘

CENÁRIO COM popUpTo(0) INCLUSIVE:
┌─────────────────────────┐
│ [0] FinancialScreen     │  ← Atual (voltar sai do app!)
└─────────────────────────┘
```

---

## Quando Usar `popUpTo(0)`

### ✅ Use `popUpTo(0)` **APENAS** nestes casos:

#### 1. **Logout / Desautenticação**
```kotlin
fun NavController.navigateToSignInScreen() {
    navigate(Graphs.AuthGraph) {
        popUpTo(0) {
            inclusive = true  // Remove TUDO, incluindo a posição 0
        }
        launchSingleTop = true
    }
}
```

**Razão:** Usuário logout não deve conseguir voltar com back para screens autenticadas.

#### 2. **Fluxo Concluído (Checkout, Confirmação, etc)**
```kotlin
fun NavController.navigateToOrderSuccessScreen() {
    navigate(OrderScreens.SuccessScreen) {
        popUpTo<OrderScreens.CartScreen> {
            inclusive = true
        }
    }
}
```

**Razão:** Após completar um pedido, usuário não deve voltar para o carrinho.

#### 3. **Reset Completo da Navegação**
```kotlin
fun NavController.resetToHome() {
    navigate(Graphs.HomeGraphs) {
        popUpTo(0) {
            inclusive = true
        }
    }
}
```

**Razão:** Limpa toda a pilha e recomeça do zero.

---

## Quando NÃO Usar `popUpTo()`

### ❌ NÃO use `popUpTo()` quando:

#### 1. **Navegação Normal Entre Screens (Submenus)**
```kotlin
// ❌ ERRADO
fun NavController.navigateToFinancialScreen() {
    navigate(MenuScreens.FinancialScreen) {
        popUpTo(0)  // ← Problema!
    }
}

// ✅ CORRETO
fun NavController.navigateToFinancialScreen() {
    navigate(MenuScreens.FinancialScreen) {
        launchSingleTop = true
    }
}
```

#### 2. **Volta com Back Button**
O back button é automático! Não precisa fazer nada:

```kotlin
// ✅ AUTOMÁTICO - Back volta para MenuScreen
FinancialScreen(
    onNavigateUp = { navController.navigateUp() }
)
```

#### 3. **Preservar Estado de Screens Anteriores**
```kotlin
// ❌ ERRADO - Perde o estado de MenuScreen
navigate(MenuScreens.FinancialScreen) {
    popUpTo<MenuScreens.MenuScreen> {
        saveState = false
    }
}

// ✅ CORRETO - Preserva o estado
navigate(MenuScreens.FinancialScreen) {
    launchSingleTop = true
}
```

---

## Seu Caso: MenuScreen com Submenus

### 📋 Estrutura que você quer:

```
HomeScreen (BottomBar)
    ↓ (clica no botão Menu)
MenuScreen (BottomBar ainda visível)
    ├─ Botão "Financeiro" → FinancialScreen (sem BottomBar)
    ├─ Botão "Criar Agenda" → CreateScheduleScreen (sem BottomBar)
    ├─ Botão "Visualizar Agendamentos" → ViewSchedulesScreen (sem BottomBar)
    └─ Botão "Voltar" (AppTopBar) → volta para HomeScreen
```

### 🎯 Fluxo de Navegação Correto:

```
PILHA DURANTE A NAVEGAÇÃO:

1. Usuário está em HomeScreen
   [1] HomeScreen ← TOPO

2. Usuário clica "Menu" (BottomBar)
   [2] MenuScreen ← TOPO
   [1] HomeScreen

3. Usuário clica "Financeiro"
   [3] FinancialScreen ← TOPO
   [2] MenuScreen
   [1] HomeScreen

4. Usuário clica "Voltar" em FinancialScreen
   [2] MenuScreen ← TOPO (volta automática com back)
   [1] HomeScreen

5. Usuário clica "Voltar" em MenuScreen (AppTopBar)
   [1] HomeScreen ← TOPO
```

### ✅ Implementação Correta:

#### **1. MenuNavigation.kt** (já corrigido)
```kotlin
// Navegação DE HomeScreen PARA MenuScreen
fun NavController.navigateToMenuScreen(){
    navigate(HomeScreens.MenuScreen){
        popUpTo<HomeScreens.HomeScreen> {
            saveState = true  // ← Importante: salva HomeScreen
        }
        restoreState = true   // ← Restaura quando voltar
        launchSingleTop = true
    }
}
```

**Por quê `saveState`?**
- Quando usuário volta para HomeScreen, ele não recomeça do zero
- Scroll position, dados carregados, etc. são preservados

#### **2. Novo arquivo: FinancialNavigation.kt**
```kotlin
package com.br.xbizitwork.ui.presentation.features.menu.financial.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.menu.financial.screen.FinancialScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

// ✅ SEM popUpTo() - deixa a navegação natural funcionar
fun NavController.navigateToFinancialScreen(){
    navigate(MenuScreens.FinancialScreen){
        launchSingleTop = true  // Evita duplicatas se clicar 2x
    }
}

fun NavGraphBuilder.financialScreen(
    onNavigateUp: () -> Unit
){
    composable<MenuScreens.FinancialScreen> {
        FinancialScreen(
            onNavigateUp = onNavigateUp
        )
    }
}
```

#### **3. Novo arquivo: CreateScheduleNavigation.kt**
```kotlin
package com.br.xbizitwork.ui.presentation.features.menu.createschedule.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.menu.createschedule.screen.CreateScheduleScreen
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

// ✅ SEM popUpTo() - deixa a navegação natural funcionar
fun NavController.navigateToCreateScheduleScreen(){
    navigate(MenuScreens.CreateScheduleScreen){
        launchSingleTop = true
    }
}

fun NavGraphBuilder.createScheduleScreen(
    onNavigateUp: () -> Unit
){
    composable<MenuScreens.CreateScheduleScreen> {
        CreateScheduleScreen(
            onNavigateUp = onNavigateUp
        )
    }
}
```

#### **4. MenuScreen.kt (com botões)**
```kotlin
@Composable
fun MenuScreen(
    onNavigateToHomeGraph: () -> Unit,
    onNavigateToFinancial: () -> Unit,
    onNavigateToCreateSchedule: () -> Unit,
    onNavigateToViewSchedules: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Menu",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = { onNavigateToHomeGraph() }  // ← Volta para Home
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { onNavigateToFinancial() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("💰 Financeiro")
                }

                Button(
                    onClick = { onNavigateToCreateSchedule() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("📅 Criar Agenda")
                }

                Button(
                    onClick = { onNavigateToViewSchedules() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("📋 Visualizar Agendamentos")
                }
            }
        }
    )
}
```

#### **5. HomeGraph.kt (registrar tudo)**
```kotlin
fun NavGraphBuilder.homeGraph(
    onNavigateUp: () -> Unit,
    onNavigateToSignInScreen: () -> Unit,
    onNavigateToProfileScreen: () -> Unit,
    onNavigateToSearchScreen: () -> Unit,
    onNavigateToUsersConnectionScreen: () -> Unit,
    onNavigateToMenuScreen: () -> Unit,
    onNavigateProfileClick: () -> Unit
){
    navigation<Graphs.HomeGraphs>(startDestination = HomeScreens.HomeScreen) {
        homeScreen(
            onNavigateToSignInScreen = onNavigateToSignInScreen,
            onNavigateToProfileScreen = onNavigateToProfileScreen,
            onNavigateToSearchScreen = onNavigateToSearchScreen,
            onNavigateToUsersConnectionScreen = onNavigateToUsersConnectionScreen,
            onNavigateToMenuScreen = onNavigateToMenuScreen,
            onNavigateProfileClick = onNavigateProfileClick
        )
        
        profileScreen(
            onNavigateToHomeGraph = onNavigateUp
        )
        
        menuScreen(
            onNavigateToHomeGraph = onNavigateUp,
            onNavigateToFinancial = { /* será passado do RootHost */ },
            onNavigateToCreateSchedule = { /* será passado do RootHost */ },
            onNavigateToViewSchedules = { /* será passado do RootHost */ }
        )
        
        // ✅ NOVO: Registrar screens do menu
        financialScreen(onNavigateUp = onNavigateUp)
        createScheduleScreen(onNavigateUp = onNavigateUp)
        viewSchedulesScreen(onNavigateUp = onNavigateUp)
    }
}
```

---

## Exemplos Práticos

### Exemplo 1: E-commerce App
```
Fluxo: HomeScreen → ProductListScreen → ProductDetailScreen → CartScreen → CheckoutScreen → SuccessScreen

✅ Correto:
- Home → List: sem popUpTo
- List → Detail: sem popUpTo
- Detail → Cart: sem popUpTo
- Cart → Checkout: sem popUpTo
- Checkout → Success: COM popUpTo<CartScreen> (não volta para cart)
- Success → ??? : sem voltar button, só "Continuar Comprando"

Pilha final em Success:
[3] SuccessScreen ← TOPO
[2] CheckoutScreen
[1] HomeScreen
```

### Exemplo 2: Seu App (MenuScreen)
```
Fluxo: HomeScreen → MenuScreen → FinancialScreen → (voltar)

✅ Correto:
- Home → Menu: COM popUpTo<HomeScreen> (saveState=true)
  Razão: Menu é uma screen principal da BottomBar

- Menu → Financial: SEM popUpTo
  Razão: Financial é submenu de Menu, volta natural

- Financial → (back button): Automático via navigateUp()
  Resultado: Volta para MenuScreen

Pilha:
[3] FinancialScreen ← TOPO
[2] MenuScreen
[1] HomeScreen

Clica back em Financial:
[2] MenuScreen ← TOPO
[1] HomeScreen

Clica back em MenuScreen:
[1] HomeScreen ← TOPO
```

### Exemplo 3: Auth App
```
Fluxo: SignInScreen → (clica logout) → SignInScreen (limpa tudo)

❌ Errado (sem popUpTo):
Pilha: [1] SignInScreen
       [0] HomeScreen (ainda na pilha!)
Usuário clica back em SignIn → volta para Home (BUG!)

✅ Correto (com popUpTo):
navigate(AuthGraph) {
    popUpTo(0) {
        inclusive = true  // Remove tudo
    }
}
Pilha: [0] SignInScreen (nova raiz)
```

---

## Referência Rápida

### 📊 Tabela de Decisão

| Situação | Use `popUpTo()`? | Qual? | `saveState` | Motivo |
|----------|-----------------|-------|------------|--------|
| Home → Menu (BottomBar) | ✅ Sim | `<HomeScreen>` | true | Menu é principal, salva Home |
| Menu → Financeiro | ❌ Não | — | — | Submenu, volta natural |
| Financeiro → Menu (back) | ❌ Não | — | — | Back automático |
| Menu → Home (AppTopBar) | ❌ Não | — | — | Back automático |
| Cart → Success (checkout) | ✅ Sim | `<CartScreen>` | false | Não volta para cart |
| Any → Logout | ✅ Sim | `0` inclusive | — | Remove tudo |
| Any → App Restart | ✅ Sim | `0` inclusive | — | Limpa pilha |

### 🎯 Regra de Ouro

```
┌─────────────────────────────────────────────────┐
│ USE popUpTo() quando:                           │
│ • Logout / Desautenticação                      │
│ • Fluxo completado (não deve voltar)            │
│ • Navegando entre screens PRINCIPAIS (BottomBar)|
│                                                 │
│ NÃO use quando:                                 │
│ • Navegação normal entre screens                │
│ • Submenus                                      │
│ • Back button (é automático!)                   │
└─────────────────────────────────────────────────┘
```

---

## Resumo para seu MenuScreen

```kotlin
// ✅ PADRÃO PARA VOCÊ SEGUIR:

// 1. Screen Principal (MenuScreen)
fun NavController.navigateToMenuScreen(){
    navigate(HomeScreens.MenuScreen){
        popUpTo<HomeScreens.HomeScreen> {
            saveState = true
        }
        restoreState = true
        launchSingleTop = true
    }
}

// 2. Submenus (Financeiro, Criar Agenda, etc)
fun NavController.navigateToFinancialScreen(){
    navigate(MenuScreens.FinancialScreen){
        launchSingleTop = true  // ← Apenas isso!
    }
}

// 3. Voltar é automático!
FinancialScreen(
    onNavigateUp = { navController.navigateUp() }
)
```

**Resultado:**
- ✅ Financial clica voltar → MenuScreen
- ✅ MenuScreen clica voltar → HomeScreen  
- ✅ HomeScreen mantém seu estado (scroll, dados)

---

## Links Úteis

- [Android Navigation Docs](https://developer.android.com/guide/navigation)
- [Jetpack Compose Navigation](https://developer.android.com/jetpack/compose/navigation)
- [Back Stack Management](https://developer.android.com/guide/navigation/navigate)
