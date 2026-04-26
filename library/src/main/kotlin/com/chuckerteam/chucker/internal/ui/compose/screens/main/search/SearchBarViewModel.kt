package com.chuckerteam.chucker.internal.ui.compose.screens.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chuckerteam.chucker.internal.data.repository.Di
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

internal class SearchBarViewModel : ViewModel() {

    private val suggestionsRepository = Di.suggestionsRepository
    private val currentQuery = MutableStateFlow("")

    val suggestions = currentQuery.map { searchQuery ->
        suggestionsRepository.loadSuggestions(searchQuery)
    }

    fun onQueryChanged(query: String) {
        currentQuery.value = query
    }

    fun onSearch(query: String) {
        currentQuery.value = query
        viewModelScope.launch(Dispatchers.IO) {
            suggestionsRepository.saveSearchQuery(query)
        }
    }
}


