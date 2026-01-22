package com.br.xbizitwork.ui.presentation.features.searchprofessionals.state

/**
 * Search state for professional search
 * Following the same pattern as schedule/search/SearchState
 */
sealed class SearchState {
    data object Empty : SearchState()
    data class Query(val query: String) : SearchState()
}

