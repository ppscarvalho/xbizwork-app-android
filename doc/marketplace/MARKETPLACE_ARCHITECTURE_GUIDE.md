# 📚 Guia de Arquitetura para Marketplace

## Visão Geral da Estrutura

Este documento explica como a arquitetura está preparada para o seu marketplace de serviços (Uber de Serviços) e como adicionar novas features.

---

## 🏗️ Estrutura em Camadas

```
┌─────────────────────────────────────────────────────────┐
│                    PRESENTATION (UI)                     │
│            Compose Screens + MVVM ViewModels            │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│              APPLICATION (Use Cases)                     │
│        Lógica de negócio, validações, orquestração      │
│  SearchProfessionalsUseCase, CreateProposalUseCase, etc │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│              DOMAIN (Modelos e Contratos)                │
│     ProfessionalProfile, ServiceProposal, Repositories  │
│                  (100% independente)                     │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│                    DATA LAYER                            │
│  ┌──────────────────┬──────────────────┐               │
│  │  Remote Data     │   Local Data      │               │
│  │  Source (API)    │   Source (Cache)  │               │
│  └──────────────────┴──────────────────┘               │
│           (Repositories Implementation)                 │
└─────────────────────────────────────────────────────────┘
```

---

## 📦 Modelos Criados para Marketplace

### 1. **Professional** (`domain/model/professional/`)
- `ProfessionalProfile`: Perfil completo do profissional
- `ServiceCategory`: Enum das categorias (PLUMBING, ELECTRICAL, etc)
- `VerificationStatus`: Status de verificação

**Quando usar:**
- Exibir detalhes do profissional
- Listar profissionais em busca
- Mostrar rating e reviews

### 2. **Service/Proposal** (`domain/model/service/`)
- `ServiceProposal`: Uma proposta de serviço (do cliente)
- `ProposalStatus`: Estados (PENDING, ACCEPTED, IN_PROGRESS, etc)
- `ProposalResponse`: Resposta do profissional a uma proposta
- `ServiceReview`: Avaliação deixada pelo cliente

**Quando usar:**
- Criar nova proposta
- Listar propostas de um cliente
- Aceitar/recusar proposta (profissional)
- Deixar avaliação

### 3. **Search** (`domain/model/service/SearchFilters.kt`)
- `SearchFilters`: Critérios de busca (localização, categoria, preço)
- `ProfessionalSearchResult`: Resultado resumido para lista
- `SearchResult`: Resultado paginado

**Quando usar:**
- Buscar profissionais
- Aplicar filtros (categoria, preço, distância)
- Paginação de resultados

---

## 🔌 Repositórios (Interfaces)

### `ProfessionalRepository`
Operações relacionadas a profissionais:
```kotlin
suspend fun searchProfessionals(filters: SearchFilters): DefaultResult<SearchResult>
suspend fun getProfessionalDetails(professionalId: String): DefaultResult<ProfessionalProfile>
suspend fun getTrendingProfessionals(count: Int): DefaultResult<List<ProfessionalProfile>>
suspend fun addToFavorites(professionalId: String): DefaultResult<Unit>
// ... mais métodos
```

### `ProposalRepository`
Operações relacionadas a propostas:
```kotlin
suspend fun createProposal(proposal: ServiceProposal): DefaultResult<ServiceProposal>
suspend fun getClientProposals(page: Int): DefaultResult<List<ServiceProposal>>
suspend fun acceptProposal(proposalId: String): DefaultResult<ServiceProposal>
suspend fun submitReview(review: ServiceReview): DefaultResult<ServiceReview>
// ... mais métodos
```

---

## 🔄 DTOs (Data Transfer Objects)

DTOs são os modelos que vêm da API. São mapeados para o domínio automaticamente.

### Responses da API
- `ProfessionalResponseDtos.kt`: Busca e detalhes de profissionais
- `ProposalResponseDtos.kt`: Operações com propostas

### Requests para API
- `ProposalRequestDtos.kt`: Criação e respostas de propostas

**Padrão Gson:**
```kotlin
@SerializedName("profile_photo_url")  // JSON: profile_photo_url
val profilePhotoUrl: String?          // Kotlin: profilePhotoUrl
```

---

## 💼 Use Cases (Lógica de Negócio)

Cada Use Case é uma **operação discreta** de negócio:

### Profissionais
- `SearchProfessionalsUseCase`: Buscar com filtros
- Outros (a implementar): GetProfessionalDetailsUseCase, GetTrendingUseCase

### Propostas
- `CreateProposalUseCase`: Cliente cria proposta
- `AcceptProposalUseCase`: Profissional aceita
- `DeclineProposalUseCase`: Profissional recusa
- Outros (a implementar): CompleteProposalUseCase, SubmitReviewUseCase

**Estrutura de um Use Case:**
```kotlin
class SearchProfessionalsUseCase @Inject constructor(
    private val professionalRepository: ProfessionalRepository,
) {
    suspend operator fun invoke(filters: SearchFilters): DefaultResult<SearchResult> {
        // 1. Validações
        // 2. Lógica de negócio
        // 3. Chamada ao repository
        // 4. Tratamento de erro
        return professionalRepository.searchProfessionals(filters)
    }
}
```

---

## 📋 Como Adicionar uma Nova Feature

### Exemplo: Implementar "Marcar como Favorito"

#### Passo 1: Verificar/Estender o Modelo
```kotlin
// Já existe em ProfessionalRepository:
suspend fun addToFavorites(professionalId: String): DefaultResult<Unit>
suspend fun removeFromFavorites(professionalId: String): DefaultResult<Unit>
```

#### Passo 2: Criar Use Case (se necessário)
```kotlin
class AddToFavoritesUseCase @Inject constructor(
    private val professionalRepository: ProfessionalRepository,
) {
    suspend operator fun invoke(professionalId: String): DefaultResult<Unit> {
        return professionalRepository.addToFavorites(professionalId)
    }
}
```

#### Passo 3: Implementar Repository
```kotlin
class ProfessionalRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProfessionalRemoteDataSource,
    private val localDataSource: ProfessionalLocalDataSource,
) : ProfessionalRepository {
    
    override suspend fun addToFavorites(professionalId: String): DefaultResult<Unit> {
        return try {
            val response = remoteDataSource.addToFavorites(professionalId)
            localDataSource.saveFavorite(professionalId)
            DefaultResult.Success(Unit)
        } catch (e: Exception) {
            DefaultResult.Error("ERROR", e.message ?: "Erro ao adicionar favorito")
        }
    }
}
```

#### Passo 4: Implementar RemoteDataSource
```kotlin
interface ProfessionalRemoteDataSource {
    suspend fun addToFavorites(professionalId: String): Unit
}

class ProfessionalRemoteDataSourceImpl @Inject constructor(
    private val apiService: ProfessionalApiService,
) : ProfessionalRemoteDataSource {
    
    override suspend fun addToFavorites(professionalId: String) {
        apiService.addToFavorites(professionalId)
    }
}
```

#### Passo 5: Implementar API Service
```kotlin
interface ProfessionalApiService {
    @POST("professionals/{id}/favorites")
    suspend fun addToFavorites(
        @Path("id") professionalId: String
    ): Response<Unit>
}
```

#### Passo 6: Usar no ViewModel
```kotlin
class SearchViewModel @Inject constructor(
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
) : ViewModel() {
    
    fun addToFavorites(professionalId: String) {
        viewModelScope.launch {
            val result = addToFavoritesUseCase(professionalId)
            // Atualizar UI
        }
    }
}
```

---

## 🔄 Fluxo de Dados Completo

### Exemplo: Buscar Profissionais

```
1. UI (SearchScreen)
   └─> ViewModel.searchProfessionals(filters)
   
2. ViewModel
   └─> searchProfessionalsUseCase(filters)
   
3. Use Case (SearchProfessionalsUseCase)
   ├─> Validação de filtros
   └─> professionalRepository.searchProfessionals(filters)
   
4. Repository (ProfessionalRepositoryImpl)
   ├─> Tenta buscar cache local
   ├─> Se não houver, chama remoteDataSource
   └─> Salva resultado em cache local
   
5. Remote Data Source
   ├─> Faz requisição HTTP (com retry + interceptor JWT)
   └─> Mapeia DTO → Domain Model
   
6. Response volta pela cadeia
   ├─> Repository recebe Result
   ├─> Use Case recebe Result
   ├─> ViewModel recebe Result
   └─> UI atualiza com dados
```

---

## 🎯 Checklist para Adicionar Novo Endpoint

- [ ] **Modelo de Domínio**: Criar em `domain/model/`
- [ ] **DTO Response**: Criar em `data/remote/*/dtos/responses/`
- [ ] **DTO Request**: Criar em `data/remote/*/dtos/requests/` (se necessário)
- [ ] **Repository Interface**: Estender em `domain/repository/`
- [ ] **Use Case**: Criar em `domain/usecase/`
- [ ] **API Service**: Adicionar método em `data/remote/*/api/`
- [ ] **Remote DataSource**: Implementar em `data/remote/*/datasource/`
- [ ] **Repository Implementation**: Implementar em `data/repository/`
- [ ] **Tests**: Criar testes unitários
- [ ] **Integração no DI**: Adicionar ao Hilt Module se necessário

---

## 📂 Estrutura de Pastas Recomendada

```
app/src/main/java/com/br/xbizitwork/
├── domain/
│   ├── model/
│   │   ├── auth/
│   │   ├── professional/      ← Modelos do profissional
│   │   └── service/           ← Modelos de propostas/serviços
│   ├── repository/
│   │   ├── ProfessionalRepository.kt
│   │   └── ProposalRepository.kt
│   └── usecase/
│       ├── professional/
│       │   └── SearchProfessionalsUseCase.kt
│       └── proposal/
│           └── ProposalUseCases.kt
│
├── data/
│   ├── remote/
│   │   ├── professional/
│   │   │   ├── api/
│   │   │   │   └── ProfessionalApiService.kt
│   │   │   ├── datasource/
│   │   │   └── dtos/
│   │   │       └── responses/
│   │   └── proposal/
│   │       ├── api/
│   │       ├── datasource/
│   │       └── dtos/
│   │           ├── requests/
│   │           └── responses/
│   └── repository/
│       ├── professional/
│       │   └── ProfessionalRepositoryImpl.kt
│       └── proposal/
│           └── ProposalRepositoryImpl.kt
│
└── presentation/
    ├── ui/
    │   ├── screens/
    │   │   ├── SearchScreen.kt
    │   │   └── ProposalDetailsScreen.kt
    │   └── components/
    ├── viewmodel/
    │   ├── SearchViewModel.kt
    │   └── ProposalViewModel.kt
    └── state/
```

---

## 🔐 Segurança

Todos os endpoints sensíveis usam:
- ✅ JWT Token (injetado automaticamente pelo `AuthTokenInterceptor`)
- ✅ HTTPS (BuildConfig.BASE_URL)
- ✅ Retry com backoff (falhas transitórias)
- ✅ Cache com TTL (evita requisições desnecessárias)

---

## 🚀 Próximos Passos

1. **Implementar DataSources**: Criar `ProfessionalRemoteDataSourceImpl` e `ProposalRemoteDataSourceImpl`
2. **Implementar Repositories**: Criar implementações que coordenam Remote + Local
3. **Implementar API Services**: Adicionar endpoints ao Ktor Client
4. **Criar Screens em Compose**: UI para busca, criação de propostas, etc
5. **Adicionar Testes**: Testes para cada camada

---

## 📚 Referências

- **Clean Architecture**: Domain-independent business logic
- **MVVM**: ViewModel separado de lógica de negócio
- **Repository Pattern**: Abstração de fonte de dados
- **Use Cases**: Operações discretas de negócio
- **DTOs**: Transfer objects para API communication

Qualquer dúvida, consulte os arquivos implementados! 🚀
