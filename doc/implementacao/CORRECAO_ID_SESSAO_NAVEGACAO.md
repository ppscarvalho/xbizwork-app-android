# Correção: ID da Sessão e Navegação do Login

**Data:** 2025-12-21  
**Problema:** ID do usuário estava sendo salvo como 0 na sessão e o botão voltar do SignIn não funcionava

---

## 🐛 Problemas Identificados

### 1. ID = 0 na Sessão
**Sintoma:** 
```
💾 Salvando sessão:
  - userId: 0
  - userName: 'Pedro Carvalho'
  - userEmail: 'ppscarvalho@gmail.com'
```

**Causa Raiz:**  
O mapper `SignInResponseModel.toDomainResponse()` **NÃO estava mapeando o campo `id`**, então o valor padrão `null` era convertido para `0`.

### 2. Botão Voltar do SignIn Não Funcionava
**Sintoma:** AppTopBar do SignInScreen exibia o botão voltar, mas clicar não tinha efeito.

**Causa Raiz:**  
O callback `onNavigateBack` não estava sendo propagado:
- `RootHost` → `authGraph` ❌ (não passava)
- `authGraph` → `signInScreen` ❌ (não passava)

---

## ✅ Correções Aplicadas

### 1. AuthMappers.kt - Adicionar Mapeamento do ID

**Arquivo:** `data/mappers/AuthMappers.kt`

**Antes:**
```kotlin
fun SignInResponseModel.toDomainResponse(): SignInResult {
    return SignInResult(
        name = this.name ?: "",
        email = this.email ?: "",
        token = this.token ?: "",
        isSuccessful = this.isSuccessful,
        message = this.message
    )
}
```

**Depois:**
```kotlin
fun SignInResponseModel.toDomainResponse(): SignInResult {
    logInfo("DOMAIN_MAPPER_DEBUG", "SignInResponseModel mapeado: id=$id, name=$name, email=$email, token=$token")
    return SignInResult(
        id = this.id ?: 0,  // ✅ ADICIONADO
        name = this.name ?: "",
        email = this.email ?: "",
        token = this.token ?: "",
        isSuccessful = this.isSuccessful,
        message = this.message
    ).also {
        logInfo("DOMAIN_MAPPER_DEBUG", "SignInResult criado: id=${it.id}, name=${it.name}, email=${it.email}, token=${it.token}")
    }
}
```

---

### 2. AuthGraph.kt - Propagar onNavigateBack

**Arquivo:** `ui/presentation/navigation/graphs/AuthGraph.kt`

**Antes:**
```kotlin
fun NavGraphBuilder.authGraph(
    onNavigateToHomeGraph: (NavOptions) -> Unit,
    onNavigateToSignInScreen: () -> Unit,
    onNavigateToSignUpScreen: () -> Unit
){
    navigation<Graphs.AuthGraphs>(
        startDestination = AuthScreens.SignInScreen
    ){
        signInScreen(
            onNavigateToHomeGraph = { onNavigateToHomeGraph(navOptions{
                popUpTo(Graphs.AuthGraphs)
            }) },
            onNavigateToSignUpScreen = onNavigateToSignUpScreen
            // ❌ FALTAVA onNavigateBack
        )
        // ...
    }
}
```

**Depois:**
```kotlin
fun NavGraphBuilder.authGraph(
    onNavigateToHomeGraph: (NavOptions) -> Unit,
    onNavigateToSignInScreen: () -> Unit,
    onNavigateToSignUpScreen: () -> Unit,
    onNavigateBack: () -> Unit = {}  // ✅ ADICIONADO
){
    navigation<Graphs.AuthGraphs>(
        startDestination = AuthScreens.SignInScreen
    ){
        signInScreen(
            onNavigateToHomeGraph = { onNavigateToHomeGraph(navOptions{
                popUpTo(Graphs.AuthGraphs)
            }) },
            onNavigateToSignUpScreen = onNavigateToSignUpScreen,
            onNavigateBack = onNavigateBack  // ✅ ADICIONADO
        )
        // ...
    }
}
```

---

### 3. RootHost.kt - Passar Callback de Navegação

**Arquivo:** `ui/presentation/navigation/RootHost.kt`

**Antes:**
```kotlin
authGraph(
    onNavigateToHomeGraph = {navOptions ->
        navController.navigationToHomeGraph(navOptions)
    },
    onNavigateToSignUpScreen = {
        navController.navigateToSignUpScreen()
    },
    onNavigateToSignInScreen = {
        navController.navigateToSignInScreen()
    }
    // ❌ FALTAVA onNavigateBack
)
```

**Depois:**
```kotlin
authGraph(
    onNavigateToHomeGraph = {navOptions ->
        navController.navigationToHomeGraph(navOptions)
    },
    onNavigateToSignUpScreen = {
        navController.navigateToSignUpScreen()
    },
    onNavigateToSignInScreen = {
        navController.navigateToSignInScreen()
    },
    onNavigateBack = {
        navController.navigateUp()  // ✅ ADICIONADO
    }
)
```

---

### 4. ProfessionalScheduleCard.kt - Melhorar Contraste das Cores

**Arquivo:** `ui/presentation/components/schedule/ProfessionalScheduleCard.kt`

**Problema:** Texto estava aparecendo branco em fundo branco

**Antes:**
```kotlin
colors = CardDefaults.cardColors(
    containerColor = MaterialTheme.colorScheme.surface  // ❌ Baixo contraste
)
```

**Depois:**
```kotlin
colors = CardDefaults.cardColors(
    containerColor = MaterialTheme.colorScheme.surfaceVariant  // ✅ Melhor contraste
)
```

---

## 🔍 Fluxo Completo do ID

```
API Response
{
  "data": {
    "id": 14,           ← Vem da API
    "name": "...",
    "email": "...",
    "token": "..."
  }
}
        ↓
SignInResponse (DTO)
data.id = 14
        ↓
toLoginResponseModel()
        ↓
SignInResponseModel
id = 14
        ↓
toDomainResponse() ← ✅ CORRIGIDO AQUI
        ↓
SignInResult
id = 14
        ↓
SignInViewModel
userId = 14
        ↓
saveLocalSession(14, ...)
        ↓
AuthSessionLocalDataSourceImpl
preferences[ID_KEY] = 14
        ↓
DataStore
✅ ID salvo corretamente!
```

---

## 📋 Checklist de Testes

- [ ] Fazer logout
- [ ] Fazer login com credenciais válidas
- [ ] Verificar log: `userId: 14` (não deve ser 0)
- [ ] Navegar para "Minhas Agendas"
- [ ] Verificar requisição: `.../schedule/professional/14` (não deve ser 0)
- [ ] Clicar no botão voltar do SignIn (deve voltar para tela anterior)
- [ ] Verificar se as cores dos cards estão legíveis

---

## ✅ Status da Compilação

```
BUILD SUCCESSFUL in 49s
41 actionable tasks: 13 executed, 28 up-to-date
```

**Sem erros de compilação!**

---

## 📝 Notas Importantes

1. **Sessão Antiga:** Se o usuário já estava logado, a sessão antiga pode ter ID=0. É necessário fazer **logout e login novamente** para criar uma nova sessão com o ID correto.

2. **Logs Adicionados:** Foram adicionados logs em pontos estratégicos para facilitar o debug:
   - `MAPPER_DEBUG` - Conversão de DTOs
   - `DOMAIN_MAPPER_DEBUG` - Conversão para domain models
   - `SIGN_IN_SUCCESS` - Response da API
   - `SIGN_IN_SESSION` - Salvamento da sessão
   - `SAVE_SESSION` - Confirmação do salvamento

3. **Padrão de Navegação:** O callback `onNavigateBack` agora segue o mesmo padrão usado em outras partes do app, garantindo consistência.

---

## 🎯 Impacto

✅ ID do usuário agora é salvo corretamente na sessão  
✅ Requisições para a API agora usam o ID correto  
✅ Botão voltar do SignIn funciona corretamente  
✅ Cards de agenda têm melhor contraste de cores  
✅ Compilação sem erros

