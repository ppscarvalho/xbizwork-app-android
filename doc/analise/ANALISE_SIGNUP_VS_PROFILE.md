# 🔍 ANÁLISE: SignUp vs Profile - Identificação de Erros

## ❌ PROBLEMAS ENCONTRADOS NO PROFILE

### 1. **SideEffect Duplicado** ❌
```
Arquivo ERRADO criado:
📁 ui/presentation/features/profile/viewmodel/SideEffect.kt

JÁ EXISTE em:
📁 core/sideeffects/SideEffect.kt
```

### 2. **Falta TODO o fluxo Clean Architecture** ❌
```
Profile tem APENAS:
- viewmodel/EditProfileViewModel.kt
- state/EditProfileUIState.kt
- events/EditProfileEvent.kt
- views/EditProfileScreen.kt
- components/...

FALTAM:
- domain/model/UpdateProfileRequestModel.kt
- domain/model/UpdateProfileResponseModel.kt
- domain/repository/ProfileRepository.kt
- domain/usecase/UpdateProfileUseCase.kt
- domain/usecase/ValidateProfileUseCase.kt
- domain/source/ProfileRemoteDataSource.kt
- data/repository/ProfileRepositoryImpl.kt
- data/source/ProfileRemoteDataSourceImpl.kt
- data/remote/api/ProfileApiService.kt
- data/remote/api/ProfileApiServiceImpl.kt
- data/di/ProfileModule.kt
```

### 3. **ViewModel SEM UseCase** ❌
```kotlin
// ERRADO - Profile não chama UseCase
@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val getAuthSessionUseCase: GetAuthSessionUseCase, // Só pega sessão
) : ViewModel()

// CORRETO - SignUp chama UseCase
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val validateSignUpUseCase: ValidateSignUpUseCase
): ViewModel()
```

### 4. **SEM Chamada de API** ❌
```kotlin
// Profile tem TODO comentado:
private fun saveProfile() {
    // TODO: Implementar salvamento real
    viewModelScope.launch {
        // Simular delay  ← FAKE!
        kotlinx.coroutines.delay(1000)
    }
}

// SignUp CORRETO - chama API:
fun onSignUpClick(){
    viewModelScope.launch {
        signUpUseCase.invoke(
            parameters = SignUpUseCase.Parameters(
                SignUpRequestModel(...)
            )
        ).collectUiState(...)
    }
}
```

---

## ✅ ESTRUTURA CORRETA (SignUp como Referência)

### **Estrutura Completa SignUp:**

```
auth/
├── domain/
│   ├── model/
│   │   ├── SignUpRequestModel.kt
│   │   └── SignUpResultValidation.kt
│   ├── repository/
│   │   └── UserAuthRepository.kt
│   ├── source/
│   │   └── UserAuthRemoteDataSource.kt
│   └── usecase/
│       ├── SignUpUseCase.kt
│       └── ValidateSignUpUseCase.kt
├── data/
│   ├── repository/
│   │   └── UserAuthRepositoryImpl.kt
│   └── source/
│       └── UserAuthRemoteDataSourceImpl.kt
├── di/
│   └── SignUpModule.kt
└── presentation/
    └── signup/
        ├── components/
        │   ├── SignUpContent.kt
        │   └── SignUpContainer.kt
        ├── events/
        │   └── SignUpEvent.kt
        ├── navigation/
        │   └── SignUpNavigation.kt
        ├── screen/
        │   └── SignUpScreen.kt
        ├── state/
        │   └── SignUpState.kt
        └── viewmodel/
            └── SignUpViewModel.kt

API Layer (separado):
├── data/remote/auth/api/
│   ├── UserAuthApiService.kt
│   └── UserAuthApiServiceImpl.kt
└── data/remote/auth/dtos/
    ├── requests/
    │   ├── SignUpRequest.kt
    │   └── SignUpRequestModel.kt
    └── responses/
        └── SignUpResponseModel.kt
```

---

## 🎯 O QUE PRECISA SER CRIADO PARA PROFILE

### 1. **API Service**
```kotlin
// data/remote/profile/api/ProfileApiService.kt
interface ProfileApiService {
    suspend fun updateProfile(request: UpdateProfileRequest): ApiResultResponse
    suspend fun getProfile(userId: String): GetProfileResponse
}

// data/remote/profile/api/ProfileApiServiceImpl.kt
class ProfileApiServiceImpl @Inject constructor(
    private val client: HttpClient
) : ProfileApiService {
    override suspend fun updateProfile(request: UpdateProfileRequest): ApiResultResponse {
        return client.put("users/profile") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
```

### 2. **DTOs (Request/Response)**
```kotlin
// data/remote/profile/dtos/requests/UpdateProfileRequest.kt
data class UpdateProfileRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phoneNumber") val phoneNumber: String?,
    @SerializedName("bio") val bio: String?,
    @SerializedName("profilePhoto") val profilePhoto: String?
)

// data/remote/profile/dtos/responses/GetProfileResponse.kt
data class GetProfileResponse(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    ...
)
```

### 3. **Domain Models**
```kotlin
// domain/model/profile/UpdateProfileRequestModel.kt
data class UpdateProfileRequestModel(
    val name: String,
    val email: String,
    val phoneNumber: String?,
    val bio: String?,
    val profilePhotoUri: Uri?
)

// domain/model/profile/ProfileResultValidation.kt
enum class ProfileResultValidation {
    EmptyName,
    InvalidEmail,
    InvalidPhone,
    NameTooShort,
    Valid
}
```

### 4. **Repository (Interface e Implementação)**
```kotlin
// domain/repository/ProfileRepository.kt
interface ProfileRepository {
    suspend fun updateProfile(model: UpdateProfileRequestModel): DefaultResult<ApiResultModel>
    suspend fun getProfile(userId: String): DefaultResult<ProfileModel>
}

// data/repository/ProfileRepositoryImpl.kt
class ProfileRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProfileRemoteDataSource
): ProfileRepository {
    override suspend fun updateProfile(...): DefaultResult<ApiResultModel> {
        return remoteDataSource.updateProfile(...)
    }
}
```

### 5. **RemoteDataSource (Interface e Implementação)**
```kotlin
// domain/source/ProfileRemoteDataSource.kt
interface ProfileRemoteDataSource {
    suspend fun updateProfile(model: UpdateProfileRequestModel): DefaultResult<ApiResultModel>
}

// data/source/ProfileRemoteDataSourceImpl.kt
class ProfileRemoteDataSourceImpl @Inject constructor(
    private val apiService: ProfileApiService
): ProfileRemoteDataSource {
    override suspend fun updateProfile(...): DefaultResult<ApiResultModel> {
        return try {
            val request = model.toRequest()
            val response = apiService.updateProfile(request)
            DefaultResult.Success(response.toModel())
        } catch (e: Exception) {
            DefaultResult.Error(e.message ?: "Unknown error")
        }
    }
}
```

### 6. **UseCases**
```kotlin
// domain/usecase/UpdateProfileUseCase.kt
interface UpdateProfileUseCase {
    operator fun invoke(parameters: Parameters): Flow<UiState<ApiResultModel>>
    
    data class Parameters(
        val updateProfileRequestModel: UpdateProfileRequestModel
    )
}

class UpdateProfileUseCaseImpl @Inject constructor(
    private val repository: ProfileRepository,
    private val dispatcher: CoroutineDispatcherProvider
) : UpdateProfileUseCase, FlowUseCase<UpdateProfileUseCase.Parameters, ApiResultModel>() {
    override suspend fun executeTask(parameters: Parameters): UiState<ApiResultModel> {
        return withContext(dispatcher.io()) {
            when (val result = repository.updateProfile(parameters.updateProfileRequestModel)) {
                is DefaultResult.Success -> UiState.Success(result.data)
                is DefaultResult.Error -> UiState.Error(Throwable(result.message))
            }
        }
    }
}

// domain/usecase/ValidateProfileUseCase.kt
interface ValidateProfileUseCase {
    operator fun invoke(
        name: String,
        email: String,
        phone: String?
    ): ProfileResultValidation
}

class ValidateProfileUseCaseImpl : ValidateProfileUseCase {
    override fun invoke(name: String, email: String, phone: String?): ProfileResultValidation {
        if (name.isBlank()) return ProfileResultValidation.EmptyName
        if (name.length < 3) return ProfileResultValidation.NameTooShort
        if (!email.contains("@")) return ProfileResultValidation.InvalidEmail
        if (phone != null && phone.length != 11) return ProfileResultValidation.InvalidPhone
        return ProfileResultValidation.Valid
    }
}
```

### 7. **Dependency Injection**
```kotlin
// data/di/ProfileModule.kt
@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {
    
    @Provides
    @Singleton
    fun provideProfileApiService(client: HttpClient): ProfileApiService {
        return ProfileApiServiceImpl(client)
    }
    
    @Provides
    @Singleton
    fun provideProfileRemoteDataSource(apiService: ProfileApiService): ProfileRemoteDataSource {
        return ProfileRemoteDataSourceImpl(apiService)
    }
    
    @Provides
    @Singleton
    fun provideProfileRepository(remoteDataSource: ProfileRemoteDataSource): ProfileRepository {
        return ProfileRepositoryImpl(remoteDataSource)
    }
    
    @Provides
    @Singleton
    fun provideUpdateProfileUseCase(
        repository: ProfileRepository,
        dispatcher: CoroutineDispatcherProvider
    ): UpdateProfileUseCase {
        return UpdateProfileUseCaseImpl(repository, dispatcher)
    }
    
    @Provides
    @Singleton
    fun provideValidateProfileUseCase(): ValidateProfileUseCase {
        return ValidateProfileUseCaseImpl()
    }
}
```

### 8. **ViewModel CORRETO**
```kotlin
// viewmodel/EditProfileViewModel.kt
@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val validateProfileUseCase: ValidateProfileUseCase,
    private val getAuthSessionUseCase: GetAuthSessionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUIState())
    val uiState: StateFlow<EditProfileUIState> = _uiState.asStateFlow()

    private val _sideEffectChannel = Channel<SideEffect>(capacity = Channel.Factory.BUFFERED)
    val sideEffectChannel = _sideEffectChannel.receiveAsFlow()

    init {
        loadUserProfile()
    }

    fun onEvent(event: EditProfileEvent) {
        when (event) {
            is EditProfileEvent.OnNameChanged -> {
                _uiState.update { it.copy(name = event.name) }
                validateForm()
            }
            is EditProfileEvent.OnEmailChanged -> {
                _uiState.update { it.copy(email = event.email) }
                validateForm()
            }
            is EditProfileEvent.OnPhoneChanged -> {
                _uiState.update { it.copy(phoneNumber = event.phone) }
                validateForm()
            }
            is EditProfileEvent.OnBioChanged -> {
                _uiState.update { it.copy(bio = event.bio) }
            }
            is EditProfileEvent.OnPhotoSelected -> {
                _uiState.update { it.copy(profilePhotoUri = event.uri, hasChanges = true) }
            }
            EditProfileEvent.OnPhotoRemove -> {
                _uiState.update { it.copy(profilePhotoUri = null, hasChanges = true) }
            }
            EditProfileEvent.OnSaveClick -> onUpdateProfile()
        }
    }

    private fun loadUserProfile() {
        viewModelScope.launch {
            getAuthSessionUseCase.invoke().collect { session ->
                _uiState.update {
                    it.copy(
                        name = session.name,
                        email = session.email
                    )
                }
            }
        }
    }

    private fun validateForm() {
        val state = _uiState.value
        val validation = validateProfileUseCase(
            name = state.name,
            email = state.email,
            phone = state.phoneNumber
        )

        val (nameError, emailError, phoneError, isValid) = when (validation) {
            ProfileResultValidation.EmptyName -> Triple("Nome é obrigatório", null, null, false)
            ProfileResultValidation.NameTooShort -> Triple("Nome muito curto", null, null, false)
            ProfileResultValidation.InvalidEmail -> Triple(null, "Email inválido", null, false)
            ProfileResultValidation.InvalidPhone -> Triple(null, null, "Telefone inválido", false)
            ProfileResultValidation.Valid -> Triple(null, null, null, true)
        }

        _uiState.update {
            it.copy(
                nameError = nameError,
                emailError = emailError,
                phoneError = phoneError,
                isFormValid = isValid,
                hasChanges = true
            )
        }
    }

    private fun onUpdateProfile() {
        viewModelScope.launch {
            val state = _uiState.value
            updateProfileUseCase.invoke(
                parameters = UpdateProfileUseCase.Parameters(
                    UpdateProfileRequestModel(
                        name = state.name.trim(),
                        email = state.email.trim(),
                        phoneNumber = state.phoneNumber.takeIf { it.isNotBlank() },
                        bio = state.bio.takeIf { it.isNotBlank() },
                        profilePhotoUri = state.profilePhotoUri
                    )
                )
            ).collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = { response ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isSuccess = response.isSuccessful,
                            successMessage = response.message
                        )
                    }
                    _sideEffectChannel.send(SideEffect.ShowToast(response.message))
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                    _sideEffectChannel.send(SideEffect.ShowToast(error.message ?: "Erro ao atualizar"))
                }
            )
        }
    }
}
```

---

## 📝 PLANO DE AÇÃO

### 1. **Deletar arquivos duplicados/errados** ❌
```
- profile/viewmodel/SideEffect.kt
```

### 2. **Criar estrutura de pastas**
```
profile/
├── domain/
│   ├── model/
│   ├── repository/
│   ├── source/
│   └── usecase/
├── data/
│   ├── repository/
│   ├── source/
│   └── remote/
│       ├── api/
│       └── dtos/
└── di/
```

### 3. **Criar todos os arquivos seguindo SignUp**
- API Service
- DTOs
- Domain Models
- Repository
- DataSource
- UseCases
- Module DI
- Atualizar ViewModel

---

**Próximo passo:** Deletar e recriar tudo corretamente!

