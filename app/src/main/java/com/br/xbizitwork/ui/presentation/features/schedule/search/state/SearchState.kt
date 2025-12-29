package com.br.xbizitwork.ui.presentation.features.schedule.search.state

sealed class SearchState{
    data object Empty: SearchState()
    data class Query(val query: String): SearchState()
}