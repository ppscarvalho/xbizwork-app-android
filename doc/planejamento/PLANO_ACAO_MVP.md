# 🚀 PLANO DE AÇÃO - MVP XBIZWORK

## 🎯 Objetivo
Criar MVP funcional de um marketplace de serviços (estilo Uber) em **8 semanas**.

---

## 📅 CRONOGRAMA DETALHADO

### **SEMANA 1-2: Fundação do Domínio**

#### Dia 1-3: Criar Entidades de Domínio
```kotlin
📁 core/domain/model/

✅ CRIAR:
1. Professional.kt
2. Service.kt  
3. ServiceCategory.kt
4. Booking.kt
5. BookingStatus.kt
6. Review.kt
7. Location.kt
8. PriceRange.kt
9. Availability.kt
10. UserType.kt (atualizar User existente)
```

**Código Exemplo - Professional.kt:**
```kotlin
package com.br.xbizitwork.core.domain.model

data class Professional(
    val id: String,
    val userId: String,
    val professionalName: String,
    val bio: String,
    val categories: List<String>,
    val rating: Double = 0.0,
    val reviewsCount: Int = 0,
    val profilePhoto: String?,
    val verified: Boolean = false,
    val location: Location,
    val priceRange: PriceRange,
    val createdAt: String
)

data class Location(
    val latitude: Double,
    val longitude: Double,
    val address: String,
    val city: String,
    val state: String
)

data class PriceRange(
    val min: Double,
    val max: Double,
    val currency: String = "BRL"
)
```

#### Dia 4-7: APIs e Repositories
```kotlin
📁 core/data/remote/

✅ CRIAR:
1. service/ServiceApiService.kt
2. service/ServiceApiServiceImpl.kt
3. professional/ProfessionalApiService.kt
4. professional/ProfessionalApiServiceImpl.kt
5. booking/BookingApiService.kt
6. booking/BookingApiServiceImpl.kt

📁 ui/presentation/features/

7. professional/domain/repository/ProfessionalRepository.kt
8. professional/data/repository/ProfessionalRepositoryImpl.kt
9. booking/domain/repository/BookingRepository.kt
10. booking/data/repository/BookingRepositoryImpl.kt
```

#### Dia 8-10: Dependency Injection
```kotlin
📁 core/di/

✅ ATUALIZAR/CRIAR:
1. KtorModule.kt - adicionar novos ApiServices
2. RepositoryModule.kt - adicionar novos repositories
```

### **SEMANA 3-4: Telas do Cliente**

#### Dia 11-14: Home Screen Adaptada
```kotlin
📁 ui/presentation/features/home/

✅ MODIFICAR HomeScreen.kt:

Adicionar:
1. Barra de busca de serviços
2. Carrossel de categorias (similar aos chips da imagem)
3. Lista de "Profissionais Recomendados" (cards com foto, nome, rating)
4. Seção "Seus Próximos Agendamentos" (cards de bookings)

Criar componentes:
- CategoryChipRow.kt
- ProfessionalCard.kt (igual aos cards de médicos da imagem)
- UpcomingBookingCard.kt
```

**Exemplo - HomeScreen adaptada:**
```kotlin
@Composable
fun HomeContent(
    uiState: HomeUIState,
    onCategoryClick: (String) -> Unit,
    onProfessionalClick: (String) -> Unit,
    onBookingClick: (String) -> Unit
) {
    LazyColumn {
        // Barra de busca
        item {
            SearchBar(
                placeholder = "Buscar serviços...",
                onSearchClick = { /* Navegar para SearchScreen */ }
            )
        }
        
        // Categorias populares
        item {
            Text("Categorias Populares", style = MaterialTheme.typography.titleMedium)
            CategoryChipRow(
                categories = uiState.categories,
                onCategoryClick = onCategoryClick
            )
        }
        
        // Profissionais recomendados
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Profissionais Recomendados")
                TextButton(onClick = { /* Ver todos */ }) {
                    Text("Ver Todos")
                }
            }
        }
        
        item {
            LazyRow {
                items(uiState.recommendedProfessionals) { professional ->
                    ProfessionalCard(
                        professional = professional,
                        onClick = { onProfessionalClick(professional.id) }
                    )
                }
            }
        }
        
        // Próximos agendamentos
        item {
            Text("Seus Agendamentos", style = MaterialTheme.typography.titleMedium)
        }
        
        items(uiState.upcomingBookings) { booking ->
            UpcomingBookingCard(
                booking = booking,
                onClick = { onBookingClick(booking.id) }
            )
        }
    }
}
```

#### Dia 15-18: Search/Browse Screen
```kotlin
📁 ui/presentation/features/search/

✅ CRIAR:
1. screen/SearchScreen.kt
2. viewmodel/SearchViewModel.kt
3. state/SearchUIState.kt
4. components/FilterBottomSheet.kt
5. components/ProfessionalListItem.kt
6. navigation/SearchNavigation.kt

Funcionalidades:
- Busca por texto
- Filtros: categoria, preço, distância, avaliação
- Ordenação: mais próximo, melhor avaliado, menor preço
- Lista com scroll infinito
```

#### Dia 19-21: Professional Detail Screen
```kotlin
📁 ui/presentation/features/professional/detail/

✅ CRIAR:
1. screen/ProfessionalDetailScreen.kt
2. viewmodel/ProfessionalDetailViewModel.kt
3. components/ServiceListSection.kt
4. components/ReviewsSection.kt
5. components/PortfolioSection.kt
6. components/AvailabilitySection.kt

Layout:
┌─────────────────────────┐
│ Foto de capa            │
├─────────────────────────┤
│ Avatar | Nome           │
│        | ⭐ 4.8 (120+)  │
│        | 📍 São Paulo   │
├─────────────────────────┤
│ Sobre                   │
│ Bio do profissional...  │
├─────────────────────────┤
│ Serviços                │
│ ├─ Serviço 1 - R$ 100  │
│ ├─ Serviço 2 - R$ 150  │
├─────────────────────────┤
│ Avaliações (120)        │
│ ├─ Review 1            │
│ ├─ Review 2            │
├─────────────────────────┤
│ [Botão Agendar Serviço]│
└─────────────────────────┘
```

### **SEMANA 5-6: Sistema de Agendamento**

#### Dia 22-25: Booking Flow
```kotlin
📁 ui/presentation/features/booking/

✅ CRIAR:
1. create/BookingCreateScreen.kt
   - Seleção de serviço
   - Escolha de data (calendário)
   - Escolha de horário (slots disponíveis)
   - Adicionar observações
   - Resumo e confirmação

2. list/MyBookingsScreen.kt
   - Abas: Ativos | Passados
   - Lista de bookings
   - Status em tempo real
   - Opção cancelar/reagendar

3. detail/BookingDetailScreen.kt
   - Informações completas
   - Status tracking
   - Chat com profissional
   - Avaliação (após conclusão)

4. viewmodel/BookingViewModel.kt
5. state/BookingUIState.kt
```

**Fluxo de Agendamento:**
```
1. Professional Detail → "Agendar Serviço"
2. Booking Create → Selecionar Serviço
3. Booking Create → Escolher Data
4. Booking Create → Escolher Horário
5. Booking Create → Confirmar
6. My Bookings → Ver agendamento criado
```

#### Dia 26-28: Backend Integration
```kotlin
✅ IMPLEMENTAR:
1. CreateBookingUseCase
2. GetMyBookingsUseCase
3. CancelBookingUseCase
4. GetProfessionalAvailabilityUseCase

✅ TESTAR:
- Criar agendamento
- Listar agendamentos
- Cancelar agendamento
- Verificar disponibilidade
```

### **SEMANA 7: Lado do Profissional**

#### Dia 29-32: Professional Dashboard
```kotlin
📁 ui/presentation/features/professional/dashboard/

✅ CRIAR:
1. screen/ProfessionalDashboardScreen.kt
2. components/EarningsCard.kt
3. components/TodayBookingsCard.kt
4. components/RecentReviewsCard.kt
5. components/StatsCard.kt

Layout:
┌─────────────────────────┐
│ Ganhos do Mês           │
│ R$ 4.500,00 ↗️ 15%     │
├─────────────────────────┤
│ Agendamentos Hoje (3)   │
│ ├─ 10:00 - Cliente A   │
│ ├─ 14:00 - Cliente B   │
│ └─ 16:00 - Cliente C   │
├─────────────────────────┤
│ Avaliações Recentes     │
│ ⭐⭐⭐⭐⭐ Excelente!     │
├─────────────────────────┤
│ Estatísticas            │
│ Taxa de Conclusão: 95%  │
└─────────────────────────┘
```

#### Dia 33-35: Manage Services & Agenda
```kotlin
📁 ui/presentation/features/professional/

✅ CRIAR manage_services/:
1. ManageServicesScreen.kt
   - Lista de serviços
   - Adicionar serviço
   - Editar serviço
   - Ativar/Desativar

✅ ATUALIZAR menu/:
2. ProfessionalAgendaScreen.kt (já existe)
   - Adicionar calendário visual
   - Marcar disponibilidade
   - Ver agendamentos confirmados
   - Bloquear horários
```

### **SEMANA 8: Polimento e Testes**

#### Dia 36-38: UI/UX Polimento
```kotlin
✅ MELHORIAS:
1. Animações de transição
2. Loading states
3. Empty states
4. Error handling UI
5. Pull-to-refresh
6. Skeleton loaders
```

#### Dia 39-40: Testes
```kotlin
✅ TESTES:
1. Testes unitários dos UseCases
2. Testes de ViewModels
3. Testes de integração
4. Testes manuais de fluxo completo
```

#### Dia 41-42: Ajustes Finais
```kotlin
✅ CHECKLIST FINAL:
- [ ] Todos os fluxos funcionando
- [ ] Tratamento de erros
- [ ] Loading states
- [ ] Navegação fluida
- [ ] Performance otimizada
- [ ] App rodando sem crashes
```

---

## 🎨 COMPONENTES UI A CRIAR

### 1. **ProfessionalCard.kt** (Prioridade ALTA)
```kotlin
@Composable
fun ProfessionalCard(
    professional: Professional,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(180.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            // Foto do profissional
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                AsyncImage(
                    model = professional.profilePhoto,
                    contentDescription = professional.professionalName,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                
                // Badge de verificado
                if (professional.verified) {
                    Icon(
                        imageVector = Icons.Default.Verified,
                        contentDescription = "Verificado",
                        tint = Color.Blue,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Nome
            Text(
                text = professional.professionalName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            // Especialidade
            Text(
                text = professional.categories.firstOrNull() ?: "",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // Rating
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = Color(0xFFFFB800),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "${professional.rating} (${professional.reviewsCount}+)",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Botão
            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarMonth,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text("Agendar")
            }
        }
    }
}
```

### 2. **UpcomingBookingCard.kt** (Prioridade ALTA)
```kotlin
@Composable
fun UpcomingBookingCard(
    booking: Booking,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone de calendário com data
            Surface(
                modifier = Modifier.size(64.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = booking.scheduledDate.dayOfMonth.toString(),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Informações do agendamento
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = booking.service.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = booking.professional.name,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = booking.scheduledDate.toLocalTime().toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            
            // Ícone de navegação
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}
```

### 3. **CategoryChipRow.kt** (Prioridade ALTA)
```kotlin
@Composable
fun CategoryChipRow(
    categories: List<ServiceCategory>,
    onCategoryClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories) { category ->
            CategoryChip(
                category = category,
                onClick = { onCategoryClick(category.id) }
            )
        }
    }
}

@Composable
fun CategoryChip(
    category: ServiceCategory,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = Color.Transparent
        )
    ) {
        Icon(
            painter = painterResource(id = category.iconRes),
            contentDescription = category.name,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(category.name)
    }
}
```

---

## 🔧 APIs NECESSÁRIAS (Backend)

### Endpoints Mínimos para MVP:

```
# Profissionais
GET    /api/professionals              # Listar profissionais
GET    /api/professionals/{id}         # Detalhes do profissional
GET    /api/professionals/search       # Buscar com filtros
POST   /api/professionals              # Criar perfil profissional
PUT    /api/professionals/{id}         # Atualizar perfil

# Serviços
GET    /api/services                   # Listar serviços
GET    /api/services/{id}              # Detalhes do serviço
GET    /api/professionals/{id}/services # Serviços do profissional
POST   /api/services                   # Criar serviço
PUT    /api/services/{id}              # Atualizar serviço

# Agendamentos
GET    /api/bookings                   # Meus agendamentos
GET    /api/bookings/{id}              # Detalhes do agendamento
POST   /api/bookings                   # Criar agendamento
PUT    /api/bookings/{id}              # Atualizar (cancelar, etc)
GET    /api/professionals/{id}/availability # Horários disponíveis

# Categorias
GET    /api/categories                 # Listar categorias

# Avaliações
GET    /api/professionals/{id}/reviews # Avaliações do profissional
POST   /api/reviews                    # Criar avaliação
```

---

## ✅ CHECKLIST DE ENTREGA MVP

### Funcionalidades Cliente:
- [ ] Login e Cadastro
- [ ] Home com categorias e profissionais recomendados
- [ ] Buscar profissionais por categoria/nome
- [ ] Ver perfil completo do profissional
- [ ] Ver serviços oferecidos com preços
- [ ] Criar agendamento
- [ ] Ver meus agendamentos (ativos e passados)
- [ ] Cancelar agendamento
- [ ] Ver detalhes do agendamento

### Funcionalidades Profissional:
- [ ] Dashboard com visão geral
- [ ] Ver agendamentos recebidos
- [ ] Confirmar/Rejeitar agendamentos
- [ ] Gerenciar serviços (criar, editar)
- [ ] Configurar disponibilidade
- [ ] Ver avaliações recebidas

### Técnico:
- [ ] Integração completa com backend
- [ ] Cache local para dados frequentes
- [ ] Tratamento de erros robusto
- [ ] Loading states em todas as telas
- [ ] Navegação fluida
- [ ] Performance otimizada

---

## 🎯 MÉTRICAS DE SUCESSO DO MVP

1. **Usuário consegue:**
   - Criar conta em menos de 2 minutos
   - Encontrar profissional em menos de 1 minuto
   - Criar agendamento em menos de 3 minutos

2. **Performance:**
   - Tempo de carregamento de telas < 2s
   - API response time < 500ms
   - App roda suave em dispositivos mid-range

3. **Estabilidade:**
   - Crash-free rate > 99%
   - Taxa de erro de API < 1%

---

**Próximo passo:** Começar pela Semana 1 criando as entidades de domínio!


