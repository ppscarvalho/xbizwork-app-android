# 🔍 AUDITORIA COMPLETA - Use Cases vs Padrão SignUpUseCase

## Data: 2025-12-23

---

## 🎯 Padrão de Referência: SignUpUseCase

### ✅ Estrutura Correta
```kotlin
/**
 * Interface do Use Case
 * - operator fun invoke(parameters: Parameters): Flow<UiState<Result>>
 * - data class Parameters(...) dentro da interface
 */
interface SignUpUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<SignUpResult>>
    data class Parameters(val signUpModel: SignUpModel)
}

/**
 * Implementação
 * - Herda de FlowUseCase<Parameters, Result>()
 * - Implementa a interface
 * - override suspend fun executeTask(parameters): UiState<Result>
 * - try/catch com tratamento de exceções
 */
class SignUpUseCaseImpl @Inject constructor(
    private val authRepository: UserAuthRepository
) : SignUpUseCase, FlowUseCase<SignUpUseCase.Parameters, SignUpResult>() {

    override suspend fun executeTask(parameters: SignUpUseCase.Parameters): UiState<SignUpResult> {
        return try {
            when (val response = authRepository.signUp(parameters.signUpModel)) {
                is DomainDefaultResult.Success -> UiState.Success(response.data)
                is DomainDefaultResult.Error -> UiState.Error(Throwable(response.message))
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
```

---

## 📊 Análise por Módulo

### 1. ✅ CATEGORY - GetAllCategoryUseCase

#### Status: ✅ PERFEITO (10/10)

```kotlin
interface GetAllCategoryUseCase {
    operator fun invoke(parameters: Unit = Unit): Flow<UiState<List<CategoryResult>>>
}

class GetAllCategoryUseCaseImpl @Inject constructor(
    private val repository: CategoryRepository
) : GetAllCategoryUseCase, FlowUseCase<Unit, List<CategoryResult>>() {
    override suspend fun executeTask(parameters: Unit): UiState<List<CategoryResult>> {
        return try {
            when (val response = repository.getAllCategory(parameters)) {
                is DomainDefaultResult.Success -> UiState.Success(response.data)
                is DomainDefaultResult.Error -> UiState.Error(Throwable(response.message))
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
```

**Avaliação:**
- ✅ Interface + Implementação
- ✅ Herda de FlowUseCase
- ✅ Retorna Flow<UiState>
- ✅ Try/catch
- ✅ **SEGUE 100% O PADRÃO**

---

### 2. ❌ CATEGORY - GetCategoriesUseCase

#### Status: ❌ FORA DO PADRÃO (3/10)

```kotlin
class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoryRepository
) {
    suspend operator fun invoke(): DefaultResult<List<CategoryResult>> {
        return when (val result = repository.getAllCategory(Unit)) {
            is DomainDefaultResult.Success -> DefaultResult.Success(result.data)
            is DomainDefaultResult.Error -> DefaultResult.Error(message = result.message)
        }
    }
}
```

**Problemas:**
- ❌ Não tem interface
- ❌ Não herda de FlowUseCase
- ❌ Retorna `DefaultResult` ao invés de `Flow<UiState>`
- ❌ Sem try/catch
- ❌ Sem Parameters

**Deveria ser:**
```kotlin
interface GetCategoriesUseCase {
    operator fun invoke(parameters: Unit = Unit): Flow<UiState<List<CategoryResult>>>
}

class GetCategoriesUseCaseImpl @Inject constructor(...) : 
    GetCategoriesUseCase, FlowUseCase<Unit, List<CategoryResult>>() { ... }
```

---

### 3. ✅ PROFILE - UpdateProfileUseCase

#### Status: ✅ PERFEITO (10/10)

```kotlin
interface UpdateProfileUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<ApiResultModel>>
    data class Parameters(val updateProfileRequestModel: UpdateProfileRequestModel)
}

class UpdateProfileUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository,
) : UpdateProfileUseCase, FlowUseCase<UpdateProfileUseCase.Parameters, ApiResultModel>() {
    override suspend fun executeTask(parameters: UpdateProfileUseCase.Parameters): UiState<ApiResultModel> {
        return try {
            when (val response = repository.updateProfile(parameters.updateProfileRequestModel)) {
                is DefaultResult.Success -> UiState.Success(response.data)
                is DefaultResult.Error -> UiState.Error(Throwable(response.message))
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
```

**Avaliação:**
- ✅ **PERFEITO - Segue 100% o padrão SignUpUseCase**

---

### 4. ⚠️ CEP - GetCepUseCase

#### Status: ⚠️ BOM MAS NÃO USA FlowUseCase (7/10)

```kotlin
interface GetCepUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<CepModel>>
    data class Parameters(val cep: String)
}

class GetCepUseCaseImpl @Inject constructor(
    private val cepRepository: CepRepository
) : GetCepUseCase {
    override fun invoke(parameters: GetCepUseCase.Parameters): Flow<UiState<CepModel>> = flow {
        emit(UiState.Loading)
        when (val result = cepRepository.getCep(parameters.cep)) {
            is DefaultResult.Success -> emit(UiState.Success(result.data))
            is DefaultResult.Error -> emit(UiState.Error(Exception(result.message)))
        }
    }
}
```

**Problemas:**
- ⚠️ Não herda de `FlowUseCase`
- ⚠️ Implementa manualmente o `flow { }` ao invés de usar `executeTask`
- ✅ Tem interface + implementação
- ✅ Tem Parameters
- ✅ Retorna Flow<UiState>

**Deveria ser:**
```kotlin
class GetCepUseCaseImpl @Inject constructor(
    private val cepRepository: CepRepository
) : GetCepUseCase, FlowUseCase<GetCepUseCase.Parameters, CepModel>() {
    override suspend fun executeTask(parameters: GetCepUseCase.Parameters): UiState<CepModel> {
        return try {
            when (val result = cepRepository.getCep(parameters.cep)) {
                is DefaultResult.Success -> UiState.Success(result.data)
                is DefaultResult.Error -> UiState.Error(Exception(result.message))
            }
        } catch (e: Exception) {
            UiState.Error(e)
        }
    }
}
```

---

### 5. ❌ SCHEDULE - DeleteScheduleUseCase

#### Status: ❌ SEM INTERFACE (5/10)

```kotlin
class DeleteScheduleUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : FlowUseCase<DeleteScheduleUseCase.Parameters, Unit>() {
    data class Parameters(val scheduleId: String)
    
    override suspend fun executeTask(parameters: Parameters): UiState<Unit> { ... }
}
```

**Problemas:**
- ❌ Não tem interface
- ✅ Herda de FlowUseCase
- ✅ Tem Parameters
- ✅ Tem try/catch (não explícito mas funcional)

---

### 6. ✅ SCHEDULE - CreateScheduleUseCase

#### Status: ✅ TEM INTERFACE (9/10)

```kotlin
interface CreateScheduleUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<Schedule>>
    data class Parameters(val schedule: Schedule)
}

class CreateScheduleUseCaseImpl @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : CreateScheduleUseCase, FlowUseCase<CreateScheduleUseCase.Parameters, Schedule>() {
    override suspend fun executeTask(parameters: CreateScheduleUseCase.Parameters): UiState<Schedule> { ... }
}
```

**Avaliação:**
- ✅ **PERFEITO - Segue o padrão SignUpUseCase**

---

### 7. ❌ SCHEDULE - GetAvailableTimeSlotsUseCase

#### Status: ❌ SEM INTERFACE (5/10)

```kotlin
class GetAvailableTimeSlotsUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : FlowUseCase<GetAvailableTimeSlotsUseCase.Parameters, List<TimeSlot>>() {
    data class Parameters(...)
    override suspend fun executeTask(parameters: Parameters): UiState<List<TimeSlot>> { ... }
}
```

**Problemas:**
- ❌ Não tem interface

---

### 8. ❌ SCHEDULE - GetProfessionalSchedulesUseCase

#### Status: ❌ SEM INTERFACE (5/10)

```kotlin
class GetProfessionalSchedulesUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : FlowUseCase<GetProfessionalSchedulesUseCase.Parameters, List<Schedule>>() {
    data class Parameters(...)
    override suspend fun executeTask(parameters: Parameters): UiState<List<Schedule>> { ... }
}
```

**Problemas:**
- ❌ Não tem interface

---

### 9. ❌ SCHEDULE - UpdateAvailabilityUseCase

#### Status: ❌ SEM INTERFACE (5/10)

```kotlin
class UpdateAvailabilityUseCase @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : FlowUseCase<UpdateAvailabilityUseCase.Parameters, Schedule>() {
    data class Parameters(...)
    override suspend fun executeTask(parameters: Parameters): UiState<Schedule> { ... }
}
```

**Problemas:**
- ❌ Não tem interface

---

### 10. ❌ SCHEDULE - CreateScheduleFromRequestUseCase

#### Status: ❌ COMPLETAMENTE FORA DO PADRÃO (2/10)

```kotlin
class CreateScheduleFromRequestUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    suspend operator fun invoke(request: CreateScheduleRequest): DefaultResult<ScheduleResponse> {
        return repository.createScheduleFromRequest(request)
    }
}
```

**Problemas:**
- ❌ Não tem interface
- ❌ Não herda de FlowUseCase
- ❌ Retorna `DefaultResult` ao invés de `Flow<UiState>`
- ❌ Sem Parameters
- ❌ Sem try/catch
- ❌ **PIOR USE CASE DE TODOS**

---

## 📈 Resumo Geral

| Use Case | Módulo | Interface | FlowUseCase | Flow<UiState> | Parameters | Try/Catch | Nota |
|----------|--------|-----------|-------------|---------------|------------|-----------|------|
| GetAllCategoryUseCase | Category | ✅ | ✅ | ✅ | ✅ | ✅ | 10/10 |
| GetCategoriesUseCase | Category | ❌ | ❌ | ❌ | ❌ | ❌ | 3/10 |
| UpdateProfileUseCase | Profile | ✅ | ✅ | ✅ | ✅ | ✅ | 10/10 |
| ValidateProfileUseCase | Profile | ✅ | ❌ | ❌ | ✅ | ✅ | 8/10 |
| GetCepUseCase | Cep | ✅ | ❌ | ✅ | ✅ | ⚠️ | 7/10 |
| GetSpecialtiesByCategoryUseCase | Specialty | ✅ | ❌ | ❌ | ❌ | ❌ | 6/10 |
| CreateScheduleUseCase | Schedule | ✅ | ✅ | ✅ | ✅ | ✅ | 10/10 |
| DeleteScheduleUseCase | Schedule | ❌ | ✅ | ✅ | ✅ | ⚠️ | 5/10 |
| GetAvailableTimeSlotsUseCase | Schedule | ❌ | ✅ | ✅ | ✅ | ⚠️ | 5/10 |
| GetProfessionalSchedulesUseCase | Schedule | ❌ | ✅ | ✅ | ✅ | ⚠️ | 5/10 |
| UpdateAvailabilityUseCase | Schedule | ❌ | ✅ | ✅ | ✅ | ⚠️ | 5/10 |
| CreateScheduleFromRequestUseCase | Schedule | ❌ | ❌ | ❌ | ❌ | ❌ | 2/10 |
| ValidateScheduleUseCase | Schedule | ✅ | ❌ | ❌ | ❌ | ❌ | 6/10 |

---

## 🔴 Use Cases que PRECISAM ser corrigidos

### 🔥 CRÍTICO (Muito fora do padrão)
1. **CreateScheduleFromRequestUseCase** (2/10) - Sem interface, sem Flow, sem FlowUseCase
2. **GetCategoriesUseCase** (3/10) - Sem interface, sem Flow, sem FlowUseCase

### ⚠️ ALTO (Falta interface)
3. **DeleteScheduleUseCase** (5/10) - Falta interface
4. **GetAvailableTimeSlotsUseCase** (5/10) - Falta interface
5. **GetProfessionalSchedulesUseCase** (5/10) - Falta interface
6. **UpdateAvailabilityUseCase** (5/10) - Falta interface

### ⚠️ MÉDIO (Não usa FlowUseCase mas tem interface)
7. **GetCepUseCase** (7/10) - Deveria herdar de FlowUseCase
8. **GetSpecialtiesByCategoryUseCase** (6/10) - JÁ FOI CORRIGIDO
9. **ValidateScheduleUseCase** (6/10) - JÁ FOI CORRIGIDO

---

## ✅ Use Cases que estão PERFEITOS

1. ✅ **GetAllCategoryUseCase** (10/10)
2. ✅ **UpdateProfileUseCase** (10/10)
3. ✅ **CreateScheduleUseCase** (10/10)

---

## 🛠️ Plano de Correção

### Total: 9 Use Cases precisam ser corrigidos
### Total: 3 Use Cases já estão perfeitos

**Próximo passo:** Corrigir TODOS os 9 use cases fora do padrão.

