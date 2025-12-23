# 🔧 PADRONIZAÇÃO DE RESPONSES - Análise e Plano de Correção

## Data: 2025-12-23

---

## 🎯 Problema Identificado

Atualmente temos **3 classes** para representar a mesma coisa em cada módulo:
- `CategoryResponse` (API/DTO com @SerializedName)
- `CategoryResponseModel` (DataSource sem anotações)
- `CategoryResult` (Domain)

**Isso gera:**
- ❌ Duplicação desnecessária
- ❌ Confusão de nomenclatura
- ❌ Múltiplos mappers
- ❌ Código difícil de manter

---

## ✅ Padrão Correto (Auth/Profile)

### Camadas e Responsabilidades

```
┌─────────────────────────────────────────────────────────────┐
│                      API LAYER (remote/api)                  │
│  - UserAuthApiService / ProfileApiService                    │
│  - Retorna DTOs com @SerializedName (GSON)                   │
│  - ApiResultResponse (create/update/delete)                  │
│  - ApiResponse<T> (listas/objetos)                           │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│              DATASOURCE LAYER (remote/datasource)            │
│  - Converte DTO → Model (sem anotações)                      │
│  - SignUpResponseModel / ApiResultModel                      │
│  - Trata erros                                                │
│  - Retorna DefaultResult<Model>                              │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                REPOSITORY LAYER (repository)                 │
│  - Converte Model → DomainResult                             │
│  - Usa withContext(IO)                                       │
│  - Retorna DomainDefaultResult<DomainResult>                 │
└─────────────────────────────────────────────────────────────┘
```

---

## 📊 Análise por Módulo

### ✅ AUTH - PADRÃO CORRETO

#### API Layer
```kotlin
// Para lista (SignIn retorna dados)
data class SignInResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("token") val token: String,
    @SerializedName("isSuccessful") val isSuccessful: Boolean,
    @SerializedName("message") val message: String
)

// Para create/update/delete (SignUp)
data class ApiResultResponse(
    @SerializedName("isSuccessful") val isSuccessful: Boolean,
    @SerializedName("message") val message: String
)
```

#### DataSource Layer
```kotlin
data class SignUpResponseModel(
    val isSuccessful: Boolean,
    val message: String
)

data class SignInResponseModel(
    val id: Int,
    val name: String,
    val email: String,
    val token: String
)
```

#### Domain Layer
```kotlin
data class SignUpResult(
    val isSuccessful: Boolean,
    val message: String
)

data class SignInResult(
    val id: Int,
    val name: String,
    val email: String,
    val token: String
)
```

**Padrão:** ✅ 1 DTO + 1 Model + 1 Result = 3 classes necessárias

---

### ✅ PROFILE - PADRÃO CORRETO

#### API Layer
```kotlin
// Para update (retorna só sucesso/falha)
data class ApiResultResponse(
    @SerializedName("isSuccessful") val isSuccessful: Boolean,
    @SerializedName("message") val message: String
)
```

#### DataSource Layer
```kotlin
// Usa ApiResultModel diretamente
data class ApiResultModel(
    val isSuccessful: Boolean,
    val message: String
)
```

**Padrão:** ✅ Reutiliza ApiResultResponse/ApiResultModel (correto!)

---

### ❌ CATEGORY - PRECISA CORREÇÃO

#### Atual (ERRADO)
```kotlin
// API Layer - OK
data class CategoryResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("description") val description: String
)

// DataSource Layer - DESNECESSÁRIO
data class CategoryResponseModel(
    val id: Int,
    val description: String
)

// Domain Layer - OK  
data class CategoryResult(
    val id: Int,
    val description: String
)
```

**Problema:** `CategoryResponseModel` é desnecessário! Basta:
- API retorna `ApiResponse<List<CategoryResponse>>`
- DataSource converte para `CategoryModel` (sem ResponseModel)
- Repository converte para `CategoryResult`

**Correção:** 
- ✅ Manter `CategoryResponse` (API/DTO)
- ❌ Remover `CategoryResponseModel`  
- ✅ Criar `CategoryModel` (DataSource)
- ✅ Manter `CategoryResult` (Domain)

---

### ❌ SPECIALTY - PRECISA CORREÇÃO

Mesmo problema que Category:
- `SpecialtyResponse` (API) ✅
- `SpecialtyResponseModel` (DataSource) ❌ REMOVER
- Criar `SpecialtyModel` ✅
- `SpecialtyResult` (Domain) ✅

---

### ❌ CEP - ANALISAR

Preciso verificar estrutura atual do Cep

---

### ❌ SCHEDULE - ANALISAR

Preciso verificar estrutura atual do Schedule

---

## 🎯 Padrão Final Estabelecido

### Para Listas (GET)
```
API: ApiResponse<List<CategoryResponse>>
     ↓
DataSource: CategoryModel (sem anotações)
     ↓
Repository: CategoryResult (domain)
```

### Para Create/Update/Delete
```
API: ApiResultResponse
     ↓
DataSource: ApiResultModel
     ↓
Repository: ApiResultModel (domain)
```

---

## 📋 Plano de Ação

### 1. Category
- [ ] Remover `CategoryResponseModel`
- [ ] Criar `CategoryModel` em data layer
- [ ] Atualizar mappers
- [ ] Atualizar DataSource para retornar `DefaultResult<List<CategoryModel>>`
- [ ] Atualizar Repository para converter `CategoryModel` → `CategoryResult`

### 2. Specialty
- [ ] Remover `SpecialtyResponseModel`
- [ ] Criar `SpecialtyModel` em data layer
- [ ] Atualizar mappers
- [ ] Atualizar DataSource
- [ ] Atualizar Repository

### 3. Cep
- [ ] Analisar estrutura atual
- [ ] Aplicar padrão correto

### 4. Schedule
- [ ] Analisar estrutura atual
- [ ] Aplicar padrão correto

---

## 🔍 Próximos Passos

1. Analisar Cep e Schedule
2. Aplicar correções em todos os módulos
3. Atualizar mappers
4. Testar compilação
5. Documentar padrão final

---

**Status:** 📝 Análise completa - Aguardando início das correções

