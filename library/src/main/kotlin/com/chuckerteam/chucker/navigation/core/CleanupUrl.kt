package com.chuckerteam.chucker.navigation.core

internal fun String.cleanupUrl() = this.replaceFirst("/", "")
    .replace("%7B", "{")
    .replace("%7D", "}")
