# Correção do Padrão de Navegação

**Data**: 21/12/2025

## ❌ Problema Identificado

Os nomes dos parâmetros de navegação estavam **fora do padrão** estabelecido no projeto:

### Padrão Incorreto (ANTES):
```kotlin
// ❌ Errado - usando plural "Schedules"
onNavigateToViewSchedules: () -> Unit
onNavigateToProfessionalAgenda: () -> Unit
```

### Padrão Correto do Projeto:
```kotlin
// ✅ Correto - seguindo o padrão existente
onNavigateToHomeGraph       // Quando navega para um GRAPH
onNavigateToEditProfile     // Quando navega para uma SCREEN (sem "Screen" no final)
onNavigateToViewSchedule    // Singular, não plural
onNavigateToProfessionalAgenda  // Nome direto da tela
```

---

## ✅ Correções Aplicadas

### 1. **MenuNavigation.kt**
```kotlin
fun NavGraphBuilder.menuScreen(
    onNavigateToHomeGraph: () -> Unit,
    onNavigateToEditProfile: () -> Unit,
    onNavigateToViewSchedule: () -> Unit,          // ✅ Corrigido: sem "s" no final
    onNavigateToProfessionalAgenda: () -> Unit     // ✅ Mantido
){
    composable<MenuScreens.MenuScreen> {
        // ...
        MenuScreen(
            // ...
            onClickDateRange = { onNavigateToViewSchedule() },           // ✅ Corrigido
            onClickViewModule = { onNavigateToProfessionalAgenda() },    // ✅ Corrigido
        )
    }
}
```

### 2. **MenuGraph.kt**
```kotlin
fun NavGraphBuilder.menuGraph(
    onNavigateUp: () -> Unit,
    onNavigateToEditProfile: () -> Unit,
    onNavigateToCreateSchedule: () -> Unit,
    onNavigateToViewSchedule: () -> Unit,          // ✅ Corrigido: singular
    onNavigateToProfessionalAgenda: () -> Unit     // ✅ Mantido
){
    navigation<Graphs.MenuGraphs>(startDestination = MenuScreens.MenuScreen) {
        menuScreen(
            onNavigateToHomeGraph = onNavigateUp,
            onNavigateToEditProfile = onNavigateToEditProfile,
            onNavigateToViewSchedule = onNavigateToViewSchedule,              // ✅ Corrigido
            onNavigateToProfessionalAgenda = onNavigateToProfessionalAgenda   // ✅ Corrigido
        )
        // ...
    }
}
```

### 3. **HomeGraph.kt**
```kotlin
menuGraph(
    onNavigateUp = onNavigateUp,
    onNavigateToEditProfile = {
        navController.navigateToEditProfileScreen()
    },
    onNavigateToCreateSchedule = {
        navController.navigateToCreateSchedule()
    },
    onNavigateToViewSchedule = {                        // ✅ Corrigido: nome do parâmetro
        navController.navigateToViewSchedules()         // ✅ Função mantida (é a navegação real)
    },
    onNavigateToProfessionalAgenda = {                  // ✅ Corrigido
        navController.navigateToProfessionalAgenda()
    }
)
```

---

## 📝 Padrão de Nomenclatura

### Regra estabelecida:

| Tipo | Padrão | Exemplo |
|------|--------|---------|
| **Parâmetro de callback** | `onNavigateTo` + nome destino | `onNavigateToViewSchedule` |
| **Função de extensão** | `navigateTo` + nome destino | `navigateToViewSchedules()` |
| **Singular vs Plural** | Usar **SINGULAR** nos parâmetros | `ViewSchedule` não `ViewSchedules` |
| **Screen vs Graph** | Omitir "Screen", manter "Graph" | `EditProfile` não `EditProfileScreen` |

---

## ✅ Resultado

- ✅ **Padrão unificado** em todos os arquivos de navegação
- ✅ **Nomenclatura consistente** com o resto do projeto
- ✅ **Compilação bem-sucedida** (Kotlin compile OK)
- ✅ **Navegação funcional** para todas as telas de Schedule

---

## 📚 Arquivos Modificados

1. `app/.../navigation/graphs/MenuGraph.kt`
2. `app/.../features/menu/navigation/MenuNavigation.kt`
3. `app/.../navigation/graphs/HomeGraph.kt`

---

**Implementado por**: GitHub Copilot  
**Revisado**: Seguindo feedback do desenvolvedor sobre padrões do projeto

