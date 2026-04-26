package com.chuckerteam.chucker.internal.data.repository

import android.content.Context
import com.chuckerteam.chucker.internal.data.cache.UserSettings
import com.chuckerteam.chucker.internal.data.room.ChuckerDatabase
import java.lang.ref.WeakReference

internal object Di {

    private lateinit var context: WeakReference<Context>
    private val applicationContext get() = requireNotNull(context.get())
    private val database by lazy { ChuckerDatabase.Companion.create(applicationContext) }

    val transactionRepository by lazy { HttpTransactionDatabaseRepository(database) }
    val userSettings by lazy { UserSettings(applicationContext) }
    val suggestionsRepository by lazy { SuggestionsRepository(suggestionsDao = database.suggestionsDao()) }

    fun init(applicationContext: Context) {
        context = WeakReference(applicationContext)
    }
}
