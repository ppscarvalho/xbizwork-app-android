# 🗺️ Mapeamento Completo de Navegação do Menu

**Data**: 21/12/2025

## 🎯 Propósito deste Documento

Este documento mapeia **exatamente para onde cada opção do menu navega** e explica **por que temos 3 pastas dentro de schedule/**.

---

## 📱 Menu Atual → Navegação Real

| Texto do Botão | Callback Atual | Para onde vai? | Deveria chamar |
|----------------|----------------|----------------|----------------|
| **"Alterar Perfil"** | `onClickUpdateProfile` | ✅ `EditProfileScreen` | ✅ OK |
| **"Alterar Senha"** | `onClickChangerPassword` | ❌ Vazio `{}` | `onClickChangePassword` |
| **"Monte sua agenda"** | ❌ `onClickDateRange` | ✅ `ViewSchedulesScreen` (lista) | ✅ `onClickSetupSchedule` |
| **"Seu plano"** | `onClickAssignment` | ❌ Vazio `{}` | `onClickYourPlan` |
| **"Meus compromissos"** | `onClickEvent` | ❌ Vazio `{}` | `onClickMyAppointments` |
| **"Agenda profissional"** | ❌ `onClickViewModule` | ✅ `ProfessionalAgendaScreen` (calendário) | ✅ `onClickProfessionalAgenda` |
| **"Dúvidas frequentes"** | `onClickFAQ` | ❌ Vazio `{}` | ✅ OK |
| **"Versão do aplicativo"** | `onClickAppVersion` | ❌ Vazio `{}` | ✅ OK |
| **"Avalie nosso aplicativo"** | `onClickRateApp` | ❌ Vazio `{}` | ✅ OK |
| **"Sair"** | `onClickLogout` | ✅ Logout + Home | ✅ OK |

---

## 🗂️ Por que temos 3 pastas em `schedule/`?

### Estrutura Atual:
```
schedule/
├── agenda/    → ProfessionalAgendaScreen (Calendário visual)
├── create/    → CreateScheduleScreen (Criar nova agenda)
└── list/      → ViewSchedulesScreen (Listar agendas)
```

### 📋 Explicação:

#### 1️⃣ **schedule/list/** (ViewSchedulesScreen)
**Propósito**: Lista todas as agendas/disponibilidades que o profissional já criou

**Quando usar**: Quando o profissional quer:
- Ver todas as suas agendas cadastradas
- Editar uma agenda existente
- Excluir uma agenda
- Criar uma nova agenda (via FAB)

**Exemplo de tela**:
```
┌─────────────────────────────────────┐
│  Minhas Agendas                     │
├─────────────────────────────────────┤
│  📋 Encanador - Segunda a Sexta     │
│     08:00 - 18:00                   │
├─────────────────────────────────────┤
│  📋 Eletricista - Sábado            │
│     09:00 - 13:00                   │
├─────────────────────────────────────┤
│                        [+] FAB      │
└─────────────────────────────────────┘
```

---

#### 2️⃣ **schedule/create/** (CreateScheduleScreen)
**Propósito**: Formulário para criar UMA nova agenda/disponibilidade

**Quando usar**: Quando o profissional quer criar uma nova disponibilidade informando:
- Categoria do serviço (ex: Encanador)
- Especialidade (ex: Instalação Hidráulica)
- Dias da semana que trabalha
- Horário de início e fim

**Exemplo de tela**:
```
┌─────────────────────────────────────┐
│  Criar Nova Agenda                  │
├─────────────────────────────────────┤
│  Categoria: [Encanador       ▼]    │
│  Especialidade: [Instalação  ▼]    │
│  Dias: [S] [T] [Q] [Q] [S] [S] [D] │
│  Início: [08:00]                    │
│  Fim: [18:00]                       │
│                                      │
│         [Cancelar]  [Salvar]        │
└─────────────────────────────────────┘
```

---

#### 3️⃣ **schedule/agenda/** (ProfessionalAgendaScreen)
**Propósito**: Visualização em formato de CALENDÁRIO dos compromissos do profissional

**Quando usar**: Quando o profissional quer:
- Ver os compromissos agendados por clientes
- Visualizar a agenda em formato de calendário
- Ver disponibilidade por dia/semana

**Exemplo de tela**:
```
┌─────────────────────────────────────┐
│  Dezembro 2025                      │
├─────────────────────────────────────┤
│  S  T  Q  Q  S  S  D               │
│  1  2  3  4  5  6  7               │
│  8  9 10 11 12 13 14               │
│ 15 16 17 18 19 20 21               │
├─────────────────────────────────────┤
│  Segunda, 21/12                     │
│  08:00 - João Silva (Encanador)    │
│  14:00 - Maria Souza (Eletricista) │
└─────────────────────────────────────┘
```

---

## 🔄 Fluxo de Navegação Atual

### Menu → "Monte sua agenda"
```
MenuScreen (onClickDateRange)
    ↓
ViewSchedulesScreen (lista de agendas)
    ↓ (clique no FAB)
CreateScheduleScreen (criar nova)
    ↓ (após salvar)
ViewSchedulesScreen (volta para lista)
```

### Menu → "Agenda profissional"
```
MenuScreen (onClickViewModule)
    ↓
ProfessionalAgendaScreen (calendário)
```

---

## ❌ Problemas Identificados

### 1. **Nomes de Callbacks Confusos**

**Problema**: Os nomes não refletem a ação real

| Atual | Problema | Deveria ser |
|-------|----------|-------------|
| `onClickDateRange` | ❌ Fala de "range de data", não de "montar agenda" | `onClickSetupSchedule` |
| `onClickViewModule` | ❌ Fala de "módulo", não de "agenda profissional" | `onClickProfessionalAgenda` |
| `onClickAssignment` | ❌ "Assignment" não é "plano" | `onClickYourPlan` |
| `onClickEvent` | ❌ "Event" é genérico, não "compromissos" | `onClickMyAppointments` |
| `onClickChangerPassword` | ⚠️ Typo: "Changer" | `onClickChangePassword` |

### 2. **Navegação Incompleta**

Várias opções ainda têm callbacks vazios `{}`

---

## ✅ Solução Proposta

### Renomear TODOS os callbacks seguindo o padrão:

**REGRA**: `onClick` + tradução do texto do botão (em inglês, CamelCase)

| Texto do Botão | Novo Callback |
|----------------|---------------|
| "Alterar Perfil" | `onClickUpdateProfile` ✅ (já está correto) |
| "Alterar Senha" | `onClickChangePassword` |
| "Monte sua agenda" | `onClickSetupSchedule` |
| "Seu plano" | `onClickYourPlan` |
| "Meus compromissos" | `onClickMyAppointments` |
| "Agenda profissional" | `onClickProfessionalAgenda` |
| "Dúvidas frequentes" | `onClickFAQ` ✅ (já está correto) |
| "Versão do aplicativo" | `onClickAppVersion` ✅ (já está correto) |
| "Avalie nosso aplicativo" | `onClickRateApp` ✅ (já está correto) |
| "Sair" | `onClickLogout` ✅ (já está correto) |

---

## 📊 Resumo da Estrutura Schedule

### Por que 3 pastas?

**1 funcionalidade = 1 pasta**

```
schedule/
├── list/      → LISTAR agendas existentes
├── create/    → CRIAR nova agenda
└── agenda/    → VISUALIZAR calendário de compromissos
```

**Cada pasta é uma SCREEN independente com sua própria responsabilidade.**

---

**Criado em**: 21/12/2025  
**Próximo passo**: Refatorar todos os nomes de callbacks

