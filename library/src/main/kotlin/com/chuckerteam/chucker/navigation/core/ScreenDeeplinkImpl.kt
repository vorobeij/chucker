package com.chuckerteam.chucker.navigation.core

import android.net.Uri
import androidx.navigation.NamedNavArgument

internal open class ScreenDeeplinkImpl(
    override val path: String
) : ScreenDeeplink {

    override val arguments: List<NamedNavArgument> = emptyList()
    override val route: String by lazy {
        if (arguments.isEmpty()) path
        else {
            Uri.Builder()
                .appendPath(path).apply {
                    arguments.forEach {
                        appendQueryParameter(it.name, """{${it.name}}""")
                    }
                }
                .build()
                .toString()
                .cleanupUrl()
        }
    }
}
