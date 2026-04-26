package com.chuckerteam.chucker.internal.data.repository

import androidx.lifecycle.LiveData
import com.chuckerteam.chucker.internal.data.entity.SuggestionEntity
import com.chuckerteam.chucker.internal.data.room.SuggestionsDao

internal class SuggestionsRepository(
    private val suggestionsDao: SuggestionsDao
) {

    fun loadSuggestions(query: String): LiveData<List<SuggestionEntity>> = suggestionsDao.suggestions(query)

    suspend fun delete(query: String) {
        suggestionsDao.delete(query)
    }

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
