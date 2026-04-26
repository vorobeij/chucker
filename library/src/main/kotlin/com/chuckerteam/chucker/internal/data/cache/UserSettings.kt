package com.chuckerteam.chucker.internal.data.cache

import android.content.Context
import android.content.SharedPreferences

internal class UserSettings(
    applicationContext: Context
) {

    private val prefs: SharedPreferences = applicationContext.getSharedPreferences("chucker", Context.MODE_PRIVATE)

    val transaction = Transaction(prefs)
}

internal class Transaction(
    prefs: SharedPreferences
) {
    var openedTabIndex: Int by SharedPreferencesDelegate(prefs, "openedTabIndex", 0)
}
