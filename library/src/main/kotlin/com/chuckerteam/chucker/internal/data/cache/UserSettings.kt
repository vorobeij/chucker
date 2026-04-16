package com.chuckerteam.chucker.internal.data.cache

import android.content.Context
import android.content.SharedPreferences

internal object UserSettings {

    private var _prefs: SharedPreferences? = null
    private val prefs: SharedPreferences get() = _prefs ?: throw IllegalStateException("Call initialize before access!")

    object Transaction {
        var openedTabIndex: Int by SharedPreferencesDelegate(prefs, "openedTabIndex", 0)
    }

    fun initialize(applicationContext: Context) {
        _prefs = applicationContext.getSharedPreferences("chucker", Context.MODE_PRIVATE)
    }
}
