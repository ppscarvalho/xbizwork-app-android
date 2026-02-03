# 📋 PLANO DE AÇÃO - 03/02/2026

**Data**: 03/02/2026 (Hoje)  
**Status**: 📝 Planejado | ✅ AuthBottomSheet Refatorado  
**Prioridade**: 🔴 Alta

---

## ⚠️ ATUALIZAÇÃO IMPORTANTE

### ✅ REFATORAÇÃO DO AUTHBOTTOMSHEET CONCLUÍDA

**Motivo**: Código anterior estava FORA DO PADRÃO do projeto

**Problema identificado**:
- Tudo em um único arquivo monolítico
- ViewModel injetado diretamente no composable
- Sem separação State/Events/ViewModel/Container/Content
- Não seguia padrão SignUp, Profile, Schedule

**Solução implementada**:
- ✅ 6 arquivos criados seguindo padrão
- ✅ State, Events, ViewModel, Container, Content, Screen
- ✅ ViewModel gerenciado externamente
- ✅ Código testável e manutenível
- ✅ Documentação completa
- ✅ Commit realizado

**Arquivos**:
```
features/auth/bottomsheet/
  ├── state/AuthBottomSheetState.kt
  ├── events/AuthBottomSheetEvent.kt
  ├── viewmodel/AuthBottomSheetViewModel.kt
  ├── components/
  │   ├── AuthBottomSheetContainer.kt
  │   └── AuthBottomSheetContent.kt
  └── screen/AuthBottomSheetScreen.kt
```

**Documentação**: `REFATORACAO_AUTH_BOTTOMSHEET_PADRAO.md`

---

## 🎯 OBJETIVOS DO DIA

### 1. 🐛 CORRIGIR BUG CRÍTICO - Crash na Busca (PRIORIDADE MÁXIMA)
### 2. 🎨 IMPLEMENTAR Fluxo de Assinatura de Plano

---

## ⏰ CRONOGRAMA

| Horário | Atividade | Duração |
|---------|-----------|---------|
| **Manhã** | | |
| 09:00 - 09:30 | ☕ Preparação + Review das análises | 30 min |
| 09:30 - 10:00 | 🐛 Correção do Bug de Crash | 30 min |
| 10:00 - 10:15 | 🧪 Testes extensivos do bug | 15 min |
| **Coffee Break** | | 15 min |
| 10:30 - 11:30 | 🎨 Implementação do Plano (Parte 1) | 60 min |
| **Almoço** | | 60 min |
| **Tarde** | | |
| 13:00 - 14:00 | 🎨 Implementação do Plano (Parte 2) | 60 min |
| 14:00 - 14:30 | 🧪 Testes finais | 30 min |
| 14:30 - 15:00 | 📝 Documentação + Commit | 30 min |

**TOTAL**: ~4h de trabalho efetivo

---

## 🐛 TAREFA 1: CORRIGIR BUG DE CRASH (30 min)

### Problema
App fecha ao: Login → Mapa → Home → Buscar novamente

### Causa Provável
`observeAuthSession()` em `SearchProfessionalsViewModel` cria múltiplos collectors

### Correção

#### PASSO 1: Adicionar Logs (5 min)
```kotlin
// SearchProfessionalsViewModel.kt

init {
    logInfo("SEARCH_VM", "🟢 ViewModel CRIADO - hash: ${this.hashCode()}")
    observeAuthSession()
}

private fun observeAuthSession() {
    logInfo("SEARCH_VM", "🔵 Iniciando observeAuthSession")
    viewModelScope.launch {
        getAuthSessionUseCase.invoke().collect { authSession ->
            logInfo("SEARCH_VM", "🟡 Sessão: token presente = ${authSession.token.isNotEmpty()}")
            _uiState.update {
                it.copy(isAuthenticated = authSession.token.isNotEmpty())
            }
        }
    }
}

override fun onCleared() {
    logInfo("SEARCH_VM", "🔴 ViewModel DESTRUÍDO - hash: ${this.hashCode()}")
    super.onCleared()
}
```

#### PASSO 2: Implementar stateIn (15 min)
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
                _uiState.update {
                    it.copy(isAuthenticated = session.token.isNotEmpty())
                }
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            logInfo("SEARCH_VM", "❌ Erro ao observar sessão: ${e.message}")
            e.printStackTrace()
        }
    }
}
```

#### PASSO 3: Verificar Navigation (5 min)
```kotlin
// SearchProfessionalBySkillNavigation.kt
fun NavController.navigateToSearchProfessionalBySkillScreen() {
    navigate(MenuScreens.SearchProfessionalBySkillScreen) {
        launchSingleTop = true  // ← Garantir instância única
        restoreState = true
    }
}
```

#### PASSO 4: Testar (5 min)
- Login → Mapa → Home → Buscar (3x)
- Verificar logs
- Confirmar que não crasha

### Arquivo a Modificar
- `SearchProfessionalsViewModel.kt`

---

## 🎨 TAREFA 2: IMPLEMENTAR FLUXO DE PLANO (2h)

### Status Atual
- ✅ Backend 100% pronto
- ✅ Components 100% prontos
- ❌ Screen, ViewModel, State, Events, Navigation - FALTANDO

### Implementação

#### FASE 1: State + Events (10 min)

**Criar: `PlanUiState.kt`**
```kotlin
package com.br.xbizitwork.ui.presentation.features.plans.state

import com.br.xbizitwork.domain.model.plan.PlanModel

data class PlanUiState(
    val plans: List<PlanModel> = emptyList(),
    val selectedPlan: PlanModel? = null,
    val isLoading: Boolean = false,
    val isSubscribing: Boolean = false,
    val errorMessage: String? = null,
    val subscriptionSuccess: Boolean = false
)
```

**Criar: `PlanEvent.kt`**
```kotlin
package com.br.xbizitwork.ui.presentation.features.plans.events

import com.br.xbizitwork.domain.model.plan.PlanModel

sealed class PlanEvent {
    data object OnRefresh : PlanEvent()
    data class OnPlanSelected(val plan: PlanModel) : PlanEvent()
    data class OnSubscribeClick(val planId: Int) : PlanEvent()
}
```

#### FASE 2: ViewModel (20 min)

**Criar: `PlanViewModel.kt`**
```kotlin
package com.br.xbizitwork.ui.presentation.features.plans.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.core.util.logging.logInfo
import com.br.xbizitwork.domain.usecase.plan.GetAllPlanUseCase
import com.br.xbizitwork.ui.presentation.features.plans.events.PlanEvent
import com.br.xbizitwork.ui.presentation.features.plans.state.PlanUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val getAllPlanUseCase: GetAllPlanUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlanUiState())
    val uiState = _uiState.asStateFlow()

    private val _sideEffectChannel = Channel<AppSideEffect>(capacity = Channel.BUFFERED)
    val sideEffectChannel = _sideEffectChannel.receiveAsFlow()

    init {
        loadPlans()
    }

    fun onEvent(event: PlanEvent) {
        when (event) {
            is PlanEvent.OnRefresh -> loadPlans()
            is PlanEvent.OnPlanSelected -> selectPlan(event.plan)
            is PlanEvent.OnSubscribeClick -> subscribeToPlan(event.planId)
        }
    }

    private fun loadPlans() {
        viewModelScope.launch {
            getAllPlanUseCase.invoke().collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onSuccess = { plans ->
                    logInfo("PLAN_VM", "✅ ${plans.size} planos carregados")
                    _uiState.update {
                        it.copy(
                            plans = plans,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                },
                onFailure = { error ->
                    logInfo("PLAN_VM", "❌ Erro ao carregar planos: ${error.message}")
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message
                        )
                    }
                }
            )
        }
    }

    private fun selectPlan(plan: PlanModel) {
        _uiState.update { it.copy(selectedPlan = plan) }
    }

    private fun subscribeToPlan(planId: Int) {
        viewModelScope.launch {
            // TODO: Implementar quando API de assinatura existir
            _uiState.update { it.copy(isSubscribing = true) }
            
            // Simulação de sucesso (remover quando API existir)
            kotlinx.coroutines.delay(1500)
            
            _uiState.update {
                it.copy(
                    isSubscribing = false,
                    subscriptionSuccess = true
                )
            }
            
            _sideEffectChannel.send(
                AppSideEffect.ShowToast("Plano assinado com sucesso! (Simulado)")
            )
        }
    }
}
```

#### FASE 3: Screen (30 min)

**Criar: `PlanScreen.kt`**
```kotlin
package com.br.xbizitwork.ui.presentation.features.plans.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.br.xbizitwork.core.sideeffects.AppSideEffect
import com.br.xbizitwork.core.state.LifecycleEventEffect
import com.br.xbizitwork.core.util.extensions.toast
import com.br.xbizitwork.ui.presentation.components.topbar.AppTopBar
import com.br.xbizitwork.ui.presentation.features.plans.components.PlanContent
import com.br.xbizitwork.ui.presentation.features.plans.events.PlanEvent
import com.br.xbizitwork.ui.presentation.features.plans.state.PlanUiState
import kotlinx.coroutines.flow.Flow

@Composable
fun PlanScreen(
    uiState: PlanUiState,
    appSideEffectFlow: Flow<AppSideEffect>,
    onEvent: (PlanEvent) -> Unit,
    onNavigateBack: () -> Unit
) {
    val context = LocalContext.current

    // Tratar SideEffects
    LifecycleEventEffect(appSideEffectFlow) { sideEffect ->
        when (sideEffect) {
            is AppSideEffect.ShowToast -> context.toast(sideEffect.message)
            else -> {}
        }
    }

    // Refresh ao iniciar
    LaunchedEffect(Unit) {
        onEvent(PlanEvent.OnRefresh)
    }

    Scaffold(
        topBar = {
            AppTopBar(
                isHomeMode = false,
                title = "Escolha Seu Plano",
                navigationImageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                enableNavigationUp = true,
                onNavigationIconButton = onNavigateBack
            )
        },
        content = { paddingValues ->
            PlanContent(
                paddingValues = paddingValues,
                uiState = uiState,
                onEvent = onEvent
            )
        }
    )
}
```

**Criar: `PlanContent.kt`** (Adaptar PlanContainer para ser dinâmico)
```kotlin
package com.br.xbizitwork.ui.presentation.features.plans.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.ui.presentation.components.background.AppGradientBackground
import com.br.xbizitwork.ui.presentation.components.loading.LoadingIndicator
import com.br.xbizitwork.ui.presentation.features.plans.events.PlanEvent
import com.br.xbizitwork.ui.presentation.features.plans.state.PlanUiState

@Composable
fun PlanContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    uiState: PlanUiState,
    onEvent: (PlanEvent) -> Unit
) {
    AppGradientBackground(
        modifier = modifier,
        paddingValues = paddingValues
    ) {
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingIndicator(message = "Carregando planos...")
                }
            }
            
            uiState.errorMessage != null -> {
                // TODO: Criar ErrorState component
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = uiState.errorMessage)
                }
            }
            
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    uiState.plans.forEach { plan ->
                        PlanCard(
                            planLabel = plan.description,
                            planDescription = "Duração: ${plan.duration} dias - R$ ${plan.price}",
                            isLoading = uiState.isSubscribing,
                            buttonEnabled = !uiState.isSubscribing && plan.isActive,
                            onClick = {
                                onEvent(PlanEvent.OnSubscribeClick(plan.id))
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}
```

#### FASE 4: Navigation (15 min)

**Criar: `PlanNavigation.kt`**
```kotlin
package com.br.xbizitwork.ui.presentation.features.plans.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.br.xbizitwork.ui.presentation.features.plans.screen.PlanScreen
import com.br.xbizitwork.ui.presentation.features.plans.viewmodel.PlanViewModel
import com.br.xbizitwork.ui.presentation.navigation.screens.MenuScreens

fun NavGraphBuilder.planScreen(
    onNavigateUp: () -> Unit
) {
    composable<MenuScreens.PlanScreen> {
        val viewModel: PlanViewModel = hiltViewModel()
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        PlanScreen(
            uiState = uiState,
            appSideEffectFlow = viewModel.sideEffectChannel,
            onEvent = viewModel::onEvent,
            onNavigateBack = onNavigateUp
        )
    }
}

fun NavController.navigateToPlanScreen() {
    navigate(MenuScreens.PlanScreen) {
        launchSingleTop = true
    }
}
```

#### FASE 5: Integrar no Menu (15 min)

**1. Atualizar `MenuScreens.kt`:**
```kotlin
@Serializable
data object PlanScreen : MenuScreens()
```

**2. Atualizar `MenuGraph.kt`:**
```kotlin
import com.br.xbizitwork.ui.presentation.features.plans.navigation.planScreen

// Adicionar no menuGraph
planScreen(
    onNavigateUp = onNavigateUp
)
```

**3. Conectar navegação:**
```kotlin
// Onde onClickYourPlan é chamado
onNavigateToYourPlan = {
    navController.navigateToPlanScreen()
}
```

#### FASE 6: Testes (30 min)

- [ ] Listar planos funciona
- [ ] Loading aparece
- [ ] Erro é tratado
- [ ] Clicar "Assinar" funciona
- [ ] Toast aparece
- [ ] Navegar de volta funciona
- [ ] Múltiplas navegações funcionam

---

## 📊 RESUMO DAS ENTREGAS

### Bug Corrigido ✅
- App não crasha mais ao buscar profissional novamente
- Logs implementados
- stateIn implementado
- Navigation verificada

### Fluxo de Plano Completo ✅
- PlanUiState.kt
- PlanEvent.kt
- PlanViewModel.kt
- PlanScreen.kt
- PlanContent.kt
- PlanNavigation.kt
- Integração no Menu

---

## 🎯 CHECKLIST FINAL

### Manhã
- [ ] ☕ Café + Review
- [ ] 🐛 Correção do bug
- [ ] 🧪 Testes do bug
- [ ] 🎨 State + Events criados
- [ ] 🎨 ViewModel criado

### Tarde
- [ ] 🎨 Screen criada
- [ ] 🎨 Navigation criada
- [ ] 🎨 Integração Menu
- [ ] 🧪 Testes finais
- [ ] 📝 Documentação
- [ ] 💾 Commits

---

## 📝 COMMITS PLANEJADOS

### Commit 1: Correção do Bug
```
fix: Corrige crash ao buscar profissional após login

- Implementa stateIn em observeAuthSession()
- Adiciona try-catch para errors
- Verifica launchSingleTop na navegação
- Adiciona logs para debug

Bug corrigido: App fechava ao Login → Mapa → Home → Buscar
Status: ✅ Testado e funcionando
```

### Commit 2: Implementação do Plano
```
feat: Implementa fluxo completo de assinatura de plano

Screen + ViewModel:
- PlanUiState com lista de planos
- PlanEvent para ações
- PlanViewModel com GetAllPlanUseCase
- PlanScreen com Scaffold
- PlanContent dinâmico

Navigation:
- PlanNavigation com rota
- Integração em MenuGraph
- Conectado no botão "Seu Plano"

Backend já estava pronto:
- API, Repository, UseCase 100%

Simulação de assinatura até API estar pronta
Status: ✅ Funcional e testado
```

---

## ⏰ ESTIMATIVA FINAL

| Tarefa | Tempo Estimado | Tempo Real |
|--------|---------------|------------|
| Bug Correction | 30 min | ? |
| Plan Implementation | 120 min | ? |
| Tests | 45 min | ? |
| Documentation + Commits | 30 min | ? |
| **TOTAL** | **~4h** | **?** |

---

## 🎉 RESULTADO ESPERADO

Ao final do dia:
- ✅ Bug de crash corrigido
- ✅ Fluxo de plano 100% funcional
- ✅ Testes passando
- ✅ Commits limpos e documentados
- ✅ App estável e pronto para produção

---

**Status**: 📝 Pronto para execução  
**Prioridade**: 🔴 Alta  
**Data**: 03/02/2026

**BOA SORTE E BOM TRABALHO! 🚀**

---

**Planejado por**: GitHub Copilot  
**Data de Criação**: 03/02/2026 - 02:30 AM  
**Hora de dormir**: AGORA! 😴💤
