# 📋 PLANO DE AÇÃO - Ajustes de Comportamento na Home - 05/02/2026

## 📌 Contexto

A HomeScreen atualmente possui dois comportamentos que precisam ser ajustados:

1. **Card "Divulgue seu trabalho"** → Atualmente chama login, deve navegar para Planos
2. **Ícone superior direito** → Atualmente faz logout, deve chamar login

---

## 🎯 Objetivos

### ✅ 1. Card "Divulgue seu trabalho" → "Planos de Assinatura"

**Estado Atual:**
- Card com ícone de compartilhar (Share)
- Texto: "Divulgue / seu trabalho"
- Ação: `onClick = { onNavigationToSignInScreen() }`

**Estado Desejado:**
- Card com ícone de planos/estrela
- Texto: "Planos de / Assinatura"
- Ação: Navegar para `PlanScreen`
- **Importante:** A tela de planos já está preparada para carregar planos públicos via endpoint `plans/public`

---

### ✅ 2. Ícone Superior Direito → Ponto de Entrada do Login

**Estado Atual:**
- Ícone: `Icons.Outlined.Person` (pessoa)
- Ação: `onRightIconClick = {onLogout()}`
- Comportamento: Desloga o usuário

**Estado Desejado:**
- Ícone: Mantém `Icons.Outlined.Person`
- Ação: Verificar se usuário está logado
  - ❌ **Se deslogado:** Navegar para tela de login (`SignInScreen`)
  - ✅ **Se logado:** Navegar para tela de perfil (`ProfileScreen`)

---

## 🔐 Lógica de Seleção de Planos de Assinatura

Precisamos definir o comportamento do sistema quando o usuário clicar em um plano de assinatura, considerando os cenários abaixo.

### 1️⃣ Usuário Não Logado

Ao clicar em um plano, o sistema deve exibir um **pop-up** com a seguinte mensagem:

```
Você já possui um cadastro?
 >> Se sim, clique em Fazer login
 >> Se não, clique em Cadastrar-se
```

**Comportamento:**
- ✅ **Opção "Fazer login"** → Direciona para `SignInScreen`
- ✅ **Opção "Cadastrar-se"** → Direciona para `SignUpScreen`

**Implementação:**
- Criar `AlertDialog` ou `BottomSheet` customizado
- Dois botões de ação: "Fazer login" e "Cadastrar-se"
- Após login/cadastro bem-sucedido, usuário retorna para tela de planos

---

### 2️⃣ Usuário Logado

Ao clicar em um plano, o sistema deve:

1. ✅ Verificar se o usuário está autenticado
2. ✅ Chamar endpoint para verificar se o usuário já possui um plano de assinatura

#### 2.1 Usuário já possui um plano

**Comportamento:**
- ✅ O **plano atual do usuário** deve ser **destacado visualmente** (conforme já ocorre hoje)
- ✅ O usuário **poderá trocar de plano**:
  - Ao selecionar um plano diferente, a assinatura existente deve ser **atualizada** para o novo plano
  - Endpoint: `PUT /api/v1/user-plans` ou similar (confirmar com backend)

**Validações:**
- ⚠️ Não permitir assinar o mesmo plano que já possui (botão desabilitado)
- ✅ Exibir confirmação antes de trocar de plano:
  ```
  Deseja realmente trocar seu plano atual 
  [Plano Básico] para [Plano Premium]?
  ```

#### 2.2 Usuário logado, mas sem plano

**Comportamento:**
- ✅ O sistema deve permitir que o usuário **assine o plano selecionado normalmente**
- ✅ Chamada do endpoint: `POST /api/v1/user-plans`
- ✅ Exibir mensagem de sucesso após assinatura

---

### 🎯 Fluxograma de Decisão

```
Usuário clica em um plano
         ↓
    Está logado?
    ╱          ╲
  NÃO          SIM
   ↓            ↓
Pop-up:     Tem plano ativo?
"Tem conta?"   ╱         ╲
  ↓          NÃO          SIM
Login ou      ↓            ↓
Cadastro   Assinar    É o plano atual?
            plano       ╱         ╲
              ↓       SIM         NÃO
           Sucesso  Botão         ↓
                   desabilitado Confirmar troca
                                   ↓
                                Atualizar plano
```

---

### 📋 Regras de Negócio - Resumo

| Cenário | Condição | Ação |
|---------|----------|------|
| **1** | Usuário deslogado | Exibir pop-up → Login ou Cadastro |
| **2** | Usuário logado + sem plano | Permitir assinatura do plano |
| **3** | Usuário logado + plano ativo | Destacar plano atual |
| **4** | Usuário logado + quer trocar plano | Confirmar troca → Atualizar assinatura |
| **5** | Usuário clica no plano que já possui | Botão desabilitado ou mensagem informativa |

---

## 📂 Arquivos Afetados

### 1. **HomeScreen.kt** ✏️
- **Localização:** `app/src/main/java/com/br/xbizitwork/ui/presentation/features/home/screen/HomeScreen.kt`
- **Mudanças:**
  - ❌ Remover parâmetro `onLogout: () -> Unit`
  - ✅ Adicionar parâmetro `onNavigateToPlansScreen: () -> Unit`
  - ✅ Alterar lógica do `AppTopBar.onRightIconClick`:
    ```kotlin
    onRightIconClick = {
        if (uiState.userName.isNullOrEmpty()) {
            onNavigateToSignInScreen()  // Deslogado → Login
        } else {
            onNavigateToProfileScreen()  // Logado → Perfil
        }
    }
    ```

---

### 2. **HomeContent.kt** ✏️
- **Localização:** `app/src/main/java/com/br/xbizitwork/ui/presentation/features/home/components/HomeContent.kt`
- **Mudanças:**
  - ❌ Remover parâmetro `onNavigationToSignInScreen: () -> Unit`
  - ✅ Adicionar parâmetro `onNavigateToPlansScreen: () -> Unit`
  - ✅ Passar novo callback para `HomeContainer`

---

### 3. **HomeContainer.kt** ✏️
- **Localização:** `app/src/main/java/com/br/xbizitwork/ui/presentation/features/home/components/HomeContainer.kt`
- **Mudanças:**
  - ❌ Remover parâmetro `onNavigationToSignInScreen: () -> Unit`
  - ✅ Adicionar parâmetro `onNavigateToPlansScreen: () -> Unit`
  - ✅ Atualizar chamada de `PromotionalContainer`:
    ```kotlin
    PromotionalContainer(
        modifier = Modifier.fillMaxWidth(),
        onNavigateToPlansScreen = onNavigateToPlansScreen  // ✅ NOVO
    )
    ```

---

### 4. **PromotionalContainer.kt** ✏️
- **Localização:** `app/src/main/java/com/br/xbizitwork/ui/presentation/components/cards/PromotionalContainer.kt`
- **Mudanças:**
  - ❌ Remover parâmetro `onNavigationToSignInScreen: () -> Unit`
  - ✅ Adicionar parâmetro `onNavigateToPlansScreen: () -> Unit`
  - ✅ Atualizar chamada de `CardContainer`:
    ```kotlin
    CardContainer(
        modifier = Modifier.fillMaxWidth(),
        onNavigateToPlansScreen = onNavigateToPlansScreen  // ✅ NOVO
    )
    ```

---

### 5. **CardContainer.kt** ✏️
- **Localização:** `app/src/main/java/com/br/xbizitwork/ui/presentation/components/cards/CardContainer.kt`
- **Mudanças:**
  - ❌ Remover parâmetro `onNavigationToSignInScreen: () -> Unit`
  - ✅ Adicionar parâmetro `onNavigateToPlansScreen: () -> Unit`
  - ✅ Atualizar primeiro `ShortcutCard`:
    ```kotlin
    ShortcutCard(
        icon = Icons.Default.Subscriptions,  // ✅ NOVO ícone
        title = stringResource(id = R.string.planos_text),  // ✅ NOVO texto
        subtitle = stringResource(id = R.string.assinatura_text),  // ✅ NOVO texto
        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
        onClick = { onNavigateToPlansScreen() },  // ✅ NOVA ação
        modifier = Modifier.weight(1f)
    )
    ```

---

### 6. **strings.xml** ✏️
- **Localização:** `app/src/main/res/values/strings.xml`
- **Mudanças:**
  - ✅ Adicionar novos textos:
    ```xml
    <string name="planos_text">Planos de</string>
    <string name="assinatura_text">Assinatura</string>
    ```

---

### 7. **HomeGraph.kt** ✏️
- **Localização:** `app/src/main/java/com/br/xbizitwork/ui/presentation/navigation/graphs/HomeGraph.kt`
- **Mudanças:**
  - ✅ Adicionar callback `onNavigateToPlansScreen` ao `homeScreen` composable
  - ✅ Implementar navegação:
    ```kotlin
    fun NavGraphBuilder.homeScreen(
        navController: NavController,
        onNavigateToPlansScreen: () -> Unit  // ✅ NOVO
    ) {
        composable<HomeScreens.HomeScreen> {
            DefaultScreen(
                // ...
                onNavigateToPlansScreen = onNavigateToPlansScreen,
                // ...
            )
        }
    }
    ```

---

### 8. **HomeNavigation.kt** ✏️
- **Localização:** `app/src/main/java/com/br/xbizitwork/ui/presentation/navigation/HomeNavigation.kt`
- **Mudanças:**
  - ✅ Conectar `homeScreen` com `navigateToPlanScreen()`:
    ```kotlin
    homeScreen(
        navController = navController,
        onNavigateToPlansScreen = { navController.navigateToPlanScreen() }
    )
    ```

---

## 🔄 Fluxo de Dados Atualizado

### Antes:
```
HomeGraph → DefaultScreen → HomeContent → HomeContainer → PromotionalContainer → CardContainer
                                  ↓                                                       ↓
                         onNavigationToSignInScreen()                     onNavigationToSignInScreen()
```

### Depois:
```
HomeGraph → DefaultScreen → HomeContent → HomeContainer → PromotionalContainer → CardContainer
                  ↓                                                                        ↓
      onNavigateToPlansScreen()                                            onNavigateToPlansScreen()
                  ↓
      navigateToPlanScreen()
```

---

## ⚙️ Lógica do Ícone Superior

```kotlin
// Em DefaultScreen (HomeScreen.kt)
AppTopBar(
    username = if (uiState.userName.isNullOrEmpty()) "Usuário" else uiState.userName,
    onRightIconClick = {
        if (uiState.userName.isNullOrEmpty()) {
            onNavigateToSignInScreen()  // 🔓 Deslogado → Login
        } else {
            onNavigateToProfileScreen()  // 👤 Logado → Perfil
        }
    }
)
```

**Regra de Negócio:**
- ✅ `userName` vazio ou null → Usuário deslogado → Abre Login
- ✅ `userName` preenchido → Usuário logado → Abre Perfil

---

## 🎨 Alterações Visuais

### Card "Planos de Assinatura"

**Antes:**
- 📤 Ícone: `Icons.Default.Share`
- 📝 Texto: "Divulgue / seu trabalho"

**Depois:**
- ⭐ Ícone: `Icons.Default.Subscriptions` (ou `Icons.Default.CardMembership`)
- 📝 Texto: "Planos de / Assinatura"

---

## ✅ Checklist de Implementação

### Fase 1: Preparação (5 min)
- [ ] Adicionar strings `planos_text` e `assinatura_text` em `strings.xml`
- [ ] Adicionar strings para pop-up: `possui_cadastro_text`, `fazer_login_text`, `cadastrar_se_text`
- [ ] Verificar se `Icons.Default.Subscriptions` está disponível (ou usar alternativa)

### Fase 2: Atualizar Cards (15 min)
- [ ] Modificar `CardContainer.kt` → Trocar ícone, texto e ação
- [ ] Modificar `PromotionalContainer.kt` → Atualizar assinatura de parâmetros
- [ ] Modificar `HomeContainer.kt` → Passar novo callback
- [ ] Modificar `HomeContent.kt` → Passar novo callback

### Fase 3: Atualizar HomeScreen (10 min)
- [ ] Modificar `HomeScreen.kt` → Remover `onLogout`, adicionar `onNavigateToPlansScreen`
- [ ] Implementar lógica condicional no `AppTopBar.onRightIconClick`
- [ ] Atualizar Preview

### Fase 4: Atualizar Navegação (10 min)
- [ ] Modificar `HomeGraph.kt` → Adicionar parâmetro `onNavigateToPlansScreen`
- [ ] Modificar `HomeNavigation.kt` → Conectar com `navigateToPlanScreen()`

### Fase 5: Implementar Lógica de Seleção de Planos (20 min)
- [ ] Criar componente `PlanSelectionDialog.kt` (pop-up login/cadastro)
- [ ] Atualizar `PlanScreen.kt` para verificar autenticação antes de assinar
- [ ] Implementar lógica de exibição do pop-up para usuários não logados
- [ ] Adicionar validação para não permitir assinar o mesmo plano já ativo
- [ ] Implementar confirmação de troca de plano (se aplicável)

### Fase 6: Testes (15 min)
- [ ] Compilar o projeto sem erros
- [ ] Testar navegação: Home → Planos
- [ ] Testar ícone superior (deslogado) → Login
- [ ] Testar ícone superior (logado) → Perfil
- [ ] Testar seleção de plano (usuário deslogado) → Pop-up → Login/Cadastro
- [ ] Testar seleção de plano (usuário logado sem plano) → Assinatura normal
- [ ] Testar seleção de plano (usuário logado com plano) → Destaque visual + Troca

---

## 🧪 Cenários de Teste

### Teste 1: Card "Planos de Assinatura"
1. ✅ Abrir HomeScreen
2. ✅ Verificar texto do card: "Planos de / Assinatura"
3. ✅ Verificar ícone: Subscriptions/CardMembership
4. ✅ Clicar no card
5. ✅ Verificar navegação para `PlanScreen`
6. ✅ Verificar carregamento de planos via endpoint `plans/public`

### Teste 2: Ícone Superior (Deslogado)
1. ✅ Garantir que `userName` está vazio/null
2. ✅ Clicar no ícone superior direito
3. ✅ Verificar navegação para `SignInScreen`

### Teste 3: Ícone Superior (Logado)
1. ✅ Fazer login (userName preenchido)
2. ✅ Voltar para HomeScreen
3. ✅ Clicar no ícone superior direito
4. ✅ Verificar navegação para `ProfileScreen`

---

## 📊 Estimativa de Tempo

| Fase | Descrição | Tempo |
|------|-----------|-------|
| 1 | Preparação (strings, imports) | 5 min |
| 2 | Atualizar Cards e Containers | 15 min |
| 3 | Atualizar HomeScreen | 10 min |
| 4 | Atualizar Navegação | 10 min |
| 5 | Implementar Lógica de Seleção de Planos | 20 min |
| 6 | Testes e Validação Completa | 15 min |
| **TOTAL** | | **75 min** |

---

## 🚨 Pontos de Atenção

1. ✅ **Ícone `Subscriptions` não disponível?**
   - Alternativas: `Icons.Default.CardMembership`, `Icons.Default.Star`, `Icons.Default.Payments`

2. ✅ **Verificar se `navigateToPlanScreen()` existe**
   - ✅ Confirmado em `PlanNavigation.kt`

3. ✅ **HomeViewModel não precisa de alteração**
   - A lógica de navegação é toda via callbacks

4. ✅ **Não alterar BottomBar**
   - A navegação para planos agora é pelo card, não pelo menu inferior

5. ✅ **Endpoint `plans/public` já está implementado**
   - `GetPublicPlansUseCase` já carrega planos sem autenticação

---

## 📝 Observações Finais

- ❌ **NÃO** atualizar bibliotecas
- ❌ **NÃO** criar novos componentes
- ✅ **SEGUIR** o padrão de navegação existente
- ✅ **REUTILIZAR** `PlanScreen` existente
- ✅ **TESTAR** compilação após cada fase

---

## 🎯 Resultado Esperado

### Card "Planos de Assinatura"
```
┌─────────────────────────────┐
│     ⭐ Subscriptions Icon   │
│                             │
│      Planos de              │
│      Assinatura             │
│                             │
│  onClick → PlanScreen       │
└─────────────────────────────┘
```

### Ícone Superior
```
👤 Ícone Pessoa
   │
   ├─ Se deslogado → SignInScreen
   └─ Se logado → ProfileScreen
```

---

**Status:** 📋 Aguardando Revisão e Aprovação
**Data:** 05/02/2026
**Versão:** 2.0 - Incluída lógica de seleção de planos com cenários de usuário logado/não logado
