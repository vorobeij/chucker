package com.chuckerteam.chucker.internal.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chuckerteam.chucker.internal.data.entity.HttpTransaction
import com.chuckerteam.chucker.internal.data.entity.SuggestionEntity

@Database(
    entities = [
        HttpTransaction::class,
        SuggestionEntity::class,
    ],
    version = 6,
    exportSchema = false
)
internal abstract class ChuckerDatabase : RoomDatabase() {
    abstract fun transactionDao(): HttpTransactionDao
    abstract fun suggestionsDao(): SuggestionsDao

    companion object {

        private const val DB_NAME = "chucker.db"

        fun create(applicationContext: Context): ChuckerDatabase {
            return Room.databaseBuilder(applicationContext, ChuckerDatabase::class.java, DB_NAME)
                .fallbackToDestructiveMigration(false)
                .build()
        }
    }
}
