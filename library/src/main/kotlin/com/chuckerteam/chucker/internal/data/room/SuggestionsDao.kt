package com.chuckerteam.chucker.internal.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.chuckerteam.chucker.internal.data.entity.SuggestionEntity

@Dao
internal interface SuggestionsDao {

    @Insert
    suspend fun insert(suggestion: SuggestionEntity)

    @Query("delete from suggestions where q = :query")
    suspend fun delete(query: String)

    @Query(
        """
        SELECT s.* FROM suggestions s
        WHERE s.q LIKE '%' || :query || '%'
        ORDER BY s.timestamp DESC
        limit 20
    """
    )
    fun suggestions(query: String): LiveData<List<SuggestionEntity>>

    @Query(
        """
        select * from suggestions
        where q=:query
    """
    )
    suspend fun get(query: String): SuggestionEntity?

    @Query(
        """
        update suggestions
        set timestamp=:timestamp
        where id=:id
    """
    )
    suspend fun update(id: Long, timestamp: Long)
}
