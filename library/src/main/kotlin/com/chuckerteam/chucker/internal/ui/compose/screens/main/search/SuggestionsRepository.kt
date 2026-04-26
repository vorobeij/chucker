package com.chuckerteam.chucker.internal.ui.compose.screens.main.search

import com.chuckerteam.chucker.internal.data.entity.SuggestionEntity
import com.chuckerteam.chucker.internal.data.room.SuggestionsDao

internal class SuggestionsRepository(
    private val suggestionsDao: SuggestionsDao
) {

    suspend fun loadSuggestions(query: String): List<SuggestionEntity> = suggestionsDao.suggestions(query)

    suspend fun saveSearchQuery(query: String) {
        if (query.isEmpty()) return
        val existing = suggestionsDao.get(query)
        if (existing == null) {
            suggestionsDao.insert(SuggestionEntity(query = query, timestamp = System.currentTimeMillis()))
        } else {
            suggestionsDao.update(id = existing.id, timestamp = System.currentTimeMillis())
        }
    }
}
