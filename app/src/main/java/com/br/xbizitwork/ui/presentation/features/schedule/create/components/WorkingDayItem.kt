package com.br.xbizitwork.ui.presentation.features.schedule.create.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.br.xbizitwork.domain.model.schedule.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

// NOTA: Este componente não é mais usado no novo fluxo UX
// Mantém aqui apenas para compatibilidade
// O novo fluxo usa dropdowns e lista de ScheduleTimeSlot
