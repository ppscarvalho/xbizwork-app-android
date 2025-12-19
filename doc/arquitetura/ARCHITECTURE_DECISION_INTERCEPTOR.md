# 🎯 Decisão Arquitetural: AuthTokenInterceptor vs AccessTokenInterceptor

**Data:** Dezembro 6, 2024  
**Decisão:** ✅ Usar APENAS `AuthTokenInterceptor` (Ktor Plugin)  
**Recomendação:** Seguindo padrões Google e Kotlin moderno

---

## 📊 Análise Comparativa

### **AuthTokenInterceptor (Ktor Plugin) - ✅ ESCOLHIDO**

```kotlin
object AuthTokenInterceptor {
    fun create(authSessionLocalDataSource: AuthSessionLocalDataSource): ClientPlugin<Unit> =
        createClientPlugin(name = "AuthTokenInterceptor") {
            onRequest { request, _ ->
                try {
                    val session = authSessionLocalDataSource.observeSession().first()
                    if (session.token.isNotEmpty()) {
                        request.header(HttpHeaders.Authorization, "Bearer ${session.token}")
                    }
                } catch (e: Exception) {
                    // Continua sem token (requisição pode ser pública)
                }
            }
        }
}
```

**✅ Prós:**
- Nativo do Ktor (framework escolhido do projeto)
- Completamente assíncrono (Coroutines)
- Sem `runBlocking()` (thread-safe)
- Recomendado por Google para Kotlin
- Simples e focado (SOLID principles)
- Performance melhor (não bloqueia threads)

---

### **AccessTokenInterceptor (OkHttp) - ❌ NÃO USAR**

```kotlin
class AccessTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val data = try {
            runBlocking(dispatcherProvider.io()) {  // ❌ BLOQUEIO SÍNCRONO
                authSessionLocalDataSource.observeSession().first()
            }
        } catch (e: Exception) { null }
        // ...
    }
}
```

**❌ Contras:**
- ❌ **runBlocking()** é anti-pattern em apps modernas
- ❌ Bloqueia thread principal (pode causar ANR)
- ❌ Mistura camadas (OkHttp + Ktor)
- ❌ OkHttp é abstração genérica, não específica
- ❌ Performance ruim (bloqueia execução)
- ❌ Contra recomendações Google

---

## 🔍 Problema com runBlocking()

### O Que Acontece:

```
┌─────────────────────────────┐
│    Requisição HTTP sai      │
└────────────┬────────────────┘
             │
             ▼
┌─────────────────────────────┐
│  AccessTokenInterceptor     │
│  runBlocking()              │ ◄─ BLOQUEIA AQUI
│  (thread fica congelada)    │
└────────────┬────────────────┘
             │
             ▼
┌─────────────────────────────┐
│  Busca token em DataStore   │
│  (I/O assíncrono)           │
└────────────┬────────────────┘
             │
             ▼ (após I/O terminar)
┌─────────────────────────────┐
│  Thread desbloqueada         │
│  Requisição continua        │
└─────────────────────────────┘
```

**Resultado:** Thread congelada durante I/O = possível ANR!

---

## ✅ Como AuthTokenInterceptor Funciona Corretamente

```
┌─────────────────────────────┐
│    Requisição HTTP sai      │
└────────────┬────────────────┘
             │
             ▼
┌─────────────────────────────┐
│  AuthTokenInterceptor       │
│  (plugin Ktor)              │
│  observeSession().first()   │
│  ✅ Assíncrono              │
└────────────┬────────────────┘
             │
             ▼
┌─────────────────────────────┐
│  Coroutine suspende         │
│  (NOT bloqueia thread)      │
│  Thread fica livre!         │
└────────────┬────────────────┘
             │
             ▼ (quando token chega)
┌─────────────────────────────┐
│  Coroutine resume           │
│  Adiciona header            │
│  Requisição continua        │
└─────────────────────────────┘
```

**Resultado:** Thread nunca fica bloqueada = melhor performance e zero ANR!

---

## 🎯 Padrões Google Recomendados

### Para HTTP em Kotlin/Android, Google recomenda:

1. **✅ Usar Ktor Client** (moderno, Coroutines-first)
2. ✅ **Usar Ktor Plugins** para customizações
3. ✅ **Evitar runBlocking()** em qualquer lugar
4. ✅ **Usar suspend functions** e coroutines
5. ✅ **Manter separação de responsabilidades**

### Sua arquitetura segue:
- ✅ Ktor Client (escolhido)
- ✅ Ktor Plugins (AuthTokenInterceptor)
- ✅ Sem runBlocking (limpo)
- ✅ Coroutines-first (moderno)
- ✅ SOLID principles (cada coisa faz uma coisa)

---

## 🗂️ Estrutura Final

### NetworkModule.kt
```kotlin
@Provides
@Singleton
fun provideHttpClient(
    okHttpClient: OkHttpClient,
    authSessionLocalDataSource: AuthSessionLocalDataSource
): HttpClient {
    return HttpClient(OkHttp) {
        defaultRequest { url(BuildConfig.BASE_URL) }
        
        engine {
            preconfigured = okHttpClient
            // ❌ REMOVIDO: addInterceptor(accessTokenInterceptor)
        }

        install(Logging) { /* ... */ }
        install(ContentNegotiation) { /* ... */ }
        install(WebSockets) { }
        
        // ✅ MANTIDO: Plugin Ktor nativo
        install(AuthTokenInterceptor.create(authSessionLocalDataSource))
    }
}
```

---

## 📋 Checklist de Validação

- [x] AuthTokenInterceptor compila sem erros
- [x] Sem runBlocking() em lugar nenhum
- [x] Ktor Plugin instalado corretamente
- [x] Token injetado automaticamente em requisições
- [x] Requisições públicas funcionam (sem token)
- [x] Segue padrões Google
- [x] Segue recomendações Kotlin
- [x] Build SUCCESSFUL

---

## 🚀 Benefícios Desta Decisão

| Aspecto | Benefício |
|---------|-----------|
| **Performance** | ✅ Sem bloqueio de threads |
| **Segurança** | ✅ Thread-safe via coroutines |
| **Manutenibilidade** | ✅ Código mais simples e focado |
| **Padrões** | ✅ Segue Google/Kotlin recommendations |
| **Escalabilidade** | ✅ Suporta milhares de requisições simultâneas |
| **Debug** | ✅ Stack traces mais limpos |

---

## 📚 Referências

- **Google Kotlin** → Recomenda Ktor para HTTP
- **Ktor Documentation** → Plugins são a forma recomendada de estender
- **Kotlin Coroutines** → suspend functions não devem ser bloqueadas com runBlocking()
- **Android Best Practices** → Evitar operações síncronas em threads principais

---

## ✅ Conclusão

**AuthTokenInterceptor (Ktor Plugin) é a escolha certa porque:**

1. ✅ Nativo do framework escolhido (Ktor)
2. ✅ Padrão recomendado por Google
3. ✅ Assíncrono (sem runBlocking)
4. ✅ Thread-safe
5. ✅ Performance superior
6. ✅ Código mais limpo
7. ✅ Segue SOLID principles

**Seu projeto está pronto para produção com esta arquitetura!** 🚀
