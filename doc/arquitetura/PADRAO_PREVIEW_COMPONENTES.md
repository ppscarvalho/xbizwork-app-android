# Padrão: Criação de Previews para Componentes

**Data:** 2025-12-21  
**Objetivo:** Estabelecer padrão para criação de previews em todos os componentes Compose

---

## 📋 Regra Geral

**SEMPRE criar preview para TODOS os componentes Composable!**

---

## 🎯 Padrão de Preview

### 1. Estrutura Básica

```kotlin
// ============================================
// PREVIEW
// ============================================

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ComponenteNomePreview() {
    XBizWorkTheme {
        ComponenteNome(
            // ... parâmetros de exemplo
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ComponenteNomeDarkPreview() {
    XBizWorkTheme {
        ComponenteNome(
            // ... parâmetros de exemplo
        )
    }
}
```

---

## 📦 Imports Necessários

```kotlin
import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.br.xbizitwork.ui.theme.XBizWorkTheme
```

---

## ✅ Exemplo Completo: WeekDayDropdown

### Componente
```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekDayDropdown(
    selectedWeekDayName: String,
    onWeekDaySelected: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    // ... implementação
}
```

### Preview Light Mode
```kotlin
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun WeekDayDropdownPreview() {
    XBizWorkTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            WeekDayDropdown(
                selectedWeekDayName = "Segunda-feira",
                onWeekDaySelected = { _, _ -> }
            )
        }
    }
}
```

### Preview Dark Mode
```kotlin
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WeekDayDropdownDarkPreview() {
    XBizWorkTheme {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            WeekDayDropdown(
                selectedWeekDayName = "",
                onWeekDaySelected = { _, _ -> }
            )
        }
    }
}
```

---

## 🎨 Boas Práticas

### 1. Sempre 2 Previews
- ✅ **Light Mode:** `UI_MODE_NIGHT_NO`
- ✅ **Dark Mode:** `UI_MODE_NIGHT_YES`

### 2. Usar `showBackground = true`
```kotlin
@Preview(showBackground = true, ...)
```

### 3. Envolver com `XBizWorkTheme`
```kotlin
XBizWorkTheme {
    // componente aqui
}
```

### 4. Adicionar Padding para Visualização
```kotlin
modifier = Modifier.padding(16.dp)
```

### 5. Nome do Preview
- **Padrão:** `{ComponenteNome}Preview`
- **Dark Mode:** `{ComponenteNome}DarkPreview`

### 6. Modificador `private`
```kotlin
private fun ComponenteNomePreview()
```

---

## 📝 Checklist de Criação de Componente

- [ ] Implementar o componente Composable
- [ ] Documentar com KDoc (/** ... */)
- [ ] Criar preview Light Mode
- [ ] Criar preview Dark Mode
- [ ] Adicionar seção `// PREVIEW` antes dos previews
- [ ] Testar ambos os previews no Android Studio
- [ ] Compilar sem erros

---

## 🎯 Componentes Verificados

### ✅ Com Preview (Light + Dark)
- `AddedScheduleTimeSlotsCard` ✅
- `WeekDayDropdown` ✅
- `CategoryDropdown` ✅
- `SpecialtyDropdown` ✅
- `ScheduleTimeSlotCard` ✅
- `CategoryInfoRow` ✅
- `SpecialtyInfoRow` ✅
- `ScheduleTableHeader` ✅
- `ScheduleTimeSlotRow` ✅
- `ScheduleTimeSlotList` ✅
- `ProfessionalScheduleCard` ✅

---

## 💡 Exemplos de Dados para Preview

### String Simples
```kotlin
selectedWeekDayName = "Segunda-feira"
```

### Lista Vazia
```kotlin
timeSlots = emptyList()
```

### Lista com Dados
```kotlin
val sampleSlots = listOf(
    ScheduleTimeSlot(
        id = "1",
        categoryId = 1,
        categoryName = "Educador Físico",
        specialtyId = 1,
        specialtyName = "Musculação",
        weekDay = 1,
        weekDayName = "Segunda-feira",
        startTime = "08:00",
        endTime = "10:00"
    )
)
```

### Callbacks Vazios
```kotlin
onWeekDaySelected = { _, _ -> }
onRemoveSlot = {}
onClick = {}
```

---

## 🚀 Como Visualizar no Android Studio

1. Abrir o arquivo do componente
2. Clicar em **"Split"** (Ctrl+Shift+P)
3. Ver o preview ao lado direito
4. Alternar entre Light/Dark usando os controles do preview

---

## 📊 Benefícios

✅ **Visualização rápida** sem executar o app  
✅ **Teste de Light e Dark mode** simultaneamente  
✅ **Detecção de problemas** de layout/cores  
✅ **Documentação visual** do componente  
✅ **Facilita manutenção** e refatoração  
✅ **Code review** mais eficiente  

---

## ⚠️ Erros Comuns

### ❌ Esquecer imports
```kotlin
// Sempre adicionar
import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview
import com.br.xbizitwork.ui.theme.XBizWorkTheme
```

### ❌ Não envolver com Theme
```kotlin
// ❌ ERRADO
@Preview
@Composable
fun MyPreview() {
    MyComponent()
}

// ✅ CORRETO
@Preview
@Composable
fun MyPreview() {
    XBizWorkTheme {
        MyComponent()
    }
}
```

### ❌ Parâmetros obrigatórios não preenchidos
```kotlin
// ❌ ERRADO - vai dar erro de compilação
MyComponent()

// ✅ CORRETO - passar todos os parâmetros
MyComponent(
    param1 = "valor",
    param2 = {},
    modifier = Modifier
)
```

---

## 📚 Referências

- [Compose Preview Documentation](https://developer.android.com/jetpack/compose/tooling/previews)
- [Material Design 3 Theming](https://m3.material.io/develop/android/jetpack-compose)

---

**Padrão Estabelecido e Documentado! ✅**

