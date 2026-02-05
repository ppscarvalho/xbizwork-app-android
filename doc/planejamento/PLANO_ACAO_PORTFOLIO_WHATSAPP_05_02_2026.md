# 📋 PLANO DE AÇÃO - Portfólio Visual e Contato WhatsApp

**Data**: 05/02/2026  
**Branch**: `feature/melhorias-visualizacao-perfil-usuario`  
**Objetivo**: MVP Visual - Portfólio com imagens mock + WhatsApp  
**Status**: ⏳ AGUARDANDO REVISÃO E APROVAÇÃO

---

## 🎯 ESCOPO (MVP Visual)

### ✅ O que VAI ser feito:
1. ✅ Adicionar portfólio visual com carrossel (imagens mock)
2. ✅ Reutilizar componente de carrossel existente (SEM autoplay)
3. ✅ Integrar WhatsApp no botão "Contactar"
4. ✅ Usar imagens da pasta `drawable` (mock)
5. ✅ Seguir RIGOROSAMENTE o padrão existente

### ❌ O que NÃO será feito:
- ❌ Backend / API
- ❌ Upload de imagens
- ❌ Firebase / Firestore
- ❌ Atualizar/baixar bibliotecas
- ❌ Criar padrões novos

---

## 📌 CONTEXTO

Atualmente, a tela **Perfil do Profissional** exibe apenas:
- Nome e especialidade do profissional
- Telefone
- Localização (cidade/estado)
- Botão "Contactar"

### Screenshot Atual:
```
┌─────────────────────────────────┐
│  ← Perfil do Profissional      │
├─────────────────────────────────┤
│ 👤 Pedro Carvalho              │
│    Educador Físico             │
├─────────────────────────────────┤
│ Informações de Contato         │
│ 📱 (91) 99999-9999             │
│ 📍 Belém - PA                  │
├─────────────────────────────────┤
│                                 │
│     (espaço vazio)             │
│                                 │
├─────────────────────────────────┤
│    📞 Contactar                │
└─────────────────────────────────┘
```

### Layout Desejado (Após Implementação):
```
┌─────────────────────────────────┐
│  ← Perfil do Profissional      │
├─────────────────────────────────┤
│ 👤 Pedro Carvalho              │
│    Educador Físico             │
├─────────────────────────────────┤
│ Informações de Contato         │
│ 📱 (91) 99999-9999             │
│ 📍 Belém - PA                  │
├─────────────────────────────────┤
│ **Trabalhos realizados**       │ ← NOVO
│ [🖼️] [🖼️] [🖼️]               │ ← NOVO (carrossel)
├─────────────────────────────────┤
│    📞 Contactar                │ ← Abre WhatsApp
└─────────────────────────────────┘
```

---

## 📦 ARQUIVOS A SEREM CRIADOS/MODIFICADOS

### 1. **PortfolioCarousel.kt** (🆕 CRIAR)
**Localização**: `ui/presentation/components/carousel/PortfolioCarousel.kt`

**Descrição**: Componente de carrossel baseado em `AutoScrollingCarousel.kt`, mas **SEM autoplay**.

**Diferenças do AutoScrollingCarousel**:
- ❌ Remove `LaunchedEffect` de autoplay
- ✅ Mantém LazyRow com scroll manual
- ✅ Mantém indicadores de página
- ✅ Usa recursos de imagem (drawable)

**Estrutura**:
```kotlin
@Composable
fun PortfolioCarousel(
    modifier: Modifier = Modifier,
    images: List<Int>, // Resource IDs das imagens
    imageHeight: Dp = 200.dp,
    itemWidthFraction: Float = 0.90f,
    itemSpacing: Dp = 12.dp
) {
    // LazyRow com scroll manual
    // Box com Image e Card
    // Indicadores de página (dots)
}
```

---

### 2. **ProfessionalProfileContainer.kt** (✏️ MODIFICAR)
**Localização**: `ui/presentation/features/professionalprofile/components/ProfessionalProfileContainer.kt`

**Mudanças**:
```kotlin
Column(...) {
    ProfessionalProfileHeader(professional)
    
    ProfessionalProfileContactInfo(professional)
    
    // 🆕 ADICIONAR: Seção de Portfólio
    Spacer(modifier = Modifier.height(16.dp))
    
    Text(
        text = "Trabalhos realizados",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        fontFamily = poppinsFontFamily
    )
    
    Spacer(modifier = Modifier.height(8.dp))
    
    PortfolioCarousel(
        images = getPortfolioImages(professional.skill.name)
    )
    
    Spacer(modifier = Modifier.weight(1f))
    
    Button(onClick = { onEvent(OnContactClick) }) { ... }
}

// Helper function
private fun getPortfolioImages(skillName: String): List<Int> {
    return when {
        skillName.contains("Educador", ignoreCase = true) -> listOf(
            R.drawable.educador_1,
            R.drawable.educador_2,
            R.drawable.educador_3
        )
        skillName.contains("Manicure", ignoreCase = true) -> listOf(
            R.drawable.nanicure_1,
            R.drawable.nanicure_2,
            R.drawable.nanicure_3
        )
        else -> listOf(
            R.drawable.educador_1,
            R.drawable.educador_2
        )
    }
}
```

---

### 3. **ProfessionalProfileViewModel.kt** (✏️ MODIFICAR)
**Localização**: `ui/presentation/features/professionalprofile/viewmodel/ProfessionalProfileViewModel.kt`

**Mudanças**:

#### a) Adicionar dependência de AuthSession:
```kotlin
@HiltViewModel
class ProfessionalProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAuthSessionUseCase: GetAuthSessionUseCase // 🆕 ADICIONAR
) : ViewModel() {
    
    private val _userName = MutableStateFlow("")
    
    init {
        observeAuthSession() // 🆕 ADICIONAR
    }
    
    // 🆕 ADICIONAR
    private fun observeAuthSession() {
        viewModelScope.launch {
            getAuthSessionUseCase.invoke().collect { authSession ->
                _userName.value = authSession.name
            }
        }
    }
}
```

#### b) Implementar abertura do WhatsApp:
```kotlin
private fun handleContactClick() {
    val professional = _uiState.value.professional
    val userName = _userName.value.ifEmpty { "Usuário" }
    
    if (professional != null) {
        logInfo("PROFESSIONAL_PROFILE_VM", "📱 Abrindo WhatsApp...")
        
        val message = "Olá, me chamo $userName. " +
                      "Encontrei seu perfil no aplicativo e gostaria de " +
                      "conversar sobre um trabalho de ${professional.skill.name.lowercase()}."
        
        val phone = professional.mobilePhone.replace(Regex("[^0-9]"), "")
        val whatsappUrl = "https://wa.me/55$phone?text=${Uri.encode(message)}"
        
        // Enviar SideEffect para abrir WhatsApp
        viewModelScope.launch {
            _sideEffectChannel.send(
                AppSideEffect.OpenExternalUrl(whatsappUrl)
            )
        }
    }
}
```

---

### 4. **AppSideEffect.kt** (✏️ MODIFICAR - SE NÃO EXISTIR)
**Localização**: `core/sideeffects/AppSideEffect.kt`

**Adicionar**:
```kotlin
sealed class AppSideEffect {
    // ...existing code...
    
    // 🆕 ADICIONAR (se não existir)
    data class OpenExternalUrl(val url: String) : AppSideEffect()
}
```

---

### 5. **ProfessionalProfileScreen.kt** (✏️ MODIFICAR)
**Localização**: `ui/presentation/features/professionalprofile/screen/ProfessionalProfileScreen.kt`

**Adicionar tratamento do SideEffect**:
```kotlin
@Composable
fun ProfessionalProfileScreen(
    uiState: ProfessionalProfileUiState,
    sideEffectFlow: Flow<AppSideEffect>, // 🆕 ADICIONAR
    onEvent: (ProfessionalProfileEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current
    
    // 🆕 ADICIONAR
    LifecycleEventEffect(sideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.OpenExternalUrl -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(sideEffect.url))
                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(
                        context,
                        "Erro ao abrir WhatsApp. Certifique-se de que está instalado.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            // ...other side effects...
        }
    }
    
    Scaffold(...) { ... }
}
```

---

### 6. **ProfessionalProfileNavigation.kt** (✏️ MODIFICAR)
**Localização**: `ui/presentation/features/professionalprofile/navigation/ProfessionalProfileNavigation.kt`

**Passar sideEffectFlow**:
```kotlin
fun NavGraphBuilder.professionalProfileScreen(...) {
    composable<MenuScreens.ProfessionalProfileScreen> { backStackEntry ->
        val viewModel: ProfessionalProfileViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        val sideEffectFlow = viewModel.sideEffectChannel // 🆕 ADICIONAR
        
        // ...existing code...
        
        ProfessionalProfileScreen(
            uiState = uiState,
            sideEffectFlow = sideEffectFlow, // 🆕 ADICIONAR
            onEvent = viewModel::onEvent,
            onNavigateBack = onNavigateUp
        )
    }
}
```

---

### 7. **ImageAssets.kt** (✏️ VERIFICAR/ADICIONAR)
**Localização**: `ui/presentation/common/ImageAssets.kt`

**Adicionar referências**:
```kotlin
object ImageAssets {
    // ...existing code...
    
    // 🆕 ADICIONAR - Portfolio Mock Images
    const val PORTFOLIO_EDUCADOR_1 = R.drawable.educador_1
    const val PORTFOLIO_EDUCADOR_2 = R.drawable.educador_2
    const val PORTFOLIO_EDUCADOR_3 = R.drawable.educador_3
    const val PORTFOLIO_NANICURE_1 = R.drawable.nanicure_1
    const val PORTFOLIO_NANICURE_2 = R.drawable.nanicure_2
    const val PORTFOLIO_NANICURE_3 = R.drawable.nanicure_3
}
```

---

### 8. **Imagens no drawable** (✅ VERIFICAR)
**Localização**: `app/src/main/res/drawable/`

**Imagens necessárias**:
- `educador_1.webp`
- `educador_2.webp`
- `educador_3.webp`
- `nanicure_1.webp` (ou `manicure_1.webp`)
- `nanicure_2.webp`
- `nanicure_3.webp`

**Ação**: Verificar se existem. Se não, usar imagens placeholder existentes.

---

## 🔧 IMPLEMENTAÇÃO - ORDEM DE EXECUÇÃO

### **FASE 1: Preparação**
1. ✅ Verificar imagens no drawable
2. ✅ Atualizar ImageAssets.kt

### **FASE 2: Componente de Carrossel**
3. ✅ Criar `PortfolioCarousel.kt`
4. ✅ Testar componente isoladamente (Preview)

### **FASE 3: Integração no Perfil**
5. ✅ Modificar `ProfessionalProfileContainer.kt`
6. ✅ Adicionar seção "Trabalhos realizados"
7. ✅ Integrar PortfolioCarousel

### **FASE 4: WhatsApp**
8. ✅ Adicionar `OpenExternalUrl` em AppSideEffect
9. ✅ Implementar `handleContactClick` no ViewModel
10. ✅ Adicionar observação de AuthSession
11. ✅ Tratar SideEffect na Screen
12. ✅ Atualizar Navigation

### **FASE 5: Testes e Ajustes**
13. ✅ Compilar e testar
14. ✅ Validar layout visual
15. ✅ Testar abertura do WhatsApp
16. ✅ Ajustes finais de espaçamento

---

## 📐 ESPECIFICAÇÕES TÉCNICAS

### **PortfolioCarousel**
```kotlin
// Parâmetros
- images: List<Int> (resource IDs)
- imageHeight: Dp = 200.dp
- itemWidthFraction: Float = 0.90f (90% da largura)
- itemSpacing: Dp = 12.dp

// Comportamento
- Scroll MANUAL (sem autoplay)
- Indicadores de página (dots)
- Imagem centralizada com Card
- cornerRadius = 12.dp
- elevation = 4.dp
```

### **Mensagem WhatsApp**
```
Template:
"Olá, me chamo {userName}. 
Encontrei seu perfil no aplicativo e gostaria de 
conversar sobre um trabalho de {skillName}."

Exemplo real:
"Olá, me chamo Pedro Carvalho. 
Encontrei seu perfil no aplicativo e gostaria de 
conversar sobre um trabalho de educador físico."
```

### **Formato do Número WhatsApp**
```kotlin
// Entrada: "(91) 99999-9999"
// Processamento: remove caracteres não numéricos
val phone = mobilePhone.replace(Regex("[^0-9]"), "")
// Resultado: "91999999999"

// URL: https://wa.me/5591999999999?text={mensagem}
```

---

## ✅ CRITÉRIOS DE ACEITE

- [ ] **Portfólio exibido** abaixo de "Informações de Contato"
- [ ] **Carrossel funciona** apenas com swipe manual (SEM autoplay)
- [ ] **Usa imagens mock** da pasta `drawable`
- [ ] **Botão "Contactar" abre WhatsApp** com mensagem personalizada
- [ ] **Mensagem contém nome do usuário** logado
- [ ] **Layout consistente** com o resto do app
- [ ] **Código segue padrão existente** (AutoScrollingCarousel, etc.)
- [ ] **Nenhuma biblioteca adicionada/atualizada**
- [ ] **Compila sem erros**
- [ ] **Preview funciona** no Android Studio

---

## 📊 ESTRUTURA DE COMMITS

1. `feat: criar componente PortfolioCarousel sem autoplay`
2. `feat: adicionar seção "Trabalhos realizados" no perfil profissional`
3. `feat: integrar abertura do WhatsApp no botão Contactar`
4. `feat: adicionar mensagem personalizada com nome do usuário`
5. `docs: atualizar documentação do fluxo de perfil profissional`

---

## 🧪 TESTES NECESSÁRIOS

### **Teste 1: Carrossel**
- [ ] Imagens aparecem corretamente
- [ ] Scroll manual funciona
- [ ] Indicadores de página funcionam
- [ ] Não há autoplay

### **Teste 2: WhatsApp**
- [ ] Botão abre WhatsApp
- [ ] Número está no formato correto
- [ ] Mensagem aparece preenchida
- [ ] Nome do usuário está correto
- [ ] Trata erro se WhatsApp não está instalado

### **Teste 3: Layout**
- [ ] Seção "Trabalhos realizados" posicionada corretamente
- [ ] Espaçamentos consistentes
- [ ] Botão "Contactar" no final da tela
- [ ] Scroll funciona corretamente

---

## 🔄 FLUXO COMPLETO

```
1. Usuário navega para Perfil do Profissional
   ↓
2. Tela carrega dados do profissional
   ↓
3. Exibe Header + Contato + Portfólio
   ↓
4. Usuário visualiza imagens do portfólio (swipe manual)
   ↓
5. Usuário clica em "Contactar"
   ↓
6. ViewModel busca nome do usuário (AuthSession)
   ↓
7. ViewModel formata mensagem WhatsApp
   ↓
8. ViewModel envia SideEffect OpenExternalUrl
   ↓
9. Screen recebe SideEffect
   ↓
10. Screen abre WhatsApp via Intent
    ↓
11. WhatsApp abre com mensagem preenchida
```

---

## 📝 NOTAS TÉCNICAS

### **Reutilização de Código**
- ✅ `AutoScrollingCarousel` serve de base para `PortfolioCarousel`
- ✅ `CarouselItemView` pode ser reutilizado
- ✅ `AppSideEffect` já existe (apenas adicionar novo tipo)
- ✅ `GetAuthSessionUseCase` já existe

### **Dependências Injetadas**
```kotlin
ProfessionalProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle, // ✅ Já existe
    private val getAuthSessionUseCase: GetAuthSessionUseCase // 🆕 Adicionar
)
```

### **Imports Necessários**
```kotlin
import android.content.Intent
import android.net.Uri
import androidx.core.net.toUri
```

---

## 🚨 ATENÇÕES ESPECIAIS

1. **NÃO modificar `AutoScrollingCarousel.kt`** (criar novo componente)
2. **NÃO adicionar bibliotecas** (usar Intent nativo do Android)
3. **NÃO fazer chamadas de API** (tudo local/mock)
4. **Tratar erro** se WhatsApp não estiver instalado
5. **Verificar se imagens existem** antes de referenciar

---

## 📚 REFERÊNCIAS

### **Arquivos de Referência**:
- `AutoScrollingCarousel.kt` - Base para o carrossel
- `CarouselContainer.kt` - Como usar o carrossel
- `ProfessionalProfileContainer.kt` - Estrutura atual
- `ProfessionalProfileViewModel.kt` - Lógica de negócio
- `GetAuthSessionUseCase.kt` - Buscar dados do usuário

### **Padrões Seguidos**:
- Clean Architecture (UI → ViewModel → UseCase → Repository)
- Compose best practices
- Material Design 3
- Side Effects para ações externas

---

**Status**: ⏳ AGUARDANDO REVISÃO E APROVAÇÃO  
**Próximo Passo**: Revisão do plano e início da implementação
