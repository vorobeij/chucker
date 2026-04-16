package com.chuckerteam.chucker.internal.data.cache

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

internal class SharedPreferencesDelegate<T>(
    private val prefs: SharedPreferences,
    private val key: String,
    private val defaultValue: T
) : ReadWriteProperty<Any, T> {

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return when (defaultValue) {
            is String -> prefs.getString(key, defaultValue) as T
            is Int -> prefs.getInt(key, defaultValue) as T
            is Long -> prefs.getLong(key, defaultValue) as T
            is Float -> prefs.getFloat(key, defaultValue) as T
            is Boolean -> prefs.getBoolean(key, defaultValue) as T
            is Set<*> -> prefs.getStringSet(key, defaultValue as Set<String>) as T
            null -> prefs.getString(key, null) as T // Nullable String only
            else -> throw IllegalArgumentException("Type ${defaultValue::class.simpleName} not supported by SharedPreferences")
        }
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        prefs.edit().apply {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Boolean -> putBoolean(key, value)
                is Set<*> -> putStringSet(key, value as Set<String>)
                null -> remove(key) // Native way to represent null
                else -> throw IllegalArgumentException("Type ${value::class.simpleName} not supported by SharedPreferences")
            }
            apply() // Async write. Use commit() only if you need synchronous confirmation
        }
    }
}
