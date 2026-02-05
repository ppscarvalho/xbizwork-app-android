# 🐛 INVESTIGAÇÃO DE BUG - Crash ao Buscar Profissional Novamente

**Data**: 03/02/2026  
**Prioridade**: 🔴 ALTA  
**Status**: 🔍 Em Investigação

---

## 📋 DESCRIÇÃO DO BUG

### Fluxo que Causa o Crash
```
1. Usuário faz login no AuthBottomSheet
2. Navega para o mapa (OK ✅)
3. Volta para Home (OK ✅)
4. Verifica Menu habilitado (OK ✅)
5. Verifica Nome na AppBar (OK ✅)
6. Clica em "Buscar Profissional" novamente
7. ❌ APP FECHA (CRASH)
```

### Frequência
- **2ª vez que acontece**
- Sempre no mesmo fluxo (após login → mapa → home → buscar novamente)

---

## 🔍 CAUSAS POSSÍVEIS

### 1. 🎯 PROVÁVEL: Ciclo de Vida do ViewModel

#### Problema Identificado: `observeAuthSession()`

**Código em SearchProfessionalsViewModel.kt:**
```kotlin
init {
    observeAuthSession()  // ← AQUI
}

private fun observeAuthSession() {
    viewModelScope.launch {
        getAuthSessionUseCase.invoke().collect { authSession ->  // ← Flow contínuo
            _uiState.update {
                it.copy(isAuthenticated = authSession.token.isNotEmpty())
            }
        }
    }
}
```

**Por que é problema:**
1. `collect` é um loop infinito que escuta mudanças
2. Quando navega: Home → Buscar → Mapa → Home → Buscar
3. ViewModel pode ser:
   - **Mantido** (se na back stack) OU
   - **Recriado** (dependendo da configuração)
4. Se recriado, um NOVO `collect` é iniciado
5. Mas o ANTIGO pode ainda estar rodando (memory leak)
6. Conflito entre múltiplos collectors → **CRASH**

**Evidência:**
- Crash acontece na SEGUNDA vez (primeira OK, segunda não)
- Sugere que há 2 instâncias coletando simultaneamente

---

### 2. 🔄 AuthBottomSheet + Session

#### Problema: Mudança de Estado Durante Navegação

**Fluxo problemático:**
```
Login no BottomSheet
    ↓
SignInViewModel.saveLocalSession()
    ↓
MainViewModel observa session
    ↓
SearchProfessionalsViewModel observa session
    ↓
AMBOS recebem atualização SIMULTANEAMENTE
    ↓
Race condition? 🤔
```

**Possível conflito:**
- AuthBottomSheet chama `onLoginSuccess()` que navega
- MAS a sessão ainda está sendo salva/propagada
- SearchProfessionalsViewModel tenta acessar dados não finalizados
- **CRASH**

---

### 3. 📱 Navigation Back Stack

#### Problema: Estado Compartilhado

**Cenário:**
```
SearchProfessionalsScreen (1ª vez)
    ↓
Navega para Mapa
    ↓
SearchProfessionalsScreen vai para back stack
    ↓
Volta para Home
    ↓
Clica Buscar novamente
    ↓
SearchProfessionalsScreen (2ª instância?)
    ↓
Conflito de estados
```

**Se houver 2 instâncias:**
- 1ª ainda observando session
- 2ª tenta observar também
- **CRASH**

---

### 4. 🔒 Thread Safety no MainViewModel

#### Problema: Múltiplas Atualizações Simultâneas

**Cenário:**
```
Login salva token
    ↓
MainViewModel atualiza
    ↓
SearchProfessionalsViewModel consulta
    ↓
SIMULTANEAMENTE:
    ├─ AuthBottomSheet navega
    ├─ MainViewModel propaga mudança
    └─ SearchProfessionalsViewModel coleta
    ↓
Race condition → CRASH
```

---

## 🛠️ SOLUÇÕES PROPOSTAS

### SOLUÇÃO 1: Usar `stateIn` ao Invés de `collect` ✅

**Problema atual:**
```kotlin
// ❌ collect é um loop bloqueante
private fun observeAuthSession() {
    viewModelScope.launch {
        getAuthSessionUseCase.invoke().collect { authSession ->
            _uiState.update { ... }
        }
    }
}
```

**Solução:**
```kotlin
// ✅ stateIn gerencia lifecycle automaticamente
private val authSession = getAuthSessionUseCase.invoke()
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AuthSession.empty()
    )

init {
    observeAuthSession()
}

private fun observeAuthSession() {
    viewModelScope.launch {
        authSession.collect { session ->
            _uiState.update {
                it.copy(isAuthenticated = session.token.isNotEmpty())
            }
        }
    }
}
```

**Benefícios:**
- `WhileSubscribed(5_000)` cancela após 5s sem subscribers
- Evita múltiplos collectors ativos
- Gerenciamento automático de lifecycle

---

### SOLUÇÃO 2: Cancelar Coleta Anterior ✅

```kotlin
private var authSessionJob: Job? = null

private fun observeAuthSession() {
    // Cancela coleta anterior se existir
    authSessionJob?.cancel()
    
    authSessionJob = viewModelScope.launch {
        getAuthSessionUseCase.invoke().collect { authSession ->
            _uiState.update {
                it.copy(isAuthenticated = authSession.token.isNotEmpty())
            }
        }
    }
}
```

---

### SOLUÇÃO 3: Usar `collectLatest` ao Invés de `collect` ✅

```kotlin
private fun observeAuthSession() {
    viewModelScope.launch {
        getAuthSessionUseCase.invoke().collectLatest { authSession ->  // ← collectLatest
            _uiState.update {
                it.copy(isAuthenticated = authSession.token.isNotEmpty())
            }
        }
    }
}
```

**Benefício:**
- `collectLatest` cancela operação anterior se nova emissão chegar
- Evita processamento concorrente

---

### SOLUÇÃO 4: Adicionar Try-Catch ✅

```kotlin
private fun observeAuthSession() {
    viewModelScope.launch {
        try {
            getAuthSessionUseCase.invoke().collect { authSession ->
                _uiState.update {
                    it.copy(isAuthenticated = authSession.token.isNotEmpty())
                }
            }
        } catch (e: CancellationException) {
            // Ignorar cancelamento (é esperado)
            throw e
        } catch (e: Exception) {
            logInfo("SEARCH_PROFESSIONALS_VM", "❌ Erro ao observar sessão: ${e.message}")
            e.printStackTrace()
        }
    }
}
```

---

### SOLUÇÃO 5: Verificar Navigation (launchSingleTop) ✅

**Verificar se navegação está usando `launchSingleTop`:**

```kotlin
fun NavController.navigateToSearchProfessionalBySkillScreen() {
    navigate(MenuScreens.SearchProfessionalBySkillScreen) {
        launchSingleTop = true  // ← IMPORTANTE!
        // Evita criar múltiplas instâncias
    }
}
```

---

## 🧪 PLANO DE TESTE

### Teste 1: Implementar stateIn
```kotlin
1. Implementar SOLUÇÃO 1
2. Testar fluxo: Login → Mapa → Home → Buscar
3. Verificar: App não fecha
4. Testar múltiplas vezes
```

### Teste 2: Adicionar Logs
```kotlin
1. Adicionar logs em:
   - observeAuthSession() inicio
   - observeAuthSession() coleta
   - init() do ViewModel
2. Reproduzir bug
3. Ver logs: quantas vezes init() é chamado?
```

### Teste 3: Verificar Navigation
```kotlin
1. Verificar launchSingleTop
2. Adicionar popUpTo se necessário
3. Testar navegação
```

---

## 📊 PRIORIZAÇÃO DAS SOLUÇÕES

| Solução | Prioridade | Dificuldade | Impacto |
|---------|-----------|-------------|---------|
| 1. stateIn | 🔴 ALTA | Média | Alto |
| 2. Cancelar Job | 🟡 MÉDIA | Baixa | Médio |
| 3. collectLatest | 🟡 MÉDIA | Baixa | Médio |
| 4. Try-Catch | 🟢 BAIXA | Baixa | Baixo |
| 5. Navigation | 🟡 MÉDIA | Baixa | Médio |

**Recomendação**: Implementar **SOLUÇÃO 1 + 4** (stateIn + try-catch)

---

## 🎯 PLANO DE AÇÃO IMEDIATO

### 1. Adicionar Logs Detalhados (5 min)
```kotlin
init {
    logInfo("SEARCH_PROFESSIONALS_VM", "🟢 ViewModel CRIADO - hashCode: ${this.hashCode()}")
    observeAuthSession()
}

private fun observeAuthSession() {
    logInfo("SEARCH_PROFESSIONALS_VM", "🔵 Iniciando observeAuthSession")
    viewModelScope.launch {
        getAuthSessionUseCase.invoke().collect { authSession ->
            logInfo("SEARCH_PROFESSIONALS_VM", "🟡 Sessão recebida: token=${authSession.token.take(10)}...")
            _uiState.update {
                it.copy(isAuthenticated = authSession.token.isNotEmpty())
            }
        }
    }
}

override fun onCleared() {
    logInfo("SEARCH_PROFESSIONALS_VM", "🔴 ViewModel DESTRUÍDO - hashCode: ${this.hashCode()}")
    super.onCleared()
}
```

### 2. Implementar stateIn (10 min)
```kotlin
private val authSession = getAuthSessionUseCase.invoke()
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = AuthSession(
            id = 0,
            name = "",
            email = "",
            token = "",
            errorMessage = ""
        )
    )

private fun observeAuthSession() {
    viewModelScope.launch {
        try {
            authSession.collect { session ->
                logInfo("SEARCH_PROFESSIONALS_VM", "🟡 Auth atualizada: ${session.token.isNotEmpty()}")
                _uiState.update {
                    it.copy(isAuthenticated = session.token.isNotEmpty())
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            logInfo("SEARCH_PROFESSIONALS_VM", "❌ Erro: ${e.message}")
            e.printStackTrace()
        }
    }
}
```

### 3. Verificar Navigation (5 min)
```kotlin
// SearchProfessionalBySkillNavigation.kt
fun NavController.navigateToSearchProfessionalBySkillScreen() {
    navigate(MenuScreens.SearchProfessionalBySkillScreen) {
        launchSingleTop = true
        restoreState = true
    }
}
```

### 4. Testar (10 min)
- Reproduzir bug
- Verificar logs
- Confirmar correção

**TEMPO TOTAL: ~30 minutos**

---

## 🔍 OUTRAS ÁREAS A INVESTIGAR

### 1. MainViewModel
- Verificar se também tem problema similar de múltiplos collectors

### 2. AuthBottomSheet
- Verificar se `onLoginSuccess()` é chamado múltiplas vezes

### 3. Navigation Stack
- Verificar se há memory leak na navegação

### 4. DataStore
- Verificar se há problema ao salvar sessão durante navegação

---

## 📝 LOGS PARA ADICIONAR

### Pontos Críticos:
1. ✅ ViewModel init/destroy
2. ✅ observeAuthSession início/coleta
3. ✅ Navigation para SearchProfessionalsScreen
4. ✅ AuthBottomSheet onLoginSuccess
5. ✅ MainViewModel session update

---

## ✅ CHECKLIST DE CORREÇÃO

- [ ] Adicionar logs detalhados
- [ ] Implementar stateIn
- [ ] Adicionar try-catch
- [ ] Verificar navigation (launchSingleTop)
- [ ] Testar fluxo completo 3x
- [ ] Verificar logs
- [ ] Remover logs de debug (ou deixar como debug level)
- [ ] Documentar correção

---

## 🎯 RESULTADO ESPERADO

Após implementação:
- ✅ App não fecha mais
- ✅ Logs mostram apenas 1 ViewModel ativo
- ✅ Session é observada corretamente
- ✅ Navegação funciona múltiplas vezes
- ✅ Sem memory leaks

---

**Investigação realizada por**: GitHub Copilot  
**Data**: 03/02/2026  
**Status**: 🔄 Aguardando implementação das correções
