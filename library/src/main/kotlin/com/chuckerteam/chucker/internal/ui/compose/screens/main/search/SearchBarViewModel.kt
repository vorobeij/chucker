package com.chuckerteam.chucker.internal.ui.compose.screens.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.chuckerteam.chucker.internal.data.entity.SuggestionEntity
import com.chuckerteam.chucker.internal.data.repository.Di
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class SearchBarViewModel : ViewModel() {

    private val suggestionsRepository = Di.suggestionsRepository
    private val currentQuery = MutableLiveData("")

    val suggestions: LiveData<List<SuggestionEntity>> = currentQuery.switchMap { searchQuery ->
        suggestionsRepository.loadSuggestions(searchQuery)
    }

    fun onDelete(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            suggestionsRepository.delete(query)
        }
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


