# 🎯 Análise: DefaultResult vs DomainDefaultResult - Qual Usar?

**Data:** Dezembro 6, 2025  
**Questão:** Usar uma classe ou duas? Qual deve estar em qual camada?  
**Resposta:** ✅ MANTER AMBAS (está correto!)

---

## 📊 Comparação: Suas Duas Classes

### DefaultResult (Core Layer)
```kotlin
// Location: core/result/DefaultResult.kt
sealed class DefaultResult<out T> {
    data class Success<out T>(val data: T) : DefaultResult<T>()
    data class Error(val code: String? = null, val message: String) : DefaultResult<Nothing>()
}
```

### DomainDefaultResult (Domain Layer)
```kotlin
// Location: domain/common/DomainDefaultResult.kt
sealed class DomainDefaultResult<out T> {
    data class Success<out T>(val data: T) : DomainDefaultResult<T>()
    data class Error(val code: String? = null, val message: String) : DomainDefaultResult<Nothing>()
    companion object
}
```

---

## 🏗️ Por Que Manter Ambas?

### ✅ Separação de Responsabilidades (Core Pattern)

```
┌─────────────────────────────────────────────────────────┐
│              PRESENTATION LAYER (UI)                    │
│            (ViewModels, Screens, State)                 │
└────────────────────┬────────────────────────────────────┘
                     │ depends on
┌────────────────────▼────────────────────────────────────┐
│           DOMAIN LAYER (Business Logic)                 │
│  ┌──────────────────────────────────────────────────┐  │
│  │  Models, Use Cases, Repository (Interfaces)     │  │
│  │  Returns: DomainDefaultResult<T>                │  │
│  │  ✅ Independente de framework                   │  │
│  │  ✅ Independente de camada de dados             │  │
│  └──────────────────────────────────────────────────┘  │
└────────────────────┬────────────────────────────────────┘
                     │ implements
┌────────────────────▼────────────────────────────────────┐
│              DATA LAYER (I/O)                           │
│  ┌──────────────────────────────────────────────────┐  │
│  │  Repositories (Impl), DataSources, DTOs         │  │
│  │  Remote: Returns DefaultResult<DTO>             │  │
│  │  Mapeia: DefaultResult → DomainDefaultResult    │  │
│  └──────────────────────────────────────────────────┘  │
└────────────────────┬────────────────────────────────────┘
                     │ uses
┌────────────────────▼────────────────────────────────────┐
│              CORE LAYER (Utilities)                     │
│  ┌──────────────────────────────────────────────────┐  │
│  │  DefaultResult (genérico, reutilizável)         │  │
│  │  Network utils, Retry, Cache, etc               │  │
│  └──────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────┘
```

---

## 📍 Onde Cada Uma É Usada

### DefaultResult (Core)

**Usado em:**
- ✅ RemoteDataSource (API calls)
- ✅ LocalDataSource (database operations)
- ✅ Network utilities
- ✅ Cache implementations
- ✅ Qualquer operação de I/O

**Exemplo:**
```kotlin
// data/remote/auth/datasource/interfaces/UserAuthRemoteDataSource.kt
interface UserAuthRemoteDataSource {
    suspend fun signIn(signInRequest: SignInRequestModel): 
        DefaultResult<ApplicationResponseModel>  // ✅ DefaultResult (Core)
}

// Implementação
class UserAuthRemoteDataSourceImpl : UserAuthRemoteDataSource {
    override suspend fun signIn(request: SignInRequestModel): 
        DefaultResult<ApplicationResponseModel> {
        return try {
            // API call
            DefaultResult.Success(response)  // ✅ Core Result
        } catch (e: Exception) {
            DefaultResult.Error(message = e.message ?: "Unknown error")
        }
    }
}
```

---

### DomainDefaultResult (Domain)

**Usado em:**
- ✅ Repository Interfaces (contracts)
- ✅ Use Cases (return types)
- ✅ Domain Models
- ✅ Business logic

**Exemplo:**
```kotlin
// domain/repository/auth/UserAuthRepository.kt
interface UserAuthRepository {
    suspend fun signIn(signInModel: SignInModel): 
        DomainDefaultResult<SignInResult>  // ✅ DomainDefaultResult
}

// Implementação (mapeia Core → Domain)
class UserAuthRepositoryImpl : UserAuthRepository {
    override suspend fun signIn(signInModel: SignInModel): 
        DomainDefaultResult<SignInResult> {
        val remoteResult = remoteDataSource.signIn(request)
        
        return when (remoteResult) {
            is DefaultResult.Success -> {  // ✅ Core
                DomainDefaultResult.Success(...)  // ✅ Domain
            }
            is DefaultResult.Error -> {
                DomainDefaultResult.Error(message = remoteResult.message)
            }
        }
    }
}

// Use Case
class SignInUseCase {
    suspend fun execute(model: SignInModel): UiState<SignInResult> {
        val result = repository.signIn(model)
        
        return when (result) {
            is DomainDefaultResult.Success -> {  // ✅ Domain
                UiState.Success(result.data)
            }
            is DomainDefaultResult.Error -> {
                UiState.Error(Throwable(result.message))
            }
        }
    }
}
```

---

## 🔄 Fluxo Completo de Dados

```
┌────────────────────────────────────────────────────────────┐
│  API Response (JSON)                                       │
└────────────────┬───────────────────────────────────────────┘
                 │
                 ▼
┌────────────────────────────────────────────────────────────┐
│  RemoteDataSource.signIn()                                 │
│  ├─ Faz requisição HTTP                                   │
│  ├─ Trata exceções                                        │
│  └─ Retorna: DefaultResult<ResponseDTO>                   │
└────────────────┬───────────────────────────────────────────┘
                 │ (Core Result)
                 ▼
┌────────────────────────────────────────────────────────────┐
│  RepositoryImpl.signIn()                                   │
│  ├─ Recebe: DefaultResult<ResponseDTO>                    │
│  ├─ Mapeia DTO → Domain Model                             │
│  ├─ Converte: DefaultResult → DomainDefaultResult         │
│  └─ Retorna: DomainDefaultResult<SignInResult>            │
└────────────────┬───────────────────────────────────────────┘
                 │ (Domain Result)
                 ▼
┌────────────────────────────────────────────────────────────┐
│  UseCase.invoke()                                          │
│  ├─ Recebe: DomainDefaultResult<SignInResult>            │
│  ├─ Converte para: UiState                               │
│  └─ Emite para: ViewModel                                │
└────────────────┬───────────────────────────────────────────┘
                 │ (UI State)
                 ▼
┌────────────────────────────────────────────────────────────┐
│  ViewModel → UI                                            │
│  Atualiza tela com resultado                              │
└────────────────────────────────────────────────────────────┘
```

---

## ✅ Benefícios de Manter Ambas

### 1. **Isolamento de Camadas**
```
Core    ├─ DefaultResult (genérico, sem contexto)
Domain  ├─ DomainDefaultResult (contexto de negócio)
Data    ├─ Usa DefaultResult em I/O
        └─ Mapeia para DomainDefaultResult
```

### 2. **Flexibilidade**
```kotlin
// Se remover DomainDefaultResult:
// ❌ Domain layer importaria core (violaria independência)
// ❌ Core saberia de Domain (acoplamento)

// Com ambas:
// ✅ Core é completamente independente
// ✅ Domain é independente de Data
// ✅ Cada camada tem seu próprio tipo
```

### 3. **Fácil Manutenção**
```kotlin
// Se precisar mudar formato do resultado em Data:
// ✅ Só muda DefaultResult (Core)
// ✅ Domain fica intocado

// Se precisar mudar formato em Domain:
// ✅ Só muda DomainDefaultResult
// ✅ Data e Core ficam intocados
```

### 4. **Testabilidade**
```kotlin
// Teste de Data Layer
@Test
fun testRemoteDataSource() {
    val result = remoteDataSource.signIn(request)
    assertThat(result).isInstanceOf(DefaultResult.Success::class.java)
}

// Teste de Domain Layer
@Test
fun testRepository() {
    val result = repository.signIn(model)
    assertThat(result).isInstanceOf(DomainDefaultResult.Success::class.java)
}

// Teste isolado, sem misturar conceitos
```

---

## 🤔 Alternativas (E Por Que Não Usar)

### ❌ Opção 1: Usar Apenas DefaultResult em Tudo
```kotlin
// ❌ ERRADO
interface UserAuthRepository {
    suspend fun signIn(model: SignInModel): DefaultResult<SignInResult>
    // Problem: Domain importa Core (acoplamento)
}
```

**Problemas:**
- ❌ Domain depende de Core (violaria princípio)
- ❌ Difícil de entender sem contexto
- ❌ Não representa bem a semântica de negócio

---

### ❌ Opção 2: Usar Apenas DomainDefaultResult em Tudo
```kotlin
// ❌ ERRADO
interface UserAuthRemoteDataSource {
    suspend fun signIn(request: SignInRequestModel): 
        DomainDefaultResult<ResponseDTO>
    // Problem: Data layer importa Domain
}
```

**Problemas:**
- ❌ Data layer importaria Domain (violaria dependency rule)
- ❌ Core não teria seu próprio tipo
- ❌ Acoplamento entre camadas

---

### ❌ Opção 3: Usar Result<T> do Kotlin
```kotlin
// ❌ ERRADO (para este contexto)
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Failure(val exception: Throwable) : Result<Nothing>()
}
```

**Problemas:**
- ❌ Kotlin Result é para tratamento de exceções
- ❌ Não captura informações de erro (code, message)
- ❌ Pensado para outro propósito

---

## ✅ Sua Implementação Está Correta

### Checklist:

- [x] `DefaultResult` está em `core/result/` ✅
- [x] `DomainDefaultResult` está em `domain/common/` ✅
- [x] Data layer usa `DefaultResult` ✅
- [x] Domain layer usa `DomainDefaultResult` ✅
- [x] Repository (impl) mapeia `DefaultResult` → `DomainDefaultResult` ✅
- [x] Use Cases recebem `DomainDefaultResult` ✅
- [x] Nenhuma camada viola as dependencies ✅

---

## 📋 Mapeamento Correto

```kotlin
// Data Layer
RemoteDataSource.signIn(): DefaultResult<DTO>
           ↓ (mapping)
RepositoryImpl.signIn(): DomainDefaultResult<DomainModel>
           ↓ (mapping)
UseCase.invoke(): UiState<Result>
           ↓
ViewModel.uiState: StateFlow<UiState>
           ↓
UI: Observa e atualiza
```

**Cada transição mapeia para o tipo apropriado da camada!**

---

## 🎯 Recomendação Final

### ✅ MANTER AMBAS!

**Por que:**
1. ✅ Segue Clean Architecture corretamente
2. ✅ Cada camada tem seu próprio contrato
3. ✅ Fácil de manter e testar
4. ✅ Flexível para mudanças futuras
5. ✅ Padrão amplamente usado em arquitetura Android
6. ✅ Você já implementou corretamente!

### 📝 Dica:

Se em algum momento você precisar **consolidar**, o correto seria:
- ❌ NÃO consolidar
- ✅ Manter como está (está perfeito)

A "duplicação" não é realmente duplicação - é **separação de responsabilidades**!

---

## 🚀 Resumo Visual

```
CORE          DefaultResult<T>       (Genérico, Framework-agnostic)
   ↓
DATA          DefaultResult (Input) → DomainDefaultResult (Output)
   ↓
DOMAIN        DomainDefaultResult<T> (Contexto de negócio)
   ↓
PRESENTATION  UiState<T>            (Contexto de UI)
```

**Cada transição transforma o tipo para o contexto correto!** 🎯
