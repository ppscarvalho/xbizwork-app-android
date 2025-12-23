# 📌 CHECKLIST DE VALIDAÇÃO TÉCNICA – VIEWMODEL (PADRÃO OFICIAL)

> **Objetivo**
>
> Este documento define um **checklist automático e obrigatório** para validação de qualquer ViewModel criada no projeto.
> Ele foi elaborado a partir da comparação entre uma **ViewModel de referência correta (Signup)** e uma **ViewModel fora de padrão (Schedule)**.
>
> O foco é **arquitetura, contrato e responsabilidade**, não estilo pessoal.

---

## 🧩 1. PADRÃO DE COLETA DE ESTADO (FLOW / UISTATE)

- [ ] A ViewModel utiliza **APENAS UM** padrão de coleta?
- [ ] O padrão adotado é consistente em toda a ViewModel?
- [ ] Não existe mistura entre:
  - `collectUiState`
  - `when (UiState)`
  - `try/catch`
- [ ] O fluxo de loading → success → error é linear e previsível?

❌ Reprovado se houver mais de um padrão de coleta.

---

## 🧩 2. CONTRATO DOS USECASES

- [ ] Todos os UseCases seguem o **MESMO contrato de retorno**?
- [ ] Não existem UseCases síncronos misturados com Flow?
- [ ] Não existem tipos ad-hoc (`DefaultResult`, `Any`, etc.)?
- [ ] Todos os tipos utilizados estão importados e compilam corretamente?

❌ Reprovado se houver contratos diferentes entre UseCases.

---

## 🧩 3. RESPONSABILIDADE DA VIEWMODEL

- [ ] A ViewModel **NÃO** contém regras de negócio?
- [ ] A ViewModel **NÃO** valida:
  - horários
  - duplicidade
  - sobreposição
  - regras temporais
- [ ] A ViewModel apenas **coordena eventos e estado**?

❌ Reprovado se a ViewModel executar lógica de domínio.

---

## 🧩 4. VALIDAÇÕES DE BACKEND

- [ ] A ViewModel **NÃO** monta requests complexos?
- [ ] A ViewModel **NÃO** chama validações remotas diretamente?
- [ ] A ViewModel **NÃO** decide regra de negócio baseada em resposta do backend?

❌ Reprovado se a ViewModel interpretar regra de negócio remota.

---

## 🧩 5. CONTROLE DE ESTADO E CONCORRÊNCIA

- [ ] Não existem múltiplos `collect` concorrentes?
- [ ] Não existem `Flow.collect` dentro de `forEach`?
- [ ] Não existem variáveis mutáveis compartilhadas entre fluxos?
- [ ] O estado é sempre atualizado via `copy()`?

❌ Reprovado se houver risco de condição de corrida.

---

## 🧩 6. CONTROLE DE LOADING, SUCESSO E ERRO

- [ ] Existe **UM único ponto** de loading?
- [ ] Sucesso é tratado de forma única?
- [ ] Erro é tratado de forma consistente?
- [ ] SideEffects são isolados (Channel / Effect)?

❌ Reprovado se loading, sucesso ou erro forem tratados de formas diferentes.

---

## 🧩 7. CONSISTÊNCIA COM VIEWMODEL DE REFERÊNCIA

- [ ] Esta ViewModel segue exatamente o mesmo padrão da Signup?
- [ ] Poderia ser explicada como “variação do mesmo template”?
- [ ] Não exige exceções arquiteturais para funcionar?

❌ Reprovado se não for compatível com o padrão oficial.

---

## ✅ RESULTADO FINAL

- [ ] APROVADO – ViewModel segue padrão arquitetural
- [ ] REPROVADO – Necessita correção estrutural antes de qualquer ajuste de código

> ⚠️ **Observação Final**
>
> Nenhuma ViewModel deve ser corrigida ou refatorada
> antes de **passar integralmente por este checklist**.

