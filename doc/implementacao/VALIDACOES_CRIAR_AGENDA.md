# Validações de Regras de Negócio: Criar Agenda

**Data:** 2025-12-21  
**Contexto:** Adicionar horários na agenda profissional

---

## 🎯 Problema Identificado

Ao clicar em **"Adicionar à Lista"**, o sistema permitia:
- ❌ Adicionar mesmo horário múltiplas vezes
- ❌ Hora final menor que hora inicial (ex: 08:00-07:00)
- ❌ Sobreposição de horários no mesmo dia

---

## ✅ Validações Implementadas

### 1. Hora Final > Hora Inicial

**Regra:** A hora final SEMPRE deve ser maior que a hora inicial.

**Exemplos:**
```
✅ VÁLIDO:
- 08:00 → 10:00 (OK)
- 10:00 → 12:00 (OK)
- 14:00 → 16:00 (OK)

❌ INVÁLIDO:
- 10:00 → 08:00 (Hora final menor)
- 08:00 → 07:00 (Hora final menor)
- 10:00 → 10:00 (Hora final igual)
```

**Implementação:**
```kotlin
val startTimeInMinutes = startHour * 60 + startMinute
val endTimeInMinutes = endHour * 60 + endMinute

if (endTimeInMinutes <= startTimeInMinutes) {
    _sideEffectChannel.send(
        SideEffect.ShowToast("❌ Hora final deve ser maior que hora inicial!")
    )
    return
}
```

---

### 2. Sem Horários Duplicados

**Regra:** Não pode adicionar o mesmo horário mais de uma vez.

**Critérios de Duplicação:**
- Mesma **Categoria**
- Mesma **Especialidade**
- Mesmo **Dia da Semana**
- Mesma **Hora Início**
- Mesma **Hora Fim**

**Exemplos:**
```
✅ PERMITIDO:
Musculação | Segunda | 08:00-10:00
Musculação | Segunda | 10:00-12:00  ← Horários diferentes

✅ PERMITIDO:
Musculação | Segunda | 08:00-10:00
Natação    | Segunda | 08:00-10:00  ← Especialidades diferentes

❌ BLOQUEADO:
Musculação | Segunda | 08:00-10:00
Musculação | Segunda | 08:00-10:00  ← DUPLICADO!
```

**Implementação:**
```kotlin
val isDuplicate = state.scheduleTimeSlots.any { slot ->
    slot.categoryId == state.selectedCategoryId &&
    slot.specialtyId == state.selectedSpecialtyId &&
    slot.weekDay == state.selectedWeekDay &&
    slot.startTime == state.startTime &&
    slot.endTime == state.endTime
}

if (isDuplicate) {
    _sideEffectChannel.send(
        SideEffect.ShowToast("❌ Este horário já foi adicionado!")
    )
    return
}
```

---

### 3. Sem Sobreposição de Horários

**Regra:** Horários da mesma categoria/especialidade no mesmo dia não podem se sobrepor.

**Cenários de Sobreposição:**

#### Caso 1: Novo horário começa dentro de um existente
```
Existente: 08:00 ----------- 12:00
Novo:           10:00 --- 14:00
                ❌ BLOQUEADO
```

#### Caso 2: Novo horário termina dentro de um existente
```
Existente:      10:00 ----------- 14:00
Novo:      08:00 --- 12:00
                ❌ BLOQUEADO
```

#### Caso 3: Novo horário envolve completamente um existente
```
Existente:    10:00 --- 12:00
Novo:      08:00 --------------- 14:00
                ❌ BLOQUEADO
```

#### Caso 4: Horários sequenciais (PERMITIDO)
```
Existente: 08:00 --- 10:00
Novo:               10:00 --- 12:00
                ✅ PERMITIDO
```

**Implementação:**
```kotlin
val hasOverlap = state.scheduleTimeSlots.any { slot ->
    if (slot.categoryId == state.selectedCategoryId &&
        slot.specialtyId == state.selectedSpecialtyId &&
        slot.weekDay == state.selectedWeekDay) {
        
        val slotStartMinutes = /* ... */
        val slotEndMinutes = /* ... */
        
        // Verifica sobreposição
        val startsInside = startTimeInMinutes >= slotStartMinutes && 
                         startTimeInMinutes < slotEndMinutes
        val endsInside = endTimeInMinutes > slotStartMinutes && 
                       endTimeInMinutes <= slotEndMinutes
        val encompasses = startTimeInMinutes <= slotStartMinutes && 
                        endTimeInMinutes >= slotEndMinutes
        
        startsInside || endsInside || encompasses
    } else {
        false
    }
}

if (hasOverlap) {
    _sideEffectChannel.send(
        SideEffect.ShowToast("❌ Este horário sobrepõe outro já cadastrado!")
    )
    return
}
```

---

## 📊 Exemplos Práticos

### Cenário 1: Personal Training → Musculação

```kotlin
✅ VÁLIDO:
Segunda | 08:00-10:00
Segunda | 10:00-12:00
Segunda | 14:00-16:00

❌ INVÁLIDO:
Segunda | 08:00-10:00
Segunda | 09:00-11:00  ← Sobrepõe 08:00-10:00
```

### Cenário 2: Personal Training → Natação

```kotlin
✅ VÁLIDO:
Segunda | 08:00-09:00
Segunda | 09:00-10:00
Segunda | 10:00-11:00

❌ INVÁLIDO:
Segunda | 08:00-09:00
Segunda | 08:00-09:00  ← Duplicado
```

### Cenário 3: Hora Inválida

```kotlin
❌ INVÁLIDO:
Segunda | 10:00-08:00  ← Hora final < Hora inicial
Segunda | 12:00-12:00  ← Hora final = Hora inicial
```

---

## 🎯 Fluxo de Validação

```
Usuário clica "Adicionar à Lista"
        ↓
1. Validar hora final > hora inicial
   ❌ Se FALHAR: Exibir toast e PARAR
   ✅ Se OK: Continuar
        ↓
2. Validar duplicação
   ❌ Se DUPLICADO: Exibir toast e PARAR
   ✅ Se OK: Continuar
        ↓
3. Validar sobreposição
   ❌ Se SOBREPÕE: Exibir toast e PARAR
   ✅ Se OK: Continuar
        ↓
4. ✅ Adicionar horário na lista
   Exibir toast de sucesso
```

---

## 📱 Mensagens de Feedback

```kotlin
// ❌ Hora inválida
"❌ Hora final deve ser maior que hora inicial!"

// ❌ Duplicado
"❌ Este horário já foi adicionado!"

// ❌ Sobreposição
"❌ Este horário sobrepõe outro já cadastrado!"

// ✅ Sucesso
"✅ Horário adicionado!"
```

---

## 🔧 Arquivo Modificado

**Arquivo:** `CreateScheduleViewModel.kt`  
**Método:** `addTimeSlot()`

**Localização:**
```
ui/presentation/features/schedule/create/viewmodel/
  └─ CreateScheduleViewModel.kt
```

---

## 🧪 Como Testar

### Teste 1: Hora Final < Hora Inicial
1. Selecionar Categoria e Especialidade
2. Selecionar Dia da Semana
3. Hora Início: 10:00
4. Hora Fim: 08:00
5. Clicar "Adicionar à Lista"
6. ✅ Deve exibir: "❌ Hora final deve ser maior que hora inicial!"

### Teste 2: Horário Duplicado
1. Adicionar: Musculação | Segunda | 08:00-10:00
2. Tentar adicionar novamente: Musculação | Segunda | 08:00-10:00
3. ✅ Deve exibir: "❌ Este horário já foi adicionado!"

### Teste 3: Sobreposição
1. Adicionar: Musculação | Segunda | 08:00-10:00
2. Tentar adicionar: Musculação | Segunda | 09:00-11:00
3. ✅ Deve exibir: "❌ Este horário sobrepõe outro já cadastrado!"

### Teste 4: Horários Sequenciais (DEVE FUNCIONAR)
1. Adicionar: Musculação | Segunda | 08:00-10:00
2. Adicionar: Musculação | Segunda | 10:00-12:00
3. ✅ Deve adicionar com sucesso!

---

## ✅ Resultado Final

- ✅ **Validação 1:** Hora final > Hora inicial
- ✅ **Validação 2:** Sem duplicatas
- ✅ **Validação 3:** Sem sobreposição
- ✅ **Feedback:** Mensagens claras para o usuário
- ✅ **UX:** Impede dados inválidos antes de adicionar

---

**Validações Implementadas com Sucesso! 🎉**

