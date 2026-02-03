# 🎯 PLANO DE AÇÃO - Loading e Autenticação Inline no Mapa

**Data**: 03/02/2026  
**Branch**: `feature/loading-e-auth-inline-mapa`  
**Status**: 📋 Planejamento

---

## 📋 DEMANDAS

### 1. Loading ao Carregar Mapa
**Problema**: Quando o usuário clica em "Ver no Mapa", não há feedback visual, dando impressão de que nada está acontecendo.

**Solução**: Adicionar indicador de loading enquanto o mapa carrega.

### 2. Autenticação Inline
**Problema**: Se o usuário não estiver logado, aparece mensagem e ele precisa navegar para tela de login, perdendo o contexto.

**Solução**: Exibir BottomSheet de login na mesma tela, permitindo autenticação sem sair do fluxo.

---

## 🏗️ ESTRUTURA DE IMPLEMENTAÇÃO

### Arquivos a Criar (1)
- ✅ `AuthBottomSheet.kt` - Componente de autenticação inline

### Arquivos a Modificar (5)
- ✅ `ProfessionalMapScreen.kt` - Adicionar loading
- ✅ `ProfessionalMapViewModel.kt` - Controlar estado de loading
- ✅ `ProfessionalMapUiState.kt` - Campo `isLoadingMap`
- ✅ `SearchProfessionalsScreen.kt` - Integrar AuthBottomSheet
- ✅ `ProfessionalsList.kt` - Validar autenticação antes de navegar

---

## 📝 IMPLEMENTAÇÃO DETALHADA

### FASE 1: Criar Branch ✅
```bash
git checkout develop
git pull origin develop
git checkout -b feature/loading-e-auth-inline-mapa
```

### FASE 2: Implementar Loading

#### 2.1. Atualizar ProfessionalMapUiState
```kotlin
data class ProfessionalMapUiState(
    // ... campos existentes ...
    val isLoadingMap: Boolean = true,  // NOVO
)
```

#### 2.2. Atualizar ProfessionalMapViewModel
```kotlin
fun initializeMap(...) {
    viewModelScope.launch {
        _uiState.update { it.copy(isLoadingMap = true) }
        
        // Lógica de carregamento...
        delay(300) // Mínimo para UX
        
        _uiState.update { 
            it.copy(
                isLoadingMap = false,
                // ... outros campos
            )
        }
    }
}
```

#### 2.3. Atualizar ProfessionalMapScreen
```kotlin
when {
    uiState.isLoading || uiState.isLoadingMap -> {
        LoadingIndicator(message = "Carregando mapa...")
    }
    // ... outros estados
}
```

### FASE 3: Implementar AuthBottomSheet

#### 3.1. Criar Componente AuthBottomSheet
```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthBottomSheet(
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    // Campos: email, password
    // Botão: Entrar
    // Lógica: SignInUseCase
}
```

#### 3.2. Integrar na SearchProfessionalsScreen
```kotlin
var showAuthBottomSheet by remember { mutableStateOf(false) }
var pendingNavigation by remember { mutableStateOf<(() -> Unit)?>(null) }

// Callback do botão "Ver no Mapa"
onMapClick = { professional ->
    if (isAuthenticated) {
        navigateToMap(professional)
    } else {
        pendingNavigation = { navigateToMap(professional) }
        showAuthBottomSheet = true
    }
}

// Componente
AuthBottomSheet(
    isVisible = showAuthBottomSheet,
    onDismiss = { 
        showAuthBottomSheet = false
        pendingNavigation = null
    },
    onLoginSuccess = {
        showAuthBottomSheet = false
        pendingNavigation?.invoke()
        pendingNavigation = null
    }
)
```

---

## 🧪 TESTES

### Loading
- [ ] Clicar em "Ver no Mapa" → Loading aparece
- [ ] Loading desaparece quando mapa carrega
- [ ] Mensagem clara

### AuthBottomSheet
- [ ] Usuário não logado → BottomSheet abre
- [ ] Login correto → Navega para mapa
- [ ] Login incorreto → Mensagem de erro
- [ ] Fechar BottomSheet → Cancela operação

---

## ✅ CHECKLIST

### Preparação
- [ ] Criar branch
- [ ] Garantir develop atualizado

### Loading
- [ ] Atualizar ProfessionalMapUiState
- [ ] Atualizar ProfessionalMapViewModel
- [ ] Atualizar ProfessionalMapScreen
- [ ] Testar loading

### AuthBottomSheet
- [ ] Criar AuthBottomSheet.kt
- [ ] Integrar SignInUseCase
- [ ] Criar UI (campos + botão)
- [ ] Implementar lógica de login
- [ ] Callback onLoginSuccess

### Integração
- [ ] Atualizar SearchProfessionalsScreen
- [ ] Adicionar estado showAuthBottomSheet
- [ ] Modificar callback onMapClick
- [ ] Implementar pendingNavigation
- [ ] Testar fluxo completo

### Finalização
- [ ] Commit
- [ ] Documentação
- [ ] Push

---

## 🎯 RESULTADO ESPERADO

### Loading
```
Usuário clica "Ver no Mapa"
    ↓
⏳ Loading aparece: "Carregando mapa..."
    ↓
Mapa carrega com marcadores
    ↓
✅ Loading desaparece
```

### Auth Inline
```
Usuário não logado clica "Ver no Mapa"
    ↓
🔐 AuthBottomSheet abre
    ↓
Usuário digita email/senha
    ↓
Clica "Entrar"
    ↓
✅ Login bem-sucedido
    ↓
BottomSheet fecha
    ↓
Navega automaticamente para mapa
```

---

## 📊 ESTIMATIVA

- **Loading**: ~15 minutos
- **AuthBottomSheet**: ~30 minutos
- **Testes**: ~15 minutos
- **Total**: ~60 minutos

---

**Status**: 🚀 Pronto para implementação!
