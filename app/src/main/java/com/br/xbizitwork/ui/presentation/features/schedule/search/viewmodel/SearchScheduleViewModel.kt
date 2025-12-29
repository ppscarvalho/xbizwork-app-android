package com.br.xbizitwork.ui.presentation.features.schedule.search.viewmodel

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.xbizitwork.core.util.extensions.collectUiState
import com.br.xbizitwork.domain.usecase.schedule.SearchScheduleUseCase
import com.br.xbizitwork.ui.presentation.features.schedule.search.events.SearchSchedulesEvent
import com.br.xbizitwork.ui.presentation.features.schedule.search.state.SearchSchedulesUIState
import com.br.xbizitwork.ui.presentation.features.schedule.search.state.SearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScheduleViewModel @Inject constructor(
    private val searchScheduleUseCase: SearchScheduleUseCase
): ViewModel() {
    private val _uiState: MutableStateFlow<SearchSchedulesUIState> = MutableStateFlow(SearchSchedulesUIState())
    val uiState: StateFlow<SearchSchedulesUIState> = _uiState.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private val searchState = snapshotFlow { _uiState.value.queryTextState.text }
        .debounce( 500)
        .mapLatest {
            if(it.isBlank()){
                SearchState.Empty
            }else{
                SearchState.Query(it.toString())
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SearchState.Empty
        )

    fun onEvent(event: SearchSchedulesEvent){
        when(event) {
            is SearchSchedulesEvent ->{
                observerSearch()
            }
        }
    }

    private fun observerSearch() {
        viewModelScope.launch {
            searchState.collectLatest{searchState ->
                when(searchState){
                    is SearchState.Empty -> {
                        _uiState.update { it.copy(
                            searchIsEmpty = true,
                            schedules = emptyList(),
                            isEmpty = false,
                            isLoading = false,
                            errorMessage = null
                        ) }
                    }
                    is SearchState.Query -> {
                        fetchSchedules(searchState.query)
                    }
                }
            }
        }
    }
    private fun fetchSchedules(query: String) {
        viewModelScope.launch {
            searchScheduleUseCase.invoke(
                SearchScheduleUseCase.Parameters(query)
            ).collectUiState(
                onLoading = {
                    _uiState.update { it.copy(isLoading = true) }
                },
                onEmpty = {
                    _uiState.update { it.copy(isLoading = false, isEmpty = true) }
                },
                onFailure = {error ->
                    _uiState.update { it.copy(isLoading = false, isEmpty = false, errorMessage = error.message) }
                },
                onSuccess = { result ->
                    _uiState.update { it.copy(isLoading = false, isEmpty = false, schedules = result, errorMessage = null) }
                }
            )
        }
    }
}