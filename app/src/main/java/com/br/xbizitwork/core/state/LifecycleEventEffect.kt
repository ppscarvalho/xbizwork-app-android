package com.br.xbizitwork.core.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T : Any> LifecycleEventEffect(
    eventFlow: Flow<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    onEvent: (T) -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(eventFlow) {
        lifecycleOwner.repeatOnLifecycle(lifecycleState) {
            eventFlow.collect { event ->
                onEvent(event)
            }
        }
    }
}
